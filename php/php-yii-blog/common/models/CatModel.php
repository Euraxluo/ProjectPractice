<?php

namespace common\models;

use common\models\base\BaseModel;
use Yii;

/**
 * This is the model class for table "cats".
 *
 * @property int $id 自增ID
 * @property string $cat_name 分类名称
 */
class CatModel extends BaseModel
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'cats';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['cat_name'], 'string', 'max' => 255],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'cat_name' => '分类名',
        ];
    }

    public static function getAllCats(){
        $cat = ['0'=>Yii::t('common','NoClassification')];
        $res = self::find()->asArray()->all();
        if($res){
            foreach ($res as $k => $v) {
                $cat[$v['id']] = $v['cat_name'];
            }
        }
        return $cat;
    }
}
