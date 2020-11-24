<?php

use frontend\widgets\tag\TagWidget;
use frontend\widgets\banner\BannerWidget;
use frontend\widgets\chat\ChatWidget;
use frontend\widgets\hot\HotWidget;
use frontend\widgets\post\PostWidget;

$this->title = Yii::t('common','Blog-index');
?>
<div class="row">
    <div class="col-lg-9">
        <!-- 图片轮播 -->
        <?= BannerWidget::widget()?>
        <?= PostWidget::widget($cond)?>
    </div>
    <div class="col-lg-3">
        <?= ChatWidget::widget()?>
        <?= HotWidget::widget(['limit'=>5])?>
        <?= TagWidget::widget()?>
    </div>
    <!-- <div class="col-lg-9">
       
    </div> -->

</div>
