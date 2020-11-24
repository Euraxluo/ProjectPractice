<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\TagsModel */

$this->title = 'Update Tags [ tags_id:' . $model->id.' , tags_name:'.$model->tag_name.']';
$this->params['breadcrumbs'][] = ['label' => 'Tags Models', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="tags-model-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
