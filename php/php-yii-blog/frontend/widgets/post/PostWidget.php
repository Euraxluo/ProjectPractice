<?php
namespace frontend\widgets\post;

use common\models\PostModel;
use frontend\models\PostForm;
use Yii;
use yii\base\Widget;
use yii\data\Pagination;
use yii\helpers\Url;

/**
 * 自定义文章列表组件
 */
class PostWidget extends Widget{
    public $title = '';//标题
    public $limit = 6;//显示条数
    public $more = true;//是否显示更多
    public $page = true;//是否显示分页
    public $cond = [];
    public function run()
    {
        //获取page参数,如果没有默认为1
        $curPage = Yii::$app->request->get('page',1);
        //查询条件,已发布的
        $cond =$this->cond?$this->cond:['=','is_valid',PostModel::IS_VALID];
        $res = PostForm::getList($cond,$curPage,$this->limit);
        $result['title'] = $this->title?$this->title:Yii::t('common','Latest articles');
        $result['more'] = Url::to(['post/index']);
        $result['body'] = $res['data']?$res['data']:[];
        //是否显示分页
        if($this->page){
            $pages = new Pagination(['totalCount'=>$res['count'],'pageSize'=>$res['pageSize']]);
            $result['page'] = $pages;
        }

        return $this->render('index',['data'=>$result]);
    }
}