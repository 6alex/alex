<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
</head>
<body>
	<div id="footer">${msg }哈哈哈</div>
	<!-- <a href="./cover/login.jsp">跳转......1</a>
	<a href="./cover/login2.jsp">跳转......2</a> -->
	<a href="upload">跳转......文件上传1</a>
	<span id="f">跳转......文件上传</span>
	<span></span>
	<script src="scripts/jquery-3.3.1.min.js"></script>
	<script type="text/javascript">
		var words = new Array("\u5bcc\u5f3a", "\u6c11\u4e3b", "\u6587\u660e",
				"\u548c\u8c10", "\u81ea\u7531", "\u5e73\u7b49", "\u516c\u6b63",
				"\u6cd5\u6cbb", "\u7231\u56fd", "\u656c\u4e1a", "\u8bda\u4fe1",
				"\u53cb\u5584");
		var a_idx = 0;
		$("body").click(function(e) {
			var $i = $("<span></span>").text(words[a_idx]);
			a_idx = (a_idx + 1) % words.length;
			var x = e.pageX, y = e.pageY;
			$i.css({
				"z-index" : 9999,
				"top" : y - 36,
				"left" : x + 10,
				"position" : "absolute",
				"font-weight" : "bold",
				"color" : "#ff6651"
			});
			$("body").append($i);
			$i.animate({
				"top" : y - 180,
				"opacity" : 0
			}, 1500, function() {
				$i.remove();
			});

		});
		
		$("#f").click(function () {
			window.open("upload");
		});
	</script>
</body>
</html>
