<?php

namespace common\models;

use common\models\base\BaseModel;
use Yii;

/**
 * This is the model class for table "relation_post_tags".
 *
 * @property int $id 自增ID
 * @property int $post_id 文章ID
 * @property int $tag_id 标签ID
 */
class RelationPostTagsModel extends BaseModel
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'relation_post_tags';
    }

    public function getTags()
    {
        return $this->hasOne(TagsModel::className(),['id'=>'tag_id']);
    }

    public function getPosts()
    {
        return $this->hasMany(PostModel::className(),['id'=>'post_id']);
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['post_id', 'tag_id'], 'integer'],
            [['post_id', 'tag_id'], 'unique', 'targetAttribute' => ['post_id', 'tag_id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'post_id' => 'Post ID',
            'tag_id' => 'Tag ID',
        ];
    }
}
