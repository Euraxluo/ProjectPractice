<?php

/* @var $this yii\web\View */

use frontend\widgets\post\PostWidget;
use frontend\widgets\hot\HotWidget;
use frontend\widgets\tag\TagWidget;
use yii\helpers\Html;
use yii\helpers\Url;

// $this->title = 'Index';
// $this->params['breadcrumbs'][] = $this->title;
?>
<div class="row">
    <div class="col-lg-9">
        <?= PostWidget::widget($cond)?>
    </div>
    <div class="col-lg-3">
        <div class="panel">
            <?php if(!Yii::$app->user->isGuest):?>
                <a class="btn btn-success btn-block btn-post" href="<?=Url::to(['post/create'])?>"><?= Yii::t('common','CreateArticle')?></a>
                <?php if(Yii::$app->user->identity->id == $data['user_id']):?>
                    <a class="btn btn-info btn-block btn-post" href="<?=Url::to(['post/update','id'=>$data['id']])?>"><?= Yii::t('common','EditArticle')?></a>
                <?php endif;?>
            <?php endif;?>
        </div>
    <?= HotWidget::widget()?>
    <?= TagWidget::widget()?>
    </div>

</div>