<?php
namespace frontend\controllers;

use common\models\CatModel;
use common\models\PostExtendsModel;
use common\models\PostModel;
use common\models\PostSearch;
use frontend\controllers\base\BaseController;
use frontend\models\PostForm;
use Yii;
use yii\filters\AccessControl;
use yii\filters\VerbFilter;
use yii\web\NotFoundHttpException;

/**
 * Post controller(文章控制器)
 */
class PostController extends BaseController
{
    /**

     * 控制登录权限

     * @see \yii\base\Component::behaviors()

     */

    public function behaviors()

    {

        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['index', 'create', 'upload', 'ueditor'],
                'rules' => [
                    [
                        'actions' => ['index'],
                        'allow' => true,
                    ],
                    [
                        'actions' => ['create', 'upload', 'ueditor'],
                        'allow' => true,
                        'roles' => ['@'],
                    ],
                ],
            ],
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    '*' => ['post','get'],
                ],
            ],
        ];
    }

    /**
     * 文章列表
     *
     * @return mixed
     */
    public function actionIndex()
    {
        $param =  Yii::$app->request->get();
        /**
         * 获取相关tag对应的文章
         */
        
        if(isset($param['tag'])){
            $tag = $param['tag'];
            $model = new PostForm();
            $posts =  $model->getPostIdByTag($tag);
            $cond =['in','id',$posts]; 
            return $this->render('index',['cond'=> ['limit'=>4,'cond'=>$cond]]);
        }else{
            return $this->render('index',['cond'=> ['limit'=>4]]);
        }
        
    }

    /**
     * 文章搜索:'title','summary','content','user_name'的模糊查询
     *
     * @param [type] $PostSearch
     * @return void
     */
    public function actionSearch($PostSearch){
        if($PostSearch){
            $params =  Yii::$app->request->queryParams;
            $param_array = ['title','summary','content','user_name'];
            $posts = [];
            foreach ($param_array as $key) {
                $searchModel = new PostSearch();
                $params['PostSearch']=[$key=>$PostSearch];
                $dataProvider = $searchModel->search($params);
                $data = $dataProvider->query->asarray()->all();
                foreach($data as $v){
                    array_push($posts,$v['id']);
                }
            }
            $cond =['in','id',$posts]; 
            return $this->render('index',['cond'=> ['limit'=>4,'cond'=>$cond]]);
    
        }else{
            return $this->redirect(['post/index']);
        }




    }

    /**
     * 文章创建
     *
     * @return mixed
     */
    public function actionCreate()
    {
        $model = new PostForm();
        //定义场景
        $model->setScenario(PostForm::SCENARIOS_CREATE);
        if($model->load(Yii::$app->request->post())&&$model->validate()){
            if(!$model->create()){
                Yii::$app->session->setFlash('warning',$model->_lastError);
            }else{
                return $this->redirect(['post/view', 'id'=>$model->id]);
            }
        }
        //获取所有分类
        $cats = CatModel::getAllCats();
        return $this->render('create',['model' => $model,'cat'=>$cats]);
    }

    /**
     * 文章更新
     *
     * @param [type] $id
     * @return void
     */
    public function actionUpdate($id)
    {
        $model = new PostForm();
        $view =  $model->getViewById($id);
        //定义场景
        $model->setScenario(PostForm::SCENARIOS_UPDATE);
        // $old = $this->findModel($id);
        $model->id = $view['id'];
        $model->title = $view['title'];
        $model->cat_id = $view['cat_id'];
        $model->label_img = $view['label_img'];
        $model->content = $view['content'];
        $model->tags = $view['tags'];
        //如果有post请求
        if ($model->load(Yii::$app->request->post()) && $model->validate()) {
            if($model->update($id)===1){
                Yii::$app->session->setFlash('warning',$model->_lastError.$model->update($id));
            }else{
                return $this->redirect(['post/view', 'id' => $model->id]);
            }
        }
        //获取所有分类
        return $this->render('update', ['model' => $model,'cat'=>CatModel::getAllCats()]);
    }


    public function actionView($id)
    {
        //获取文章的数据
        $model = new PostForm();
        $data = $model->getViewById($id);

        //文章统计
        $model = new PostExtendsModel();
        $model->upCounter(['post_id'=>$id],'browser',1);


        return $this->render('view',['data'=>$data]);
    }


    	
   /**
    * Deletes an existing PostModel model.
    * If deletion is successful, the browser will be redirected to the 'index' page.
    * @param integer $id
    * @return mixed
    * @throws NotFoundHttpException if the model cannot be found
    */
   public function actionDelete($id)
   {
       $this->findModel($id)->delete();
       return $this->redirect(['index']);
   }
   /**
    * Finds the PostModel model based on its primary key value.
    * If the model is not found, a 404 HTTP exception will be thrown.
    * @param integer $id
    * @return PostModel the loaded model
    * @throws NotFoundHttpException if the model cannot be found
    */
   protected function findModel($id)
   {
       if (($model =PostModel::findOne($id)) !== null) {
           return $model;
       }
       throw new NotFoundHttpException('The requested page does not exist.');
   }

    /**
     * 第三方
     *
     * @return void
     */
    public function actions()
    {
        return [
            'upload'=>[
                'class' => 'common\widgets\file_upload\UploadAction',     //这里扩展地址别写错
                'config' => [
                    'imagePathFormat' => "/image/{yyyy}{mm}{dd}/{time}{rand:6}",  //图片存储位置
                ]
            ],
            'ueditor'=>[
                'class' => 'common\widgets\ueditor\UeditorAction',
                'config'=>[
                    //上传图片配置
                    'imageUrlPrefix' => "", /* 图片访问路径前缀 */
                    'imagePathFormat' => "/image/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */
                ]
            ]
        ];
    }
}
