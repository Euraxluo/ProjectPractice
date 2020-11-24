<?php

/* @var $this \yii\web\View */
/* @var $content string */

use yii\helpers\Html;
use yii\bootstrap\Nav;
use yii\bootstrap\NavBar;
use yii\widgets\Breadcrumbs;
use frontend\assets\AppAsset;
use common\widgets\Alert;

AppAsset::register($this);
?>
<?php $this->beginPage() ?>
<!DOCTYPE html>
<html lang="<?= Yii::$app->language ?>">
<head>
    <meta charset="<?= Yii::$app->charset ?>">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <?= Html::csrfMetaTags() ?>
    <title><?= Html::encode($this->title) ?></title>
    <?php $this->head() ?>
</head>
<body>
<?php $this->beginBody() ?>

<div class="wrap">
    <?php
    NavBar::begin([
        'brandLabel' => Yii::t('common','Blog'),
        'brandUrl' => Yii::$app->homeUrl,
        'options' => [
            'class' => 'navbar-inverse navbar-fixed-top',
        ],
    ]);

    $leftMenus = [
        ['label' => Yii::t('yii','Home'), 'url' => ['/site/index']],
        ['label' => Yii::t('common','Article'), 'url' => ['/post/index']],
        ['label' => '<form class="navbar-form navbar-left search-form" method="get" action="/post/search">
        <div class="input-group">
          <input type="text" name="PostSearch" class="form-control" placeholder="查找">
          <span type="submit" class="input-group-addon search-submit">
          <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
          </span>
        </div>
      </form>',
      'linkOptions' => [ 'style' => 'padding-top:0;height:50px;width:400px'],
      ]
    ];
    if (Yii::$app->user->isGuest) {
        $rightMenus[] = ['label' => Yii::t('common','Signup'), 'url' => ['/site/signup']];
        $rightMenus[] = ['label' => Yii::t('common','Login'), 'url' => ['/site/login']];
    } else {
        $rightMenus[] = [
            'label' => '<img src="'.Yii::$app->params['avatar']['small'].'" alt="'. Yii::$app->user->identity->username. '">',
            'linkOptions' => ['class' => 'avatar'],//设置样式
            'items'=>[//下拉框
                [
                    'label' =>'<i class="fa fa-sign-out"></i>' .Yii::t('common','Logout'),
                    'url' => ['/site/logout'],
                    'linkOptions' => ['data-method' => 'post']
                ],
                [
                    'label' => Yii::t('common','MyCenter'),
                    // 'url' => ['/site/logout'],
                    // 'linkOptions' => ['data-method' => 'post']
                ],
            ]

        ];
    }   

    
    echo Nav::widget([
        'options' => ['class' => 'navbar-nav navbar-left'],
        'encodeLabels' => false,
        'items' => $leftMenus,
    ]);
    echo Nav::widget([
        'options' => ['class' => 'navbar-nav navbar-right'],
        'encodeLabels' => false,
        'items' => $rightMenus,
    ]);
    NavBar::end();
    ?>
    

    <div class="container">
        <?= Breadcrumbs::widget([
            'links' => isset($this->params['breadcrumbs']) ? $this->params['breadcrumbs'] : [],
        ]) ?>
        <?= Alert::widget() ?>
        <?= $content ?>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <p class="pull-left">&copy; My Company <?= date('Y') ?></p>

        <p class="pull-right"><?= Yii::powered() ?></p>
    </div>
</footer>

<?php $this->endBody() ?>
</body>
</html>
<?php $this->endPage() ?>
