<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="<%=request.getContextPath()%>/default/images/favicon.ico" type="image/x-icon" />
<link rel="icon" type="image/gif" href="<%=request.getContextPath()%>/default/images/favicon.gif" />
<link href="<%=request.getContextPath()%>/default/images/favicon.png" rel="apple-touch-icon" />
<title>没有权限，拒绝访问</title>
<script type="text/javascript">     
		function countDown(secs,surl){     
		 //alert(surl);     
		 var jumpTo = document.getElementById('jumpTo');
		 jumpTo.innerHTML=secs;  
		 if(--secs>0){     
		     setTimeout("countDown("+secs+",'"+surl+"')",1000);     
		     }     
		 else{       
		     location.href=surl;     
		     }     
		 }     
</script> 
</head>
<body>
没有权限，拒绝访问。
<span id="jumpTo">5</span>秒后自动 <a href="javascript:history.go(-1);">返回到上一页</a> 
<script type="text/javascript">countDown(5,"javascript:history.go(-1);");</script> 
</body>
</html>