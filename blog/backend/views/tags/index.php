<?php

use yii\helpers\Html;
use yii\grid\GridView;
use yii\helpers\Url;

/* @var $this yii\web\View */
/* @var $searchModel common\models\TagsSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = '标签信息';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="tags-model-index">
    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'id',
            'tag_name',
            'post_num',

            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
