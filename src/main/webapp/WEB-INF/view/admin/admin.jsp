<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--开启JSP的el表达式。在一些低版本的Servlet中会默认不开启--%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>学生信息管理系统后台</title>
    <link rel="shortcut icon" href="/h-ui/favicon.ico"/>
	<link rel="bookmark" href="/h-ui/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="/easyui/css/default.css" />
    <link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="/easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='/easyui/js/outlook2.js'> </script>
    <script type="text/javascript">
	 var _menus = {
         "menus": [
             <c:if test="${userType == 1}">
             {
                 "menuid": "1", "icon": "", "menuname": "用户(管理员)管理",
                 "menus": [
                     {
                         "menuid": "11",
                         "menuname": "用户管理",
                         "icon": "icon-man",
                         "url": "../user/list"}

                 ]
             },
             </c:if>

             {
                 "menuid": "3", "icon": "", "menuname": "年级信息管理",
                 "menus": [
                     {
                         "menuid": "21",
                         "menuname": "年级列表",
                         "icon": "icon-filter",
                         "url": "../grade/list"},
                 ]
             },
             {
                 "menuid": "4", "icon": "", "menuname": "班级信息管理",
                 "menus": [
                     {
                         "menuid": "31",
                         "menuname": "班级列表",
                         "icon": "icon-house",
                         "url": "../clazz/list"},
                 ]
             },

             {
                 "menuid": "5", "icon": "", "menuname": "学生信息管理",
                 "menus": [
                     {
                         "menuid": "41",
                         "menuname": "学生列表",
                         "icon": "icon-user-student",
                         "url": "../student/list"
                     },
                 ]
             },
             {
                 "menuid": "6", "icon": "", "menuname": "基础信息管理",
                 "menus": [
                     {
                         "menuid": "51",
                         "menuname": "年级列表",
                         "icon": "icon-world",
                         "url": ""
                     },
                     {
                         "menuid": "62",
                         "menuname": "班级列表",
                         "icon": "icon-house",
                         "url": ""
                     },
                     {
                         "menuid": "43",
                         "menuname": "课程列表",
                         "icon": "icon-book-open",
                         "url": ""
                     }
                 ]
             },
             {
                 "menuid": "7", "icon": "", "menuname": "系统管理",
                 "menus": [
                     {
                         "menuid": "61",
                         "menuname": "系统设置",
                         "icon": "icon-set",
                         "url": ""
                     },
                 ]
             }
         ]};
    </script>

</head>

<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;background: #7f99be;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head"><span style="color:red; font-weight:bold;">${userInfo.username}&nbsp;</span>您好&nbsp;&nbsp;&nbsp;
            <a href="/System/logout" id="logout">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; ">学生信息管理系统</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">Copyright &copy; SWU By MirFang </div>
    </div>
    <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
	<div id="nav" class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->
	</div>
	
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<jsp:include page="/WEB-INF/view/admin/welcome.jsp" />
		</div>
    </div>
</body>
</html>