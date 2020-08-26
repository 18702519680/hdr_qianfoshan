<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java"  import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%		    
org.springframework.security.core.context.SecurityContext context = org.springframework.security.core.context.SecurityContextHolder.getContext();
org.springframework.security.core.Authentication authentication = context.getAuthentication();
if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))){//如果是ajax请求
	if("anonymousUser".equals(authentication.getPrincipal())) {//未登陆用户
		response.setStatus(608); //这里自定义一个状态608 与common.js中的ajax全局设置保持一致	
	}else if("1".equals(request.getParameter("error"))){ //authentication-failure
		response.setStatus(403); //没有权限
	}
    response.getWriter().write(request.getRequestURI());		
    return;
}else if(!"anonymousUser".equals(authentication.getPrincipal())) {//登陆用户 跳转至默认页面 
    response.sendRedirect(request.getContextPath()+"/");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>登录 - 数据上报</title> 
<link rel="shortcut icon"  type="image/x-icon"  href="<%=request.getContextPath()%>/images/icon_upv.ico"/>
<link rel="icon" type="image/gif" href="<%=request.getContextPath()%>/images/icon_upv.gif" />
<link rel="apple-touch-icon"  href="<%=request.getContextPath()%>/images/icon_upv..png"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css?20140731"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/jquery.supportPlaceholder.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js?20140415"></script>
<script type="text/javascript"> 
   if(top!=self)top.location.reload();
   $(function(){
	   if($(window).height()>565){
		   $(".copyright").css("position","absolute");
	   }
	   window.onresize=function(){
		   var h=$(window).height();
		   if(h>565){
			   $(".copyright").css("position","absolute");
		   }
		   else{
			   $(".copyright").css("position","");
		   }
	   };
     /**
      *错误提示处理
      */
      var errorMsgArea=$(".errorMsg");
      function showMsg(msg){ //展示提示信息
         if(msg==undefined || msg==""){
            errorMsgArea.hide();
         }else{
            errorMsgArea.text(msg).show();
         }
      }
      
      var $form=$("form#loginForm"),
  		$name=$form.find("input#username").val($.cookie("last-username")),
  		$pass=$form.find("input#password");
      ($.cookie("last-username")?$pass:$name).focus();//输入框默认聚焦
      $form.submit(function(){        
        if($name.val()==""){
           showMsg("请输入用户名!"); 
           errorInput($name);
           return false;
        }else if($pass.val()==""){
           showMsg("请输入密码!"); 
           errorInput($pass);
           return false;
        }else showMsg("");
        $.cookie("last-username",$name.val(),{expires:1,path:"<%=request.getContextPath()%>"});//保存最后一次的用户名
        $(this).find("input[type=submit]").prop("disabled",true).prevAll().prop("readonly",true);        
     }).find("input[type=submit]").prop("disabled",false).prevAll().blur(function(){       
        this.value=$.trim(this.value);
     }).supportPlaceholder().prop("readonly",false);
     
     function errorInput($input){
       $input.addClass("error").one("keyup",function(){$(this).removeClass("error");}).focus();
     }
     <%if ("1".equals(request.getParameter("error"))) {%>
	   var msg="<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>";
	   if(msg.indexOf("用户名")>=0){
	      errorInput($name);
	   }else if(msg.indexOf("密码")>=0){
	      errorInput($pass);
	   }
	 <%}%>
	 <%if ("1".equals(request.getParameter("logout"))) {%>
	 	$pass.valule="";
	 	$(".layerOver").show();
	 	funShowDivCenter(".logout_success");
	 	setTimeout(function(){
	 		$(".layerOver").hide();
		 	$(".logout_success").hide();
	 	},1000);
	 <%}%>
		  
   });
   function funShowDivCenter(div) {
	         var top = ($(window).height() - $(div).height()) / 2;
	         var left = ($(window).width() - $(div).width()) / 2;
	         var scrollTop = $(document).scrollTop();
	         var scrollLeft = $(document).scrollLeft();
	         $(div).css({ position: 'absolute', 'top': top + scrollTop, left: left + scrollLeft }).show();
	  }
</script>
</head>

<body>
 	<div class="head">
      	<div class="logo upv" ></div>
    </div>
    <div class="loginArea">
       	<!-- <h3>Hospital Single Disease Quality</h3> -->
        <div class="form">
			<form method="post" id="loginForm" class="loginForm_css"
				action="<%=request.getContextPath()%>/j_spring_security_check" autocomplete="off">		
				   <div class="form_left">
					   <input type="text" name="j_username" id="username" placeholder="用户名" autocomplete="off"/>
					   <input type="password" name="j_password" id="password"  placeholder="密码" autocomplete="off"/>
				   </div>
				   <div class="form_submit">
				   		<input type="submit" id="submit" value="登 录"/>
				   </div>
				   <div class="clearboth"></div>
			</form>
	    </div>
        <div class="errorMsg" style="display:<%="1".equals(request.getParameter("error"))?"block":"none"%>">
          <%if ("1".equals(request.getParameter("error"))) {%>
			 <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
		  <%}%>
        </div>	 
              
    </div>
    <div class="copyright"><!-- powered by HDUAP,北京嘉和美康信息技术有限公司 --></div>
  <!--   /** layerover  **/ -->
    <div class="layerOver"></div>
    <!-- <div class="logout_success">
    	<div class="logout_text">注销成功</div>
    </div> -->
</body>
</html>