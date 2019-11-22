<?php

namespace backend\assets;

use yii\web\AssetBundle;

/**
 * Main backend application asset bundle.
 */
class AppAsset extends AssetBundle
{
    public $basePath = '@webroot';
    public $baseUrl = '@web';
    public $css = [
        'static/css/font-awesome-4.4.0/css/font-awesome.css',
        'static/css/layout.css',
        'static/css/site.css',
    ];
    public $js = [
        'static/js/jquery-ui.js',
        'static/js/toggles.js',
        'static/js/layout.js',
        'static/js/site.js',
    ];
    public $depends = [
        'yii\web\YiiAsset',
        'yii\bootstrap\BootstrapAsset',
    ];
}
