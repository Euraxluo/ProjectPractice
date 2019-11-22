<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\CatModel */

$this->title = 'Create Cat Model';
$this->params['breadcrumbs'][] = ['label' => 'Cat Models', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="cat-model-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
