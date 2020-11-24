
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title></title>

    <link href="../static/css/table.css" rel="stylesheet" />
    <%--<link href="../static/layer/skin/default/layer.css" rel="stylesheet" /> --%>
    <link rel="stylesheet" href="../static/semantic-ui/semantic.min.css">
    <script type="text/javascript" src="../static/js/jquery.min.js"></script>
    <script type="text/javascript" src="../static/js/jquery.form.js"></script>
    <script type="text/javascript" src="../static/semantic-ui/semantic.min.js"></script>
    <script type="text/javascript" src="../static/js/myPage/jqPaginator.min.js"></script>
    <style>
        tbody img{
            width: 70px;
            height: 85px;
            display：block;
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

</script>

    <div class="page">
        <!--搜索条件-->
        <div class="ui form equal width" action="/SearchPhone" method="post">
            <div class="fields">
                <div class="field">
                <input type="text" name="id" id="idss" placeholder="编号">
                </div>
                <div class="field">
                <input type="text" name="phoneName" id="phoneNamess" placeholder="手机名称">
                </div>

                <div class="field">
                <input type="text" name="theme" id="themess" placeholder="特点">
                </div>

                <div class="field">
                <input type="text" name="nums" id="numsss" placeholder="库存">
                </div>
                <div class="field">
                    <button class="ui primary button" type="submit" id="searchbth" ><i class="search icon"></i>搜索</button>
                </div>
            </div>
        </div>

        <!--工具栏-->
        <div class="tab-tool">
            <!--纵向：vertical 反色：blue inverted-->
            <div class="ui fluid icon menu">
                <!--<div class="header item">工具栏</div>-->
                <a class="item"><i class="add icon"></i><span>添加</span></a>
                <a class="item"><i class="edit icon"></i><span>编辑</span></a>
                <a class="item" onclick="doDelete()" ><i class="remove icon"></i><span>删除</span></a>
                <a class="item right floated search-up" data-up="0"><i class="chevron up icon"></i><span>搜索条件</span></a>
            </div>
        </div>
        <!--内容-->
        <div class="tab-content">
            <!--列表内容-->
            <table class="ui compact selectable celled table mini">
                <colgroup>
                    <col width="60" />
                    <col width="60" />
                </colgroup>
                <thead>
                    <tr>
                        <th></th>
                        <th>序号</th>
                        <th>手机名称</th>
                        <th>手机主题</th>
                        <th>图片</th>
                        <th>库存</th>
                        <th>创建时间</th>
                        <th>是否上架</th>
                        <th>是否刪除</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pageBean.list}" var="phone" varStatus="varStatus">
                        <tr>
                            <td>
                                <div class="ui checkbox" id="${phone.id}">
                                    <input type="checkbox" name="example">
                                    <label></label>
                                </div>
                            </td>
                            <td>${varStatus.count+5*pageBean.currentPage-5}</td>
                            <td>${phone.phoneName}</td>
                            <td>${phone.theme}</td>
                            <td>
                                        <img src="${phone.url}"/>
                            </td>
                            <td>${phone.nums}</td>
                            <td>${phone.createTime}</td>
                            <td>
                                <div class="${phone.isShow==1?'ui fitted toggle checkbox checked':'ui fitted toggle checkbox'}">
                                    <input type="checkbox" tabindex="1" class="hidden">
                                </div>
                            </td>
                            <td>
                                <div class="${phone.isdelte==1?'ui fitted toggle checkbox checked':'ui fitted toggle checkbox'}">
                                    <input type="checkbox">
                                </div>
                            </td>

                            <%--onclick="doDelete(${stu.sid})--%>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!--分页-->
        <div class="tab-page">
            <!--每页多少条-->
            <div class="ui mini left borderless floated menu">
                <div class="item">
                    每页
                </div>
                <div class="ui pointing dropdown link item btnpagesize">
                    <span class="text">${pageBean.pageSize }</span>
                </div>
                <div class="item labeled green">
                    条
                </div>
            </div>
            <!--分页列表-->

            <div>
                <div class="ui mini left floated pagination menu" id="pagination">
                </div>
                <input type="hidden" id="PageCount" runat="server" />
                <input type="hidden" id="PageSize" runat="server" value="8" />
                <input type="hidden" id="countindex" runat="server" value="10" />
                <!--设置最多显示的页码数 可以手动设置 默认为7-->
                <input type="hidden" id="visiblePages" runat="server" value="7" />
            </div>


            <div class="ui mini left floated pagination menu ">


                <div class="item fitted">
                    <c:if test="${pageBean.currentPage !=1 }">
                        <a class="item" href="/GetPhoneList?currentPage=1">首页</a>
                        <a href="/GetPhoneList?currentPage=${pageBean.currentPage-1 }" class="icon item"><i class="left chevron icon"></i></a>
                    </c:if>
                </div>
                <div class="item fitted">
                    <c:if test="${pageBean.currentPage!=1}">
                        <a class="item disabled">...</a>
                    </c:if>
                <c:forEach begin="1" end="${pageBean.totalPage }" var="i">


                    <c:if test="${pageBean.currentPage == i }">
                        <a class="item active">${i }</a>
                    </c:if>
                    <c:if test="${pageBean.currentPage-i==1 || i-pageBean.currentPage==1 }">
                        <a class="item"  href="/GetPhoneList?currentPage=${i}">${i}</a>
                    </c:if>


                </c:forEach>
                    <c:if test="${pageBean.currentPage!=pageBean.totalPage }">
                        <a class="item disabled">...</a>
                    </c:if>
                </div>

                <div class="item fitted">
                <c:if test="${pageBean.currentPage !=pageBean.totalPage }">
                    <a class="icon item" href="/GetPhoneList?currentPage=${pageBean.currentPage+1 }"><i class="right chevron icon"></i></a>
                    <a class="item" href="/GetPhoneList?currentPage=${pageBean.totalPage }">尾页</a>
                </c:if>
                </div>

                <div class="item">
                    共${pageBean.totalSize}条&nbsp;/&nbsp;${pageBean.totalPage}页
                </div>

                <div class="item fitted">
                    <div class="ui input">
                        <input type="number" value="1" placeholder="1" class="txtSkip">
                    </div>
                </div>
                <a class="item labeled green">
                    <strong>跳转</strong>
                </a>


            </div>
        </div>
    </div>

    <script>


        function doDelete(){
            var arrayObj = new Array();
            $('input[name="example"]').each(function (i,item) {
                if(item.parentNode.classList.contains('checked')){
                    var ids = $(item.parentNode).attr('id')
                    arrayObj.push(ids)
                }
            })
            var str = arrayObj.join(',')
            console.log(str)
            $.get({
                type: "POST", //提交方式
                data: {'ids':str},
                url: "/Deletephone", //路径
                success: function(data) {
                    jsondata = stringToJson(data);
                    if (jsondata.code == 0) {
                        alert(jsondata.message)
                    }
                    if (jsondata.code != 0) {
                        alert(jsondata.message)
                        window.location.reload();
                    }
                }
            })
        }

        $('.ui.checkbox').checkbox();

        var clientwidth = document.body.clientWidth;

        $(document).ready(function () {
            function stringToJson(stringValue)
            {
                eval("var theJsonValue = "+stringValue);
                return theJsonValue;
            }


            $("#searchbth").click(function () {
                var idss = $("#idss").val() ;
                var phoneNamess = $("#phoneNamess").val() ;
                var themess = $("#themess").val() ;
                var numsss = $("#numsss").val() ;
                $.get({
                    type: "POST", //提交方式
                    data: {'id':idss,'phoneName':phoneNamess,'theme':themess,'nums':numsss},
                    url: "/SearchPhone", //路径
                    success: function(data) {
                        jsondata = stringToJson(data);
                        if (jsondata.code == 0) {
                            alert(jsondata.message)
                        }
                        if (jsondata.code != 0) {
                            window.location.reload();
                        }
                    }
                })
            });

            
            if (clientwidth <= 768) {
                $(".tab-page").css("position", "static");
                $(".tab-tool").children(".ui.menu").removeClass("fluid").addClass("compact mini").find("span").hide(); //vertical
            }
            $(".jibie").dropdown();
            $(".btnpagesize").dropdown();
            //动态表格高度
            heightauto();

            $(".search-up").click(function () {
                var dataup = $(this).data("up");
                if (dataup == 0) {
                    $(".tab-search").height(0);
                    $(this).data("up", "1");
                    $(this).find("i.icon").removeClass("up").addClass("down");
                    heightauto();

                } else {
                    $(".tab-search").css("height", "auto");
                    $(this).data("up", "0");
                    $(this).find("i.icon").removeClass("down").addClass("up");
                    heightauto();
                }

            });

            $(window).resize(function () {
                //动态表格高度
                heightauto();
                clientwidth = document.body.clientWidth;
            });

            <%--$.jqPaginator('#pagination', {--%>
                <%--totalPages: ${pageBean.totalPage },--%>
                <%--visiblePages: ${pageBean.currentPage },--%>
                <%--currentPage: ${pageBean.currentPage },--%>
                <%--first: '<a class="item">首页</a>',--%>
                <%--prev: '<a class="icon item"><i class="left chevron icon"></i></a>',--%>
                <%--next: '<a class="icon item"><i class="right chevron icon"></i></a>',--%>
                <%--last: '<a class="item">末页</a>',--%>
                <%--page: '<a class="item">{{page}}</a>',--%>
                <%--//onPageChange: function (num, type) {--%>
                <%--//    if (type == "change") {--%>
                <%--//        exeData(num, type);--%>
                <%--//    }--%>
                <%--//}--%>
            <%--});--%>
        });

        function add() {
            layer.open({
                type: 2,
                area: ['800px', '600px'],
                title: '添加',
                content: 'form.html'
            });
        }

        //动态表格高度
        function heightauto() {
            var a = document.body.clientHeight; //页面可见高度
            var b = $(".tab-search").outerHeight(); //条件搜索高度
            var c = $(".tab-tool").outerHeight(); //工具栏高度
            var d = $(".tab-page").outerHeight(); //分页高度

            //console.log("总高度：" + a);
            //console.log("总和：" + (b + c + d) + "/ 搜索:" + b + " / 工具：" + c + " / 分页：" + d);
            //console.log("结果：" + (a - (b + c + d)));

            $(".tab-content").height(a - (b + c + d + 20));
        }
    </script>
</body>
</html>
