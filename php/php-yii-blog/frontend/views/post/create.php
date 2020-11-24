<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

// $this->title = Yii::t('common','CreateArticle');
// $this->params['breadcrumbs'][] = ['label'=>Yii::t('common','Articles'),'url'=>['post/index']];
// $this->params['breadcrumbs'][] = $this->title;
//$form->field($model, 'label_img')->widget('common\widgets\file_upload\FileUpload',['config'=>[]])
?>
<div class="row">
    <div class="col-lg-9">
        <div class="panel-title box-title">
            <span> <?php echo Yii::t('common','CreateArticle'); ?></span>
        </div>
        <div class="panel-body">
            <?php $form = ActiveForm::begin() ?>
            <?= $form->field($model, 'title')->textInput(['maxlength' => true]) ?>
            <?= $form->field($model, 'cat_id')->dropDownList($cat) ?>
            <?= $form->field($model, 'label_img')->widget('common\widgets\file_upload\FileUpload',['config'=>[]])?>
            <?= $form->field($model, 'content')->widget('common\widgets\ueditor\Ueditor',[
                'options'=>[
                    // 'initialFrameWidth' => 850,
                ]
            ])?>
            <?= $form->field($model, 'tags')->widget('common\widgets\tags\TagWidget') ?>
            <div class="form-group">
                <?= Html::submitButton(Yii::t('common','Release'),['class'=>'btn btn-success']) ?>
            </div>
            <?php $form = ActiveForm::end() ?>
            
        </div>
    </div>
    <div class="col-lg-3">
        <div class="panel-title box-title">
            <span> <?php echo Yii::t('common','Considerations'); ?></span>
        </div>
        <div class="panel-body">
            <p>1.asasas</p>
            <p>2.asasas</p>
        </div>       
    </div>
</div>