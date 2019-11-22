<?php

namespace common\models;

use common\models\base\BaseModel;
use Yii;

/**
 * This is the model class for table "posts".
 *
 * @property int $id 自增ID
 * @property string $title 标题
 * @property string $summary 摘要
 * @property string $content 内容
 * @property string $label_img 标签图
 * @property int $cat_id 分类id
 * @property int $user_id 用户id
 * @property string $user_name 用户名
 * @property int $is_valid 是否有效：0-未发布 1-已发布
 * @property int $created_at 创建时间
 * @property int $updated_at 更新时间
 */
class PostModel extends BaseModel
{
    const IS_VALID = 1;//已发布
    const NO_VALID = 0;//未发布
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'posts';
    }

    /**
     * 关联RelationPostTagModel
     *
     * @return void
     */
    public function getRelate()
    {
        return $this->hasMany(RelationPostTagsModel::className(),['post_id'=>'id']);
    }

    public function getExtend(){
        return $this->hasOne(PostExtendsModel::className(),['post_id'=>'id']);
    }

    public function getCat(){
        return $this->hasOne(CatModel::className(),['id' => 'cat_id']);
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['content'], 'string'],
            [['cat_id', 'user_id', 'is_valid', 'created_at', 'updated_at'], 'integer'],
            [['title', 'summary', 'label_img', 'user_name'], 'string', 'max' => 255],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'title' => '标题',
            'summary' => '摘要',
            'content' => 'Content',
            'label_img' => 'Label Img',
            'cat_id' => '分类',
            'user_id' => 'User ID',
            'user_name' => '作者',
            'is_valid' => '状态',
            'created_at' => '创建时间',
            'updated_at' => '更新时间',
        ];
    }
}
