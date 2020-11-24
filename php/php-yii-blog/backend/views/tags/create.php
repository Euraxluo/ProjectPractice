<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\TagsModel */

$this->title = 'Create Tags Model';
$this->params['breadcrumbs'][] = ['label' => 'Tags Models', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="tags-model-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
