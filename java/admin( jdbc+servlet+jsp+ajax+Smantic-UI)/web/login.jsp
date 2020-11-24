<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome to CMS!</title>
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


        .codeimg{
            height: 95%;
            width: 100%;
            border-radius:5px;
        }
    </style>
</head>
<body>
<script type="text/javascript">

    function stringToJson(stringValue)
    {
        eval("var theJsonValue = "+stringValue);
        return theJsonValue;
    }

    $(document).ready(function() {

        $('.ui.form').form({
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
                imgObj: {
                    identifier  : 'imgObj',
                    rules: [
                        {
                            type   : 'empty',
                            prompt : '请输入验证码'
                        },
                    ]
                }
            }
        });

        $("#formLogin").ajaxForm(function(data){
            jsondata = stringToJson(data);
            if(jsondata.code==0) {
                $('#resp').val('');
                document.getElementById('resp').placeholder=jsondata.message;
                changeImg();
            }else {
                window.location.href="index.jsp";
            }
        });

    });




    function changeImg() {
        var imgSrc = $("#imgObj");
        var src = imgSrc.attr("src");
        imgSrc.attr("src", chgUrl(src));
    }

    // 时间戳
    // 为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
    function chgUrl(url) {

        var timestamp = (new Date()).valueOf();
        url = url.substring(0, 20);
        if ((url.indexOf("&") >= 0)) {
            url = url + "×tamp=" + timestamp;
        } else {
            url = url + "?timestamp=" + timestamp;
        }
        return url;
    }

</script>

<div class="ui middle aligned center aligned grid">
    <div class="column">
        <h1 class="ui teal image header">

            <div class="content">
                <h1>   Welcome to CMS!</h1>
            </div>
        </h1>

        <form class="ui large form" method="post" id="formLogin" name="formLogin" action="/checkCode">

            <div class="ui stacked segment">

                <div class="required field">
                    <div class="ui left icon input">
                        <i class="user icon"></i>
                        <input type="text" name="username" placeholder="Username">
                    </div>
                </div>

                <div class="required field">
                    <div class="ui left icon input">
                        <i class="lock icon"></i>
                        <input type="password" name="password" placeholder="Password">
                    </div>
                </div>

                <div class="two fields ui mini attached stackable">

                    <div class="ui field input">
                        <input type="text" id="resp" name="code" placeholder="输入验证码,点击切换">
                    </div>

                    <div class="field fluid ui container ">
                        <image onclick="changeImg()" class="codeimg" id="imgObj" src="${pageContext.request.contextPath}/getCode"/>
                    </div>

                </div>

                <button type="submit" class="ui fluid large teal submit button">开始使用</button>
            </div>

            <div class="ui error message"></div>
        </form>

        <div class="ui message">
            新用户？ <a href="regist.jsp">注册</a>
        </div>
    </div>
</div>
</body>
</html>



