<?php

use yii\helpers\Html;
use yii\grid\GridView;
use yii\helpers\Url;

/* @var $this yii\web\View */
/* @var $searchModel common\models\PostSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = '文章信息';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="post-model-index">

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'id',
            'title'=>[
                'attribute' => 'title',
                'format' => 'raw',
                'value' => function ($model){   
                    return '<a href="'.Yii::$app->params['frontend_url'].Url::to(['post/view','id'=>$model->id]).'">'.$model->title.'</a>';
                }
            ],
            'summary:ntext',
            //'content:ntext',
            //'label_img',
            'cat.cat_name',
            //'user_id',
            'user_name',
            'is_valid'=>[
                'label' => '状态',
                'attribute' => 'is_valid',
                'value' => function($model){
                    return ($model->is_valid == 0)?'未发布':'发布';
                },
                'filter' => ['0'=>'未发布','1'=>'发布'],
            ],
            'created_at:datetime',
            //'updated_at',

            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
