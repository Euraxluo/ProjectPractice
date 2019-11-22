<?php
namespace frontend\models;

use common\models\TagsModel;
use yii\base\Model;

/**
 * Tag form
 */
class TagForm extends Model
{
    public $id;
    public $tags;


    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            // username and password are both required
            ['tags', 'required'],
            // tags must be a string value
            ['tags','each','rule'=>['string']],
        ];
    }

    public function saveTags(){
        $ids = [];
        if (!empty($this->tags)){
            foreach ($this->tags as $tag) {
                $ids[] = $this->_saveTag($tag);
            }
        }
        return $ids;
    }
    private function _saveTag($tag){
        $model = new TagsModel();
        $res = $model->find()->where(['tag_name'=>$tag])->one();
        //新建标签
        if(!$res){
            $model->tag_name = $tag;
            $model->post_num = 1;
            if(!$model->save()){
                throw new \Exception(Yii::t('common','Failed to save a tag.'));
            }
            return $model->id;
        }else{
            $res->updateCounters(['post_num' => 1]);
        }
        return $res->id;
    }
}
