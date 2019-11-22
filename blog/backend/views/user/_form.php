<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model common\models\User */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="user-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'status')->dropDownList(['0'=>'非激活','10'=>'激活']) ?>
 
    <div class="form-group">
        <?= Html::submitButton($model->isNewRecord ? '创建' : '更新',['class' => 'btn btn-success']) ?>   
        <?php // Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
