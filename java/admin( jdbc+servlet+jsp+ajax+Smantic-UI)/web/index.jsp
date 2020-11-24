 <!-- <%@ page contentType="text/html;charset=UTF-8" language="java" %> -->
<!-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> -->
<html>
  <head>
    <meta charset="utf-8">
    <title>
      欢迎登录CMS!
    </title>
    <link rel="stylesheet" href="static/semantic-ui/semantic.min.css">
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <script type="text/javascript" src="static/js/jquery.form.js"></script>
    <script type="text/javascript" src="static/semantic-ui/semantic.min.js"></script>

    <!-- <link href="js/layer/skin/default/layer.css" rel="stylesheet" /> -->
    <script type="text/javascript" src="static/layer/layer.js"></script>
  </head>
  <script>
      $(document).ready(function(){
        $("#baseIframe").attr("src",localStorage.rurl);
           var id="<%= session.getAttribute("id")  %>";
          // var id='admin'
           if(id == null || ''==id || id == 'null'){
              window.location.href="login.jsp";
           }else{
              //todo 请求servlet,获取用户信息--%>
          }
      });

//打算做数据埋点
    // //localStorage.clear();
    // if (localStorage.OurVebViewPagecount==null){
    //   localStorage.OurVebViewPagecount=1;
    // }
    // else{
    //   localStorage.OurVebViewPagecount++;
    // }

  </script>
  <body>
    <div class="ui thin left sidebar inverted vertical menu"> <!-- visible -->
      <!--侧边栏-->
      <div class="header item">
        <img src="static/img/logo2.png">
      </div>
      
      <div class="ui item accordion inverted">

          <div class="title item">
            <i class="file text icon"></i>
            销售管理
          </div>
          <div class="content">
            <p  class="transition hidden item"><a href="cms/404.html" target="iframe">销售报告</a><i class="cubes icon"></i></p>
            <p  class="transition hidden item"><a href="cms/404.html" target="iframe">库存报告</a><i class="file outline icon"></i></p>
            <p  class="transition hidden item"><a href="cms/otherscms.html" target="iframe">其他事项</a><i class="pin icon"></i></p>
          </div>

          <div class="title item">
            <i class="area chart icon"></i>
            數據管理
          </div>
          <div class="content">
            <p  class="transition hidden item"><a href="cms/404.html" target="iframe">客户管理</a><i class="coffee  icon"></i></p>
            <p  class="transition hidden item"><a href="/GetPhoneList?currentPage=1" target="iframe">产品管理</a><i class="disk outline icon"></i></p>
            <p  class="transition hidden item"><a href="cms/404.html" target="iframe">导入管理</a><i class="upload  icon"></i></p>
          </div>


          <div class="title item">
            <i class="settings icon"></i>
            系統設置
          </div>
          <div class="content">
            <p class="transition hidden item"><a href="cms/404.html" target="iframe">角色管理</a><i class="add user icon"></i></p>
            <p class="transition hidden item"><a href="cms/404.html" target="iframe">用户管理</a><i class="spy icon"></i></p>
            <p class="transition hidden item"><a href="cms/404.html" target="iframe">系统日志</a><i class="book icon"></i></p>
          </div>


          <div class="title item">
              管理员信息
              <i class="user icon"></i>
          </div>
          <div class="content">
              <p  class="transition hidden item"><a href="cms/about.html" target="iframe"><%=session.getAttribute("id")%></a></p>
            <div class="transition item hidden">
              <img class="ui medium circular image" src="static/img/elyse.png" alt="">
            </div>
          </div>
            <!-- 扩展性验证
          <div class="title item">
            <i class="settings icon"></i>
            系統設置
          </div>
          <div class="content">
            <p class="transition hidden item"><a href="base_web.html" target="frm"></a> 角色管理<i class="add user icon"></i></p>
            <p class="transition hidden item">用户管理<i class="spy icon"></i></p>
            <p class="transition hidden item">系统日志<i class="book icon"></i></p>
          </div>
             -->
        </div>
    </div>
    
    <div class="dimmed pusher">        <!--弹出时遮罩-->      
      <iframe src="welcome/welcome.html" name="iframe" id="baseIframe" style="width: 100%;height:100%;margin:0;padding:0;" onmousemove="checkMuose(this)"></iframe>
    </div>

    <script>

$(".transition>a").click(function(){
  var url = $(this).attr(("href"));
  localStorage.rurl=url;
})
//实现侧边栏隐藏
      function checkMuose(div){
        var x1=event.clientX;
        var x2=x1-div.offsetLeft;
        if(x2<10){
          $('.ui.sidebar').sidebar('toggle');
        }
      }
//侧边的下拉效果
      $('.ui.accordion').accordion();
    </script>
  </body>
</html>
