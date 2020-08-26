<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>500错误</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/default/js/lib/jquery.min.js"></script>
<style type="text/css">
<!--
* { margin:0; padding:0 }
h2{font: bold 48px 黑体, Helvetica, sans-serif ;padding-left:36px;color:#6d808e}
#cont{ border:1px solid #ccc;margin-top:2px;padding:50px 20px}
-->
html,body{
	background:url("<%=request.getContextPath()%>/console/default/images/sysbg.png") center center repeat;
}
.content{
	width:570px;
	height:290px;
	background:url("<%=request.getContextPath()%>/console/default/images/500_logo.png") no-repeat;
	margin:0 auto;
	margin-top:47px;
	padding-left:367px;
	position:relative;
}
.errortype{
	padding-top:57px;
}
.errortype .wordfirst{
	font-size:140.34px;
	color:#f14602;
	font-family:Arial;
	text-shadow:-7px 0px 0px #c9d0dc;
}
.errortype .pill{
	background:url("<%=request.getContextPath()%>/console/default/images/pill.png") center center no-repeat;
	height:68px;
	width:69px;
	display:inline-block;
}
.left{
	float:left;
}
.right{
    float:right;
}
.text1{
	font-size:60px;
	font-family:Century Gothic;
	color:#abb2bd;
	text-shadow:-7px 0px 0px #c9d0dc;
}
.errorreason{
	color:#34495e;
	font-size:38px;
	font-family:微软雅黑;
	font-weight:bold;
	position:absolute;
	bottom:50px;
	right:0px;
}
.clear{
	clear:left;
}
.button{
	width:900px;
	margin:0 auto;
	height:55px;
}
.button button{
	float:right;
	background:#768693;
	width:88px;
	height:32px;
	text-align:center;
	line-height:32px;
	border:none;
	margin-left:33px;
	margin-top:13px;
	color:#fff;
	cursor:pointer;
}
.button button:hover{
	background:#415464;
}
.detail{
	display:none;
	width:900px;
	background:#fff;
	word-break: break-all;
    word-wrap: break-word;
    overflow:auto;
    margin:0 auto;
    height:370px;
    padding:30px 0px 0px 40px;
}
</style>
<script type="text/javascript">
	$(function(){
		$("button.view").click(function(){
		   var text=$(".detail").text();
		   text=text.replace(/[)]/g,")<br/>");
		   $(".detail").html(text);
		   $(".detail").slideToggle("slow");
		});
		$(".button button.index").click(function(){
		  window.open("<%=request.getContextPath()%>/console/jsp/index.jsp","_self");
		});
		$(".button button.up").click(function(){
		  history.go(-1);
		});
	});
</script>
</head>
<body>
<div id="main">
	 <div class="content">
	 	<div class="errortype">
	 		<div class="left">
		 		<p>
			 		<span class="wordfirst">5</span>
			 		<span class="wordsecond pill">0</span>
			 		<span class="wordthird pill">0</span>
		 		</p>
	 		</div>
	 		<div class="left" style="padding-left:5px;padding-top:13px;">
		 		<p class="text1" style="margin-bottom:-13px;">Error</p>
		 		<p class="text1">HTTP Status</p>
	 		</div>
	 		<div class="clear"></div>
	 	</div>
	 	<div class="errorreason">
	 		系统内部错误，请联系系统管理员
	 	</div>
	 </div>
	 <div class="button">
	 	<button class="view">查看</button>
	 	<button class="up">返回上一页</button>
	 	<button class="index">返回首页</button>
	 </div>
	 <div class="detail">
		java.lang.NoSuchMethodException: com.goodwill.cdr.console.dictionary.web.action.DictAction.query1()
		java.lang.Class.getMethod(Unknown Source)
		org.apache.struts2.interceptor.validation.AnnotationValidationInterceptor.getActionMethod(AnnotationValidationInterceptor.java:75)
		org.apache.struts2.interceptor.validation.AnnotationValidationInterceptor.doIntercept(AnnotationValidationInterceptor.java:47)
		com.opensymphony.xwork2.interceptor.MethodFilterInterceptor.intercept(MethodFilterInterceptor.java:98)
		com.opensymphony.xwork2.DefaultActionInvocation.invoke(DefaultActionInvocation.java:249)
		com.opensymphony.xwork2.interceptor.ConversionErrorInterceptor.intercept(ConversionErrorInterceptor.java:138)
		com.opensymphony.xwork2.DefaultActionInvocation.invoke(DefaultActionInvocation.java:249)
		com.opensymphony.xwork2.interceptor.ParametersInterceptor.doIntercept(ParametersInterceptor.java:211)
	</div>
</div>
</body>
</html>
