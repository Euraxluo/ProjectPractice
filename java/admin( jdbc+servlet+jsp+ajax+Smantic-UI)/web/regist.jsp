<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>欢迎注册CMS</title>
    <link rel="stylesheet" href="static/semantic-ui/semantic.min.css">
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <script type="text/javascript" src="static/js/jquery.form.js"></script>
    <script type="text/javascript" src="static/semantic-ui/semantic.min.js"></script>


  <style type="text/css">
    body {
      background-color: #DADADA;
    }
    body > .grid {
      height: 100%;
    }
    .image {
      margin-top: -100px;
    }
    .column {
      max-width: 450px;
    }
  </style>
</head>
<body>

<script>
    function stringToJson(stringValue)
    {
        eval("var theJsonValue = "+stringValue);
        return theJsonValue;
    }
    $(document).ready(function() { $('.ui.form').form({
        on: 'blur',
        fields: {
            username: {
                identifier  :'username',
                rules: [
                    {
                        type   : 'empty',
                        prompt : '请输入用户名'
                    },
                ]
            },
            phone: {
                identifier  :'phone',
                rules: [
                    {
                        type: 'number',
                        prompt: "请输入有效的手机号"
                    },
                    {
                        type: 'exactLength[11]',
                        prompt: "手机号码长度不正确"
                    }
                ]
            },
            email: {
                identifier : 'email',
                rules: [
                    {
                        type   : 'empty',
                        prompt : '请输入你的电子邮件'
                    },
                    {
                        type   : 'email',
                        optional:true,
                        prompt : '请输入有效的电子邮件'
                    }
                ]
            },
            password: {
                identifier  : 'password',
                rules: [
                    {
                        type   : 'empty',
                        prompt : '请输入密码'
                    },
                    {
                        type   : 'length[6]',
                        prompt : '密码必须至少大于6'
                    },
                ]
            },
            password2: {
                identifier  : 'password2',
                rules: [
                    {
                        type   : 'empty',
                        prompt : '请输入确认密码'
                    },
                    {
                        type   : 'match[password]',
                        prompt : '两次输入必须相同',
                    }
                ]
            }
        }
    });

    $("#register").ajaxForm(function(data){
        jsondata = stringToJson(data)
        if(jsondata.code==0) {

            if(jsondata.message.substring(0,2)=="邮箱"){

                $('.ui.form').form('add prompt', "email", "邮箱已存在");
                $('#email').val('');
                document.getElementById('email').placeholder=jsondata.message;

            }if(jsondata.message.substring(0,3)=="用户名"){

                $('.ui.form').form('add prompt', "username", "用户名已存在");
                $('#username').val('');
                document.getElementById('username').placeholder=jsondata.message;
            }if(jsondata.message=="验证码已失效!"){

                $('.ui.form').form('add prompt', "mobeilcode", "验证码已失效");
                $('#mobeilcode').val('');
                document.getElementById('mobeilcode').placeholder=jsondata.message;
            }
        }else {
            window.location.href="index.jsp";
        }
    });
    });
</script>

<div class="ui middle aligned center aligned grid">
  <div class="column">
    <h2 class="ui teal image header">
      <div class="content">
        注册 CMS账号
      </div>
    </h2>
    <form class="ui large form" id="register" method="post"  action="/register">

      <div class="ui stacked segment">
      <div class="field">
          <div class="ui left icon input">
              <i class="phone icon"></i>
              <input type="test" id="phone" name="phone" placeholder="phone">
          </div>
      </div>

      <div class="two fields ui mini attached stackable">

          <div class="ui field  input">
              <input type="text" name="mobeilcode" placeholder="请输入验证码" id="getcode">
          </div>

          <div class="field fluid ui container ">
              <input class="ui primary time button large"  type="button" value="获取验证码" id="click" onclick="foo(this, 60);" />
          </div>

      </div>
        <div class="field">
          <div class="ui left icon input">
            <i class="user icon"></i>
            <input type="text" name="username" id="username" placeholder="用户名">
          </div>
        </div>
          
        <div class="field">
          <div class="ui left icon input">
            <i class="at icon"></i>
            <input type="text" name="email" id="email" placeholder="email">
          </div>
        </div>

        <div class="field">
          <div class="ui left icon input">
            <i class="lock icon"></i>
            <input type="password" name="password" placeholder="密码">
          </div>
        </div>

        <div class="field">
          <div class="ui left icon input">
            <i class="lock icon"></i>
            <input type="password" name="password2" placeholder="确认密码">
          </div>
        </div>

        <div class="ui fluid large teal submit button">确定注册</div>
      </div>


      <div class="ui error message"></div>

    </form>


  </div>
</div>
</body>
<script>
function stringToJson(stringValue)
{
    eval("var theJsonValue = "+stringValue);
    return theJsonValue;
}
function foo(obj, time) {
    var flag = 0;
    var Phone = document.getElementById("phone").value;
    var register = document.getElementById("register")
    if(Phone==''||Phone=='null'){
        $('.ui.form').form('add prompt', "phone", "请输入手机号");
        document.getElementById('phone').placeholder="请输入手机号";
    }else {
        $.get({
            type: "get", //提交方式
            data: { "phone": Phone },
            url: "/getmobilecode", //路径
            success: function(data) { //返回数据根据结果进行相应的处理
                jsondata=stringToJson(data);
                if (jsondata.code == 0) {
                    $('.ui.form').form('add prompt', "phone", "手机号错误");
                    $('#phone').val('');
                    document.getElementById('phone').placeholder=jsondata.message;
                }else {
                    obj.disabled = true;
                    var x = setInterval(function() {
                        time = time - 1;
                        obj.value = jsondata.message+"  "+(time);
                        if (time == 0) {
                            clearInterval(x);
                            obj.value = "获取验证码";
                            obj.disabled = false;
                        }
                    }, 1000);
                }

            }
        });
    }
}
</script>

</html>