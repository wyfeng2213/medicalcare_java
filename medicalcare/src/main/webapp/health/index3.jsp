<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
<c:set var="ctx" value="<%=basePath%>" />
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<title>粤健康</title>
<link rel="stylesheet" href="css/style.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/touch.js"></script>
<script src="js/function.js"></script>
</head>
<style>
html, body {
	height: 100%
}

.containe {
	min-height: 100%;
}
</style>
<body>
	<div class="containe">
		<div id="tabBox" class="tabBox">
			<div class="content-main">
				<div class="tab hd">
					<ul id="column">

					</ul>
				</div>
				<div class="list bd" id="bd-auto"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$
				.get(
						"${ctx}/app/science/selectScinece.action",
						function(data, status) {
							if (data.code === 200) {
								var list = data.data.list;
								for (var i = 0; i < list.length; i++) {
									//alert(list[i].id);
									$("#column").append(
											"<li><a href='#"+i+"'>"
													+ list[i].columnHeading
													+ "</a><i></i></li>");
									var list2 = list[i].scienceArticleList;
									//alert("list2:"+list2.length);
									$("#bd-auto")
											.append(
													"<div class=\"con\"> <ul id=\""+list[i].id+"\"></ul></div>");
									if (list2 != null && list2.length > 0) {
										for (var k = 0; k < list2.length; k++) {
											//alert(list[i].columnHeading);

											$("#" + list[i].id)
													.append(
															"<li>"
																	+ "<div class=\"img\"><img src=\""+list2[k].imageUrl+"\"></div>"
																	+ "<div class=\"text\">"
																	+ "<h3>"
																	+ list2[k].title
																	+ "</h3>"
																	+ "<div class=\"info\">"
																	+ list2[k].subheading
																	+ "</div>"
																	+ "<span class=\"time\">来源："
																	+ list2[k].source
																	+ "</span> </div>"
																	+ "<a class=\"link\" href=\""+list2[k].articleUrl+"\" target=\"_self\"></a> </li>");
										}
									}

								}
								
								function changepage(i) {
									i = parseInt(i + '');
									var bd = document.getElementById("bd-auto");
									bd.parentNode.style.height = bd.children[i].children[0].offsetHeight
											+ "px";
									if (i > 0)
										bd.parentNode.style.transition = "500ms";

								}

								var theSelectIndex = location.hash || "0";
								defaultIndex = parseInt(theSelectIndex.substr(1, 3));
								TouchSlide({
									defaultIndex : defaultIndex,
									slideCell : "#tabBox",
									effect : "left",
									endFun : function(i) {
										changepage(i);
									}
								});

								$('a').click(
												function() {
													if ($(this).attr('href') != 'javascript:void(0)') {
														parent.showBar();
													}

												});

							}

						});

		parent.hideBar();
	</script>
</body>
</html>