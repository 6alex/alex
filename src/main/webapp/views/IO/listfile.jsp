<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>下载文件显示页面</title>
  </head>
  
  <body>
      <!-- 遍历Map集合 -->
     <ul style="width:1000px;margin: auto;">
    <c:forEach var="me" items="${fileNameMap}">
       <li> <c:url value="download" var="downurl">
            <c:param name="filename" value="${me.key}"></c:param>
        </c:url>
        ${me.value}<a href="${downurl}">下载</a>
        <br/>
        </li>
    </c:forEach>
    </ul>
  </body>
</html>