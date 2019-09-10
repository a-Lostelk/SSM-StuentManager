<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>学生列表</title>
	<link rel="stylesheet" type="text/css" href="/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="/easyui/css/demo.css">
	<script type="text/javascript" src="/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {
        var clazzList = ${clazzJSON};

		//datagrid初始化
	    $('#dataList').datagrid({
	        title:'学生列表',
	        iconCls:'icon-more',//图标
	        border: true,
	        collapsible:false,//是否可折叠的
	        fit: true,//自动大小
	        method: "post",
	        url:"get_list?="+new Date().getTime(),
	        idField:'id',
	        singleSelect:false,//是否单选
	        pagination:true,//分页控件
	        rownumbers:true,//行号
	        sortName:'id',
	        // sortOrder:'DESC',
	        remoteSort: false,
            columns: [[
                {field: 'chk', checkbox: true, width: 50},
                {field: 'id', title: 'ID', width: 50, sortable: true},
                {field: 'studentNumber', title: '学号', width: 100, sortable: true},
                {field: 'username', title: '姓名', width: 100},
                {field: 'password', title: '密码', width: 150},
                {field: 'sex', title: '性别', width: 50},
                {field: 'phone', title: '联系方式', width: 150},
                {field: 'email', title: '邮箱', width: 150},
                {
                    field: 'clazzId', title: '班级', width: 150,
                    formatter:function(value){
                        for(var i=0;i<clazzList.length;i++) {
                            if (clazzList[i].id == value) {
                                return clazzList[i].name;
                            }
                        }
                        return value;
                    }
                },
                {
                    field: 'gradeId', title: '年级', width: 150,
                    formatter: function (value, row) {
                        if (row.grade) {
                            return row.grade.name;
                        } else {
                            return value;
                        }
                    }
                },
                {field: 'photo', title: '头像地址', width: 300},
            ]],
	        toolbar: "#toolbar"
	    });
	    //设置分页控件
	    var p = $('#dataList').datagrid('getPager');
	    $(p).pagination({
	        pageSize: 10,//每页显示的记录条数，默认为10
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表
	        beforePageText: '第',//页数文本框前显示的汉字
	        afterPageText: '页    共 {pages} 页',
	        displayMsg: '当前显示 {from} - {to} 条记录  共 {total} 条记录',
	    });
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    //修改
	    $("#edit").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	var selectLength = selectRows.length;
        	if(selectLength == 0){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var numbers = [];
            	$(selectRows).each(function(i, row){
            		numbers[i] = row.number;
            	});
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("消息提醒", "将删除与学生相关的所有数据(包括成绩)，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "delete",
							data: {numbers: numbers, ids: ids},
							success: function(data){
								if(data.type == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
									$("#dataList").datagrid("uncheckAll");
								} else{
									$.messager.alert("消息提醒","删除失败!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });

        $("#upload-btn").click(function () {
            $("#uploadPhoto").dialog("open");
        });

	  	//设置添加学生窗口
	    $("#addDialog").dialog({
	    	title: "添加学生",
	    	width: 650,
	    	height: 550,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
						    var data = $("#addForm").serialize();
							// var gradeid = $("#add_gradeList").combobox("getValue");
							// var clazzid = $("#add_clazzList").combobox("getValue");
							$.ajax({
								type: "post",
								url: "add",
                                dataType:'json',
								data: data,
								success: function(data){
									if(data.type == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_number").textbox('setValue', "");
										$("#add_password").textbox('setValue', "");
										$("#add_name").textbox('setValue', "");
										$("#add_sex").textbox('setValue', "男");
										$("#add_phone").textbox('setValue', "");
										$("#add_email").textbox('setValue', "");
										$("#add_remark").textbox('setValue', "");
                                        $("#add_clazzList").textbox('setValue', clazzList[1]);
                                        $("#upload-photo").textbox('setValue', "");

										//重新刷新页面数据
										// $('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
							  			$('#dataList').datagrid("reload");
							  			$("#gradeList").combobox('setValue', gradeId);
							  			setTimeout(function(){
											$("#clazzList").combobox('setValue', clazzId);
										}, 100);

									} else{
										$.messager.alert("消息提醒","添加失败!","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						$("#add_number").textbox('setValue', "");
                        $("#add_password").textbox('setValue', "");
						$("#add_name").textbox('setValue', "");
						$("#add_phone").textbox('setValue', "");
						$("#add_emil").textbox('setValue', "");
                        $("#add_sex").textbox('setValue', '男');
                        $("#upload-photo").textbox('setValue', "");
                        $("#photo-preview").attr("src", "/h-ui/images/default_student_portrait.png");
						//重新加载年级
						$("#add_gradeList").combobox("clear");
						$("#add_gradeList").combobox("reload");
					}
				},
			],
			//$("#photo-preview").attr("src", "/h-ui/images/default_student_portrait.png");
            onBeforeOpen: function () {
                $("#photo-preview").attr("src", "/h-ui/images/default_student_portrait.png");
            }
	    });

	  	//设置编辑学生窗口
	    $("#editDialog").dialog({
	    	title: "修改学生信息",
	    	width: 650,
	    	height: 550,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'提交',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
                            var data = $("#editForm").serialize();
                            $.ajax({
                                type: "post",
                                url: "edit",
                                dataType: 'json',
                                data: data,
                                success: function (data) {
                                    if (data.type == "success") {
                                        $.messager.alert("消息提醒", "更新成功!", "info");
                                        //关闭窗口
                                        $("#editDialog").dialog("close");
                                        //刷新表格
                                        // $('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
                                        $("#dataList").datagrid("reload");
                                        $("#dataList").datagrid("uncheckAll");

                                        $("#gradeList").combobox('setValue', gradeid);
                                        setTimeout(function () {
                                            $("#clazzList").combobox('setValue', clazzid);
                                        }, 100);

                                    } else {
                                        $.messager.alert("消息提醒", "更新失败!", "warning");
                                        return;
                                    }
                                }
                            });
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						//清空表单
						$("#edit_upload-photo").textbox('setValue', "");
					}
				}
			],
			//打开编辑窗口获取对应的值
            onBeforeOpen: function () {
                var selectRow = $("#dataList").datagrid("getSelected");
                $("#edit_id").val(selectRow.id);
                $("#edit_number").textbox('setValue', selectRow.studentNumber);
                $("#edit_name").textbox('setValue', selectRow.username);
                $("#edit_password").textbox('setValue', selectRow.password);
                $("#edit_sex").textbox('setValue', selectRow.sex);
                $("#edit_phone").textbox('setValue', selectRow.phone);
                $("#edit_clazzId").combobox('setValue', selectRow.clazzId);
                $("#edit_email").textbox('setValue', selectRow.email);
                $("#edit_photo-preview").attr("src", selectRow.photo);	//photo是图片的路径
                $("#edit_photo").val(selectRow.photo);
            }
	    });

        //搜索按钮
        $("#search-btn").click(function(){
            $('#dataList').datagrid('load', {
                username: $("#search-name").val(),
                clazzId: $("#search-clazz-id").combobox('getValue')
            });
        });

	    //上传图片事件
        $("#upload-btn").click(function () {
            var uploadPhoto = $("#upload-photo").filebox("getValue");
            if (uploadPhoto == '') {
                $.messager.alert("消息提醒", "请选择图片文件", "warning");
                return;
            } else {
                $("#photoFrom").submit();
            }
        });
        $("#edit_upload-btn").click(function () {
            var uploadPhoto = $("#edit_upload-photo").filebox("getValue");
            if (uploadPhoto == '') {
                $.messager.alert("消息提醒", "请选择图片文件", "warning");
                return;
            } else {
                $("#edit_photoFrom").submit();
            }
        });
	});

	//图片回显
	function upload() {
	    //获取到返回到iframs中的返回信息
        var data = $(window.frames["photo_target"].document).find("body pre").text();
        data = JSON.parse(data);
        console.log(data);
        if (data.type == "success") {
            $.messager.alert("消息提醒", "图片上传成功", "info");
            $("#photo-preview").attr("src", data.uploadPath);
            $("#add_photo").val(data.uploadPath); //将图片路径添加到表单隐藏域中

            $("#edit_photo-preview").attr("src", data.uploadPath);
            $("#edit_photo").val(data.uploadPath);
        } else {
            $.messager.alert("消息提醒", data.msg, "warning");
        }
    }
	</script>
</head>
<body>
	<!-- 学生列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0">

	</table>
	<!-- 工具栏 -->
	<div id="toolbar">
		<c:if test="${userType == 1}">
			<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		</c:if>
			<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
		<c:if test="${userType == 1}">
			<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		</c:if>
		<!-- 名称与班级名搜索域 -->
		<div style="margin-left: 10px;">
			<div style="float: left;" class="datagrid-btn-separator"></div>
			<a href="javascript:" class="easyui-linkbutton"
			   data-options="iconCls:'icon-grade',plain:true">班级名称</a>
			<select id="search-clazz-id" style="width: 155px;" class="easyui-combobox"
					name="clazzId">
				<option value="">未选择年级</option>
				<c:forEach items="${clazzList}" var="c">
					<option value="${c.id}">${c.name}</option>
				</c:forEach>
			</select>
			<a href="javascript:" class="easyui-linkbutton"
			   data-options="iconCls:'icon-class',plain:true">班级名称</a>
			<!-- 搜索按钮 -->
			<input id="search-name" class="easyui-textbox" name="username"/>
			<a id="search-btn" href="javascript:" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>

	</div>

	<!-- 添加学生窗口 -->
	<div id="addDialog" style="padding: 10px">
        <div id="add-photo" style="float: right;text-align: center; margin: 20px 20px 0 0;margin-right: 50px; width: 200px; border: 1px solid #EBF3FF">
            <img alt="照片" id="photo-preview"  style="width: 100%; height: 100%;"  title="预览照片" src="/h-ui/images/default_student_portrait.png"/>
            <form id="photoFrom" method="post" action="uploadPhoto" enctype="multipart/form-data" target="photo_target">
                <input id="upload-photo" class="easyui-filebox" name="photo" data-options="prompt:'选择照片'" style="width:200px;">
				<div style="text-align: center">
						<a id="upload-btn" href="javascript:;" class="easyui-linkbutton"
						   data-options="iconCls:'icon-folder-up',plain:true">上传头像</a>
				</div>
            </form>
        </div>
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
				<input id="add_photo" type="hidden" name="photo">
	    		<tr>
	    			<td>学号:</td>
	    			<td>
	    				<input id="add_number" name="studentNumber" class="easyui-textbox" validType="number" style="width: 200px; height: 30px;" type=""
							   data-options="required:true, missingMessage:'请输入学号'" />
	    			</td>
	    		</tr>

	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="add_name" name="username" style="width: 200px; height: 30px;" validType='CHS' class="easyui-textbox"  type="text"
							   data-options="required:true ,missingMessage:'请填写姓名'" /></td>
	    		</tr>
				<tr>
					<td>密码:</td>
					<td><input id="add_password" name="password"  style="width: 200px; height: 30px;" class="easyui-textbox" type="password"
							   data-options="required:true, missingMessage:'请填写密码'" /></td>
				</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="add_sex" name="sex" class="easyui-combobox"
								data-options="editable: false, panelHeight: 50, width: 80, height: 30" >
							<option value="男">男</option>
							<option value="女">女</option>
						</select>
					</td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="add_phone" name="phone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text"  validType="mobile" /></td>
	    		</tr>
	    		<tr>
	    			<td>email:</td>
	    			<td><input id="add_email" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="email" validType="email"
							   validType="number" data-options="missingMessage:'请填写姓名'" /></td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
					<td>
						<select id="add_clazzList" name="clazzId" style="width: 200px; height: 30px;" class="easyui-combobox">
							<option value="">选择所属班级</option>
							<c:forEach items="${clazzList}" var="clazz">
								<option value="${clazz.id}">${clazz.name}</option>
							</c:forEach>
						</select>
					</td>
	    		</tr>
				<tr>
					<td>备注:</td>
					<td><input id="add_remark" style="width: 280px; height: 100px;" class="easyui-textbox" type="text" name="remark" data-options="multiline:true"  /></td>
				</tr>
	    	</table>
	    </form>
	</div>

	<!-- 修改学生窗口 -->
	<div id="editDialog" style="padding: 10px">
		<div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF">
			<img alt="照片" id="edit_photo-preview"  style="width: 100%; height: 100%;"  title="预览照片" src="/h-ui/images/default_student_portrait.png"/>
			<form id="edit_photoFrom" method="post" action="uploadPhoto" enctype="multipart/form-data" target="photo_target">
				<input id="edit_upload-photo" class="easyui-filebox" name="photo" data-options="prompt:'选择照片'" style="width:200px;">
				<div style="text-align: center">
					<a id="edit_upload-btn" href="javascript:;" class="easyui-linkbutton"
					   data-options="iconCls:'icon-folder-up',plain:true">修改头像</a>
				</div>
			</form>
	    </div>
    	<form id="editForm" method="post">
			<input type="hidden" name="id" id="edit_id">
	    	<table cellpadding="8" >
				<input id="edit_photo" type="hidden" name="photo">
	    		<tr>
	    			<td>学号:</td>
	    			<td>
	    				<input id="edit_number" name="studentNumber" data-options="readonly: true" class="easyui-textbox" style="width: 200px; height: 30px;" type="text"  data-options="required:true, missingMessage:'学号不可改变'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="edit_name" name="username" style="width: 200px; height: 30px;" class="easyui-textbox" type="text"  data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
				<tr>
					<td>密码:</td>
					<td><input id="edit_password" name="password"  style="width: 200px; height: 30px;" class="easyui-textbox" type="password"
							   data-options="required:true, missingMessage:'请填写密码'" /></td>
				</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="edit_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="edit_phone" name="phone"  style="width: 200px; height: 30px;" class="easyui-textbox" type="text" validType="mobile" /></td>
	    		</tr>
	    		<tr>
	    			<td>email:</td>
	    			<td><input id="edit_email" name="email" style="width: 200px; height: 30px;" class="easyui-textbox" type="text"  validType="email" /></td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td>
						<select id="edit_clazzId"  class="easyui-combobox" style="width: 200px;" name="clazzId" data-options="required:true, missingMessage:'请选择所属班级'">
							<c:forEach items="${clazzList}" var="clazz">
								<option value="${clazz.id }">${clazz.name }</option>
							</c:forEach>
						</select>
					</td>
	    		</tr>
				<tr>
					<td>备注:</td>
					<td><input id="edit_remark" style="width: 280px; height: 100px;" class="easyui-textbox" type="text" name="remark" data-options="multiline:true"  /></td>
				</tr>
	    	</table>
	    </form>
	</div>
	<%--提交表单处理iframe--%>
		<iframe id="photo_target" name="photo_target" onload="upload(this)"></iframe>
	</div>
</body>
</html>