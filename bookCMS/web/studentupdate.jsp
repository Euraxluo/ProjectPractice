<%--
  Created by IntelliJ IDEA.
  User: euraxluo
  Date: 18-12-20
  Time: 下午7:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>更新学生信息</title>
</head>
<body>
<h3>更新学生界面</h3>
<form method="post" action="UpdateStudentServlet">
    <input type="hidden" name="sid" value="${stu.sid}">
    <table border="1" width="600">
        <tr>
            <td>姓名</td>
            <td><input type="text" name="sname" value="${stu.sname}"></td>
        </tr>
        <tr>
            <td>性别</td>
            <td>
            <input <c:if test="${stu.gender=='男'}">checked</c:if> type="radio" name="gender" value="男">男
            <input <c:if test="${stu.gender=='女'}">checked</c:if> type="radio" name="gender" value="女">女
            </td>
        </tr>
        <tr>
            <td>电话</td>
            <td><input type="text" name="phone" value="${stu.phone}"></td>
        </tr>
        <tr>
            <td>生日</td>
            <td><input type="text" name="birthday" value="${stu.birthday}"></td>
        </tr>
        <tr>
            <td>爱好</td>
            <td>
                <!--fn:contains(stu.hobby,'游泳')->boolean 用于确定一个字符串是否包含指定的子串-->
                <input <c:if test="${fn:contains(stu.hobby,'游泳')}">checked</c:if> type="checkbox" name="hobby" value="游泳">游泳
                <input <c:if test="${fn:contains(stu.hobby,'篮球')}">checked</c:if> type="checkbox" name="hobby" value="篮球">篮球
                <input <c:if test="${fn:contains(stu.hobby,'足球')}">checked</c:if> type="checkbox" name="hobby" value="足球">足球
                <input <c:if test="${fn:contains(stu.hobby,'看书')}">checked</c:if> type="checkbox" name="hobby" value="看书">看书
                <input <c:if test="${fn:contains(stu.hobby,'写字')}">checked</c:if> type="checkbox" name="hobby" value="写字">写字
            </td>
        </tr>
        <tr>
            <td>简介</td>
            <td><textarea name="info" cols="60" rows="3">${stu.info}</textarea> </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="更新">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
