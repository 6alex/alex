<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <!DOCTYPE HTML>
<html>
    <head>
      <title>文件上传</title>
    </head>
    
    <body>
    <div>${msg }</div>
     <form action="doUpload" enctype="multipart/form-data" method="post">
             <!-- 上传用户：<input type="text" name="username"><br/> -->
        上传文件1：<input type="file" name="file1"><br/>
         上传文件2：<input type="file" name="file2"><br/>
         <input type="submit" value="提交">
     </form>
     <a href="filelist">跳转---文件下载</a>
   </body>
 </html>