<?php
namespace frontend\widgets\hot;

use common\models\PostExtendsModel;
use common\models\PostModel;
use Yii;
use yii\bootstrap\Widget;
use yii\db\Query;

class HotWidget extends Widget{
    public $title = '';
    public $limit = 6;
    public function run(){
        $res = (new Query())
            ->select('a.browser,b.id,b.title')
            ->from(['a'=>PostExtendsModel::tableName()])
            ->join('LEFT JOIN',['b'=>PostModel::tableName()],'a.post_id = b.id')
            ->where('b.is_valid = '.PostModel::IS_VALID)
            ->orderBy(['browser'=>SORT_DESC,'id'=>SORT_DESC])
            ->limit($this->limit)
            ->all();
        $result['title'] = $this->title?:Yii::t('common','Hot');
        $result['body'] = $res?:[];

        return $this->render('index',['data'=>$result]);
    }
}