<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page language="java"  import="com.goodwill.security.utils.UrlAccessAuthentication"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}" />
 --%>
<script  src="<%=request.getContextPath()%>/js/lib/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/jquery.validate.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/passmod.css" />
<!-- 密码修改层 -->
<div id="passPanel" class="passmod" >
	 <form id="passModForm"  action="" >
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tbody>
			<tr><td><label>输入原密码：</label><input type="password" placeholder="输入正确的原密码" value="" name="originalPassword" /></td></tr>
			<tr><td><label>输入新密码：</label><input type="password" placeholder="输入新密码"  value="" name="testPass" /></td></tr> 
			<tr><td><label>确认新密码：</label><input type="password" placeholder="再次输入新密码" value="" name="password" /></td></tr>
	   	</tbody>
	   	</table>				 				 
	 </form>
</div>
<script type="text/javascript">
$(function(){
	/**
	*密码修改绑定
	*/	  
	var passPanel=$("#passPanel"),passModForm=passPanel.find("form");
	passModForm.validate({
		rules: {
			originalPassword:{
				required:true,
				sPass:true 
			},
			testPass:{  
				required:true,
				sPass:true,
				notEqualTo:"input[name=originalPassword]"
			},
			password:{  
				required:true,
				sPass:true,
				equalTo:"input[name=testPass]"
			}
		},
		messages: {
			originalPassword:{
				required:"原密码不能为空"
			},
			testPass:{
				required:"新密码不能为空",
				notEqualTo:"新密码不能与原密码一样"
			},
			password:{
				required:"确认密码不能为空",
				equalTo:"两次输入的密码不一致"
			}
		}
	});
	passModForm.ajaxForm({
		   url:"<%=request.getContextPath()%>/console/control/user_changePwd.action",
		   dataType:"json",
		   type:"post",
		   beforeSubmit:function(arr, $form, options) {													 
			   passPanel.showLoading("processingdiv");
		   },
		   success:function(data){
			   if(data.status=="1"){
				  passPanel.dialog("close");
				  $.alert("修改成功！");
			   }else{
				  $.alert(data.msg||"修改失败！");
			   }
		   },error:function(){
			   $.alert("修改失败！");
		   },complete:function(){
			   passPanel.hideLoading("processingdiv");
		   }
	});
   passPanel.dialog({
		title:"密码修改",
		width:300,	 
		autoOpen:false,
		open:function(){
		   $(this).find("input").val("").next().hide();
		   if($("div.ui-widget-overlay").length==0){
			   $('<div class="ui-widget-overlay"></div>').appendTo("body");
			   passPanel.closest(".ui-dialog").css({"z-index":"1002",});
		   }
		   $("div.ui-widget-overlay").css({"position":"absolute","z-index":"1001","width":"100%","height":$(window).height(),"top":"0","left":"0"});
		   funShowDivCenter(".div.ui-widget-overlay");
		},		
		buttons:[{
		   text:"确认",
		   click:function(){
			   $(".ui-widget-overlay").remove();
		   if($(this).find(".processingdiv:visible").length)return; //请求已经发出
			  passModForm.submit();
		   }
		},{
		   text:"取消",
		   click:function(){
			 $(this).dialog("close");
			 $(".ui-widget-overlay").remove();
		   }
		}]		   
	});	
   /*
   * 添加用户的操作
   */
   if($(".user").length>0 && $(".opertlist").length==0){
		var html="";
		html = '<div class="operlist">'
					+' <div class="changepassword">'
					+'		<a href="#" id="passMenu">修改密码</a>'
					+'	 </div>'
					+'	 <div class="logout">'
					+'		<a href="${pageContext.request.contextPath}/j_spring_security_logout">退出系统</a>'
					+'	 </div>'
					+'</div>'
		//console.log(html);
		$(".user").append(html);
	}
	$("#passMenu").click(function(e){
	   e.preventDefault();
		  passPanel.dialog("open");
	});
	
	
	
});
//居中显示弹出层
function funShowDivCenter(div) {
      var top = ($(window).height() - $(div).height()) / 2;
     var left = ($(window).width() - $(div).width()) / 2;
      var scrollTop = $(document).scrollTop();
      var scrollLeft = $(document).scrollLeft();
      $(div).css({ position: 'absolute', 'top': top + scrollTop, left: left + scrollLeft }).show();
}
 //</script>