<?php
namespace frontend\widgets\tag;

use common\models\TagsModel;
use Yii;
use yii\bootstrap\Widget;

class TagWidget extends Widget{
    public $title = '';
    public $limit = 20;
    public function run()
    {
        $res = TagsModel::find()
            ->orderBy(['post_num' => SORT_DESC])
            ->limit($this->limit)
            ->all();
        $result['title'] = $this->title?:Yii::t('common','TagCloud');
        $result['body'] = $res?:[];

        return $this->render('index',['data'=>$result]);
    }
}