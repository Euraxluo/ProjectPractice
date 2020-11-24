<?php
namespace frontend\models;

use common\models\PostModel;
use common\models\RelationPostTagsModel;
use common\models\TagsModel;
use common\models\TagsSearch;
use Yii;
use yii\base\Event;
use yii\base\Model;
use yii\db\Query;
use yii\web\NotFoundHttpException;

class PostForm extends Model{
    public $id;
    public $title;
    public $content;
    public $label_img;
    public $cat_id;
    public $tags;
    public $_lastError = "";

    /**
     * 定义场景
     */
    const SCENARIOS_CREATE = 'create';//创建
    const SCENARIOS_UPDATE = 'update';//更新

    /**
     * 定义事件
     */
    const EVENT_AFTER_CREATE = 'eventAfterCreate';//创建
    const EVENT_AFTER_UPDATE = 'eventAfterUpdate';//更新

    public static function getList($cond,$curPage = 1,$pageSize = 5,$orderBy = ['id'=>SORT_DESC])
    {
        $model = new PostModel();
        //sql语句
        $select = ['id','title','summary','label_img','cat_id','user_id'
        ,'user_name','is_valid','created_at','updated_at'];
        $query = $model->find()
            ->select($select)
            ->where($cond)
            ->with('relate.tags','extend')
            ->orderBy($orderBy);
        //查询数据
        $res = $model->getPages($query,$curPage,$pageSize);
        //格式化
        $res['data'] = self::_formatList($res['data']);
        return $res;
    }

    public function getPostIdByTag($tag){
        $model = new TagsModel();
        // $model = $model->fingByTagName($tag);
        //sql语句
        $select = ['*'];
        $query = $model->find()
            ->select($select)
            ->where(['=','tag_name',$tag])
            ->with('relate.posts')
            ->asArray()
            ->all();
        foreach ($query as &$list){
            //处理标签的格式
            $list['posts'] = [];
            if(isset($list['relate']) && !empty($list['relate'])){
                foreach ($list['relate'] as $l) {
                    $list['posts'][] = $l['post_id'];
                }
            }
            unset($list['relate']);
        }
        return $query[0]['posts'];
    }

    /**
     * 标签数据格式化
     *
     * @param [type] $data
     * @return void
     */
    public static function _formatList($data){
        if(isset($data['relate'])){
            $list = &$data;
            //处理标签的格式
            $list['tags'] = [];
            if(isset($list['relate']) && !empty($list['relate'])){
                foreach ($list['relate'] as $l) {
                    $list['tags'][] = $l['tags']['tag_name'];
                }
            }
            unset($list['relate']);
            return $data;
        }
        foreach ($data as &$list){
            //处理标签的格式
            $list['tags'] = [];
            if(isset($list['relate']) && !empty($list['relate'])){
                foreach ($list['relate'] as $l) {
                    $list['tags'][] = $l['tags']['tag_name'];
                }
            }
            unset($list['relate']);
        }
        return $data;
    }
    /**
     * 场景设置
     *
     * @return void
     */
    public function scenarios()
    {
        $scenarios = [
            self::SCENARIOS_CREATE => ['title','content','label_img','cat_id','tags'],
            self::SCENARIOS_UPDATE => ['title','content','label_img','cat_id','tags'],
        
        ];
        return array_merge(parent::scenarios(),$scenarios);
    }


    public function rules()
    {
        return [
            [['id','title','content','cat_id','tags'],'required'],
            [['id','cat_id'],'integer'],
            ['title','string','min'=>4,'max'=>50],
        ];
    }
    public function attributeLabels()
    {
        return [
            'id' => Yii::t('common','PostId'),
            'title'=> Yii::t('common','PostTitle'),
            'content'=> Yii::t('common','PostContent'),
            'label_img'=> Yii::t('common','PostLabelImg'),
            'tags'=> Yii::t('common','PostTags'),
            'cat_id'=> Yii::t('common','PostCatId'),
        ];
    }

    /**
     * 修改文章
     *
     * @return void
     */
    public function update($id){
        //开始事务
        $transaction = Yii::$app->db->beginTransaction();
        try{
            $model = PostModel::findOne($id);
            $model->setAttributes($this->attributes);
            $model->summary = $this->_getSummary();
            $model->updated_at = time();
            if(!$model->save()){
                throw new \Exception(Yii::t('common','Article save failed.'));
            }
            $this->id = $model->id;
            $data = array_merge($this->getAttributes(),$model->getAttributes());
            $this->_eventAfterCreate($data);
            $transaction->commit();
            return true;
        }catch(\Exception $e){   
            $transaction->rollBack();//回滚
            $this->_lastError = $e->getMessage();
            return false;
        }
    }

    /**
     * 创建文章
     *
     * @return void
     */
    public function create(){
        //开始事务
        $transaction = Yii::$app->db->beginTransaction();
        try{
            $model = new PostModel();
            $model->setAttributes($this->attributes);
            $model->summary = $this->_getSummary();
            $model->user_id = Yii::$app->user->identity->id;
            $model->user_name = Yii::$app->user->identity->username;
            $model->created_at = time();
            $model->updated_at = time();
            $model->is_valid = PostModel::IS_VALID;
            if(!$model->save()){
                throw new \Exception(Yii::t('common','Article save failed.'));
            }
            $this->id = $model->id;
            $data = array_merge($this->getAttributes(),$model->getAttributes());
            $this->_eventAfterCreate($data);
            $transaction->commit();
            return true;
        }catch(\Exception $e){
            
            $transaction->rollBack();//回滚
            $this->_lastError = $e->getMessage();
            return false;
        }
    }
    /**
     * 获取文章摘要
     *
     * @param integer $start
     * @param integer $length
     * @param string $encoding
     * @return void
     */
    private function _getSummary($start = 0,$length = 90,$encoding = 'utf-8'){
        if (empty($this->content)){
            return null;
        }
        return (mb_substr(str_replace('&nbsp;','',strip_tags($this->content)),$start,$length,$encoding));
    }

    public function getViewById($id)
    {
        $res = PostModel::find()->with('relate.tags','extend')->where(['id'=>$id])->asArray()->one();//两层的关联关系
        if(!$res){
            throw new NotFoundHttpException(Yii::t('common','Article does not exist.'));
        }
        $res = self::_formatList($res);
        return $res;
    }
    public function _eventAfterCreate($data)
    {
        //添加事件
        $this->on(self::EVENT_AFTER_CREATE,[$this,'_eventAddTag'],$data);
        //触发事件
        $this->trigger(self::EVENT_AFTER_CREATE);
    }

    /**
     * 添加标签事件
     *
     * @param [type] $event
     * @return void
     */
    public function _eventAddTag($event)
    {
        //保存标签
        $tag = new TagForm();
        $tag->tags = $event->sender['tags'];
        $tagsIds = $tag->saveTags();
        //删除原来的关联关系
        RelationPostTagsModel::deleteAll(['post_id'=>$event->sender['id']]);
        //批量重新保存
        if(!empty($tagsIds)){
            foreach ($tagsIds as $key => $tag_id) {
                $row[$key]['post_id'] = $this->id;
                $row[$key]['tag_id'] = $tag_id;
            }
        }
        //批量插入
        $res = (new Query())->createCommand()->batchInsert(RelationPostTagsModel::tableName(),['post_id','tag_id'],$row)->execute();
        if (!$res){
            throw new \Exception(Yii::t('common','Failed to save association relationship.'));
        }
    }
}