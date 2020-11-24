<%--
  Created by IntelliJ IDEA.
  User: euraxluo
  Date: 19-1-1
  Time: 下午11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>

<h3>springMVC上传图片</h3>
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file"/>
    <input type="submit" value="上传文件"/>
</form>

<h3>富文本上传</h3>
<form name="form2" action="/manage/product/richtest_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file"/>
    <input type="submit" value="上传文件"/>
</form>

</body>
</html>
