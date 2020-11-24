<?php
namespace frontend\models;

use common\models\FeedsModel;
use common\models\TagsModel;
use Yii;
use yii\base\Model;

/**
 * Tag form
 */
class FeedForm extends Model
{
    public $content;
    public $_lastError;


    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            // username and password are both required
            ['content', 'required'],
            // tags must be a string value
            ['content','string','max'=>255],
        ];
    }
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'content' => Yii::t('common','Content'),
        ];
    }
    public function getList()
    {
        $model = new FeedsModel();
        $res = $model->find()
            ->limit(10)
            ->with('user')
            ->orderBy(['id'=>SORT_DESC])
            ->asArray()
            ->all();
        return $res?:[];
    }

    /**
     * 保存留言板
     *
     * @return void
     */
    public function create()
    {
        try{
            $model = new FeedsModel();
            $model->user_id = Yii::$app->user->identity->id;
            $model->content = $this->content;
            $model->created_at = time();
            if(!$model->save()){
                throw new \Exception(Yii::t('common','Save Faild'));
            }
            return true;
        }catch(\Exception $e){
            $this->_lastError = $e->getMessage();
            return false;
        }
    }
}
