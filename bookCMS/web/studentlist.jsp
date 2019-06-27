<%--
  Created by IntelliJ IDEA.
  User: euraxluo
  Date: 18-12-18
  Time: 下午6:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>学生列表界面</title>
</head>
<body>
<form action="SearchStudentServlet" method="post">
    <table border="1" width="700">
        <tr>
            <td colspan="8">

                按姓名查询:<input type="text" name="sname"/>
                &nbsp;
                按性别查询:<select name="sgender">
                <option value="">--请选择--
                <option value="男">男
                <option value="女">女
            </select>
                &nbsp;&nbsp;&nbsp;
                <input type="submit" value="查询">
                &nbsp;&nbsp;&nbsp;
                <a href="studentadd.jsp">添加</a>
            </td>
        </tr>
        <tr align="center">
            <td>编号</td>
            <td>姓名</td>
            <td>性别</td>
            <td>电话</td>
            <td>生日</td>
            <td>爱好</td>
            <td>简介</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${pageBean.list}" var="stu">
            <tr>
                <td>${stu.sid}</td>
                <td>${stu.sname}</td>
                <td>${stu.gender}</td>
                <td>${stu.phone}</td>
                <td>${stu.birthday}</td>
                <td>${stu.hobby}</td>
                <td>${stu.info}</td>
                <td><a href="EditStudentServlet?sid=${stu.sid}">更新</a>  <a href="#" onclick="doDelete(${stu.sid})">删除</a></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="8">
                第 ${pageBean.currentPage } / ${pageBean.totalPage }
                &nbsp;&nbsp;
                每页显示${pageBean.pageSize }条  &nbsp;&nbsp;&nbsp;
                总的记录数${pageBean.totalSize } &nbsp;&nbsp;&nbsp;
                <c:if test="${pageBean.currentPage !=1 }">
                    <a href="StudentListServlet?currentPage=1">首页</a>
                    | <a href="StudentListServlet?currentPage=${pageBean.currentPage-1 }">上一页</a>
                </c:if>

                <c:forEach begin="1" end="${pageBean.totalPage }" var="i">
                    <c:if test="${pageBean.currentPage == i }">
                        ${i }
                    </c:if>
                    <c:if test="${pageBean.currentPage != i }">
                        <a href="StudentListServlet?currentPage=${i }">${i }</a>
                    </c:if>

                </c:forEach>


                <c:if test="${pageBean.currentPage !=pageBean.totalPage }">
                    <a href="StudentListServlet?currentPage=${pageBean.currentPage+1 }">下一页</a> |
                    <a href="StudentListServlet?currentPage=${pageBean.totalPage }">尾页</a>
                </c:if>
            </td>
        </tr>
    </table>
</form>
    <script type="text/javascript">

        function doDelete(sid) {
            /* 如果这里弹出的对话框，用户点击的是确定，就马上去请求Servlet。
            如何知道用户点击的是确定。
            如何在js的方法中请求servlet。 */
            var flag = confirm("是否确定删除?");
            if(flag){
                //表明点了确定。 访问servlet。 在当前标签页上打开 超链接，
                //window.location.href="DeleteServlet?sid="+sid;
                location.href="DeleteStudentServlet?sid="+sid;
            }
        }
    </script>

</body>
</html>
