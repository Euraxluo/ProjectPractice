<?php

use yii\helpers\Html;
use yii\grid\GridView;
use yii\helpers\Url;

/* @var $this yii\web\View */
/* @var $searchModel common\models\CatSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = '分类信息';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="cat-model-index">
<a class="btn btn-success btn-block btn-post" href="<?=Url::to(['tags/create'])?>"><?= Yii::t('common','创建分类')?></a>
    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'id',
            'cat_name',

            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
