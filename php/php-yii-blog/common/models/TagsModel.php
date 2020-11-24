<?php

namespace common\models;

use common\models\base\BaseModel;
use Yii;

/**
 * This is the model class for table "tags".
 *
 * @property int $id 自增ID
 * @property string $tag_name 标签名称
 * @property int $post_num 关联文章数
 */
class TagsModel extends BaseModel 
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'tags';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['post_num'], 'integer'],
            [['tag_name'], 'string', 'max' => 255],
            [['tag_name'], 'unique'],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'tag_name' => '标签名',
            'post_num' => '关联文章数',
        ];
    }

    public function getRelate()
    {
        return $this->hasMany(RelationPostTagsModel::className(),['tag_id'=>'id']);
    }

    public function fingByTagName($tag_name)
    {
        return static::findOne(['tag_name' => $tag_name]);
    }
   

}
