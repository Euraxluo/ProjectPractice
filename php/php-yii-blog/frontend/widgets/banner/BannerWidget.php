<?php
namespace frontend\widgets\banner;

use common\models\PostModel;
use frontend\models\PostForm;
use Yii;
use yii\base\Widget;
use yii\data\Pagination;
use yii\helpers\Url;

/**
 * 自定义文章列表组件
 */
class BannerWidget extends Widget{
    public $items = [];
    public function init()
    {
        //如果为空,设置默认
        if(empty($this->items)){
            $this->items = [
                [
                    'label'=>'demo',
                    'image_url'=>Yii::$app->params['banner']['0'],
                    'url'=>['site/index'],
                    'html'=>'',
                    'active'=>'active'
                ],
                [
                    'label'=>'demo',
                    'image_url'=>Yii::$app->params['banner']['1'],
                    'url'=>['site/index'],
                    'html'=>'',
                    'active'=>''
                ],
                [
                    'label'=>'demo',
                    'image_url'=>Yii::$app->params['banner']['2'],
                    'url'=>['site/index'],
                    'html'=>'',
                    'active'=>''
                ],
            ];
        }

    }
    public function run()
    {
        $data['items'] = $this->items;
        return $this->render('index',['data'=>$data]);
    }
}