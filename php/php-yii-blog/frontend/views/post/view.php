<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;
use frontend\widgets\hot\HotWidget;
use yii\helpers\Url;

// $this->title = $data['title'];
// $this->params['breadcrumbs'][] = ['label'=>Yii::t('common','Articles'),'url'=>['post/index']];
// $this->params['breadcrumbs'][] = $this->title;
?>
<div class="row">
    <!-- 标题 -->
    <div class="col-lg-9">
        <div class="page-title">
            <h1> <?= $data['title']?></h1>
            <span><?php echo Yii::t('common','Author'); ?>: <a href="#"><?=$data['user_name'];?></a></span>
            <span><?php echo Yii::t('common','Release'); ?>: <?=date('Y-m-d',$data['created_at']);?></span>
            <span><?php echo Yii::t('common','Browse'); ?>: <?=isset($data['extend']['browser'])?$data['extend']['browser']:0?></span>
        </div>
        <!-- <div class="page-title box-title"> -->
        <!-- </div> -->
        <!-- 正文 -->
        <div class="page-content">
            <?=$data['content']?>        
        </div>
        <!-- 标签 -->
        <div class="page-tag">
        <?php echo Yii::t('common','Tags'); ?>:
            <?php foreach ($data['tags'] as $tag):?>
                <span><a href="#"><?= $tag?></a></span>
            <?php endforeach?>
        </div>
    </div>
    <!-- 侧边栏插件 -->
    <div class="col-lg-3">
    <div class="panel">
            <?php if(!Yii::$app->user->isGuest):?>
                <a class="btn btn-success btn-block btn-post" href="<?=Url::to(['post/create'])?>"><?= Yii::t('common','CreateArticle')?> </a>
                <?php if(Yii::$app->user->identity->id == $data['user_id']):?>
                    <a class="btn btn-info btn-block btn-post" href="<?=Url::to(['post/update','id'=>$data['id']])?>"><?= Yii::t('common','EditArticle')?></a>
                <?php endif;?>
            <?php endif;?>
        </div>
        <?= HotWidget::widget()?>   
        <!-- <div class="panel-title box-title">
        </div>
        <div class="panel-body">
        </div>        -->
    </div>
</div>