<?php
namespace frontend\widgets\chat;

use common\models\PostModel;
use frontend\models\FeedForm;
use frontend\models\PostForm;
use Yii;
use yii\base\Widget;
use yii\data\Pagination;
use yii\helpers\Url;

/**
 * 自定义文章列表组件
 */
class ChatWidget extends Widget{
    public function run()
    {
        $feed = new FeedForm();
        $data['feed'] = $feed->getList();
        return $this->render('index',['data'=>$data]);
    }
}