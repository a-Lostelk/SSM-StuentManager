<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>年级列表</title>
    <link rel="stylesheet" type="text/css" href="/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/easyui/css/demo.css">
    <script type="text/javascript" src="/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/easyui/js/validateExtends.js"></script>
    <script type="text/javascript">
        var gradeName =${gradeJSON};

        $(function () {
            var table;
            //初始化datagrid
            $('#dataList').datagrid({
                iconCls: 'icon-more',//图标
                border: true,
                collapsible: false,//是否可折叠
                fit: true,//自动大小
                method: "post",
                url: "get_list?t" + new Date().getTime(),
                idField: 'id',
                singleSelect: false,//是否单选
                rownumbers: true,//行号
                pagination: true,//分页控件
                sortName: 'id',
                // sortOrder: 'DESC',
                remoteSort: false,
                columns: [[
                    {field: 'chk', checkbox: true, width: 50},
                    {field: 'id', title: 'ID', width: 50, sortable: true},{field: 'name', title: '班级名称', width: 150, sortable: true},
                    {
                        field: 'gradeId', title: '所属年级', width: 150,
                        formatter: function (value) {
                            for (i = 0; i < gradeName.length; i++) {
                                if (gradeName[i].id == value) {
                                    return gradeName[i].name;
                                }
                            }
                            return value;
                        }
                    },
                    {field: 'number', title: '班级人数', width: 100},
                    {field: 'teacherName', title: '班主任姓名', width: 150},
                    {field: 'email', title: '班主任邮箱', width: 150},
                    {field: 'telephone', title: '班主任电话', width: 150},
                    {field: 'remark', title: '备注', width: 200},
                ]],
                toolbar: "#toolbar"//工具栏
            });

            //设置分页控件
            var p = $('#dataList').datagrid('getPager');
            $(p).pagination({
                pageSize: 10,//设置每页显示的记录条数,默认为10
                pageList: [10, 20, 30, 50, 100],//设置每页的记录条数
                beforePageText: '第',
                afterPageText: '页    共 {pages} 页',
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            });

            //信息添加按钮事件
            $("#add").click(function () {
                table = $("#addTable");
                $("#addTable").form("clear");//清空表单数据
                $("#addDialog").dialog("open");//打开添加窗口
            });

            //信息修改按钮事件
            $("#edit").click(function () {
                table = $("#editTable");
                var selectRows = $("#dataList").datagrid("getSelections");
                if (selectRows.length !== 1) {
                    $.messager.alert("消息提醒", "请单条选择想要修改的数据哟!", "warning");
                } else {
                    $("#editDialog").dialog("open");
                }
            });

            //信息删除按钮事件
            $("#delete").click(function () {
                var selectRows = $("#dataList").datagrid("getSelections");//返回所有选中的行,当没有选中的记录时,将返回空数组
                var selectLength = selectRows.length;
                if (selectLength === 0) {
                    $.messager.alert("消息提醒", "请选择想要删除的数据哟!", "warning");
                } else {
                    var ids = [];
                    $(selectRows).each(function (i, row) {
                        ids[i] = row.id;//将预删除行的id存储到数据中
                    });
                    $.messager.confirm("消息提醒", "删除后将无法恢复该班级信息! 确定继续?", function (r) {
                        if (r) {
                            $.ajax({
                                type: "post",
                                url: "delete?t" + new Date().getTime(),
                                data: {ids: ids},
                                dataType: 'json',
                                success: function (data) {
                                    if (data.type == "success") {
                                        $.messager.alert("消息提醒", "删除成功啦!", "info");
                                        $("#dataList").datagrid("reload");//刷新表格
                                        $("#dataList").datagrid("uncheckAll");//取消勾选当前页所有的行
                                    } else {
                                        $.messager.alert("消息提醒", data.msg, "warning");
                                    }
                                }
                            });
                        }
                    });
                }
            });

            //设置添加班级窗口
            $("#addDialog").dialog({
                title: "添加班级信息窗口",
                width: 430,
                height: 410,
                iconCls: "icon-house",
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: true,
                buttons: [
                    {
                        text: '添加',
                        plain: true,
                        iconCls: 'icon-add',
                        handler: function () {
                            var validate = $("#addForm").form("validate");
                            if (!validate) {
                                $.messager.alert("消息提醒", "请检查你输入的数据哟!", "warning");
                            } else {
                                var data = $("#addForm").serialize();//序列化表单信息
                                $.ajax({
                                    type: "post",
                                    url: "addClazz",
                                    data: data,
                                    dataType: 'json',
                                    success: function (data) {
                                        if (data.type == "success") {
                                            $("#addDialog").dialog("close"); //关闭窗口
                                            $('#dataList').datagrid("reload");//重新刷新页面数据
                                            $.messager.alert("消息提醒", "添加成功啦!", "info");
                                            $("#addDialog").dialog("close"); //关闭窗口
                                        } else {
                                            $.messager.alert("消息提醒", data.msg, "warning");
                                        }
                                    }
                                });
                            }
                        }
                    },
                    {
                        text: '重置',
                        plain: true,
                        iconCls: 'icon-reload',
                        handler: function () {
                            $("#add_name").textbox('setValue', "");
                            $("#add_remark").textbox('setValue', "");
                            $("#add_number").textbox('setValue', "");
                            $("#add_teacherName").textbox('setValue', "");
                            $("#add_email").textbox('setValue', "");
                            $("#add_telephone").textbox('setValue', "");
                        }
                    }
                ]
            });

            //设置编辑班级信息窗口
            $("#editDialog").dialog({
                title: "修改班级信息窗口",
                width: 430,
                height: 410,
                iconCls: "icon-house",
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: true,
                buttons: [
                    {
                        text: '提交',
                        plain: true,
                        iconCls: 'icon-edit',
                        handler: function () {
                            var validate = $("#editForm").form("validate");
                            if (!validate) {
                                $.messager.alert("消息提醒", "请检查你输入的数据哟!", "warning");
                            } else {
                                var data = $("#editForm").serialize();//序列化表单信息
                                $.ajax({
                                    type: "post",
                                    url: "edit",
                                    data: data,
                                    dataType: 'json',
                                    success: function (data) {
                                        if (data.type == "success") {
                                            //关闭窗口
                                            $("#editDialog").dialog("close");
                                            //重新刷新页面数据
                                            $('#dataList').datagrid("reload");
                                            $('#dataList').datagrid("uncheckAll");
                                            //用户提示
                                            $.messager.alert("消息提醒", "修改成功啦!", "info");
                                        } else {
                                            $.messager.alert("消息提醒", data.msg, "warning");
                                        }
                                    }
                                });
                            }
                        }
                    },
                    {
                        text: '重置',
                        plain: true,
                        iconCls: 'icon-reload',
                        handler: function () {
                            $("#edit_name").textbox('setValue', "");
                            $("#edit_gradeId").combobox('setValue', "");
                            $("#edit_remark").textbox('setValue', "");
                            $("#edit_number").textbox('setValue', "");
                            $("#edit_email").textbox('setValue', "");
                            $("#edit_teacherName").textbox('setValue', "");
                            $("#edit_telephone").textbox('setValue', "");
                        }
                    }
                ],
                //打开窗口前先初始化表单数据(表单回显)
                onBeforeOpen: function () {
                    var selectRow = $("#dataList").datagrid("getSelected");


                    $("#edit_id").val(selectRow.id);//需根据id更新班级信息
                    $("#edit_name").textbox('setValue', selectRow.name);
                    $("#edit_gradeId").combobox('setValue', selectRow.gradeId);
                    console.log(selectRow.gradeId);
                    $("#edit_remark").textbox('setValue', selectRow.remark);
                    // $("#edit_number").textbox('setValue', selectRow.number);
                    // $("#edit_coordinator").textbox('setValue', selectRow.coordinator);
                    // $("#edit_email").textbox('setValue', selectRow.email);
                    // $("#edit_telephone").textbox('setValue', selectRow.telephone);
                    // $("#edit_gradeId").textbox('setValue', selectRow.gradeId);
                    // $("#edit_introducation").textbox('setValue', selectRow.introducation);
                }
            });

            //班级与年级名称搜索按钮的监听事件(将搜索值返回给Controller)
            $("#search-btn").click(function () {
                $('#dataList').datagrid('load', {
                    name: $('#search-clazzname').val(),//获取班级名称
                    gradeId: $('#search-gradename').combobox('getValue')//获取年级value
                });
            });

        });

    </script>
</head>
<body>

<!-- 班级信息列表 -->
<table id="dataList" cellspacing="0" cellpadding="0"></table>

<!-- 工具栏 -->
<div id="toolbar">
    <c:if test="${userType == 1}">
    <div style="float: left;"><a id="add" href="javascript:" class="easyui-linkbutton"
                                 data-options="iconCls:'icon-add',plain:true">添加</a></div>
    <div style="float: left;" class="datagrid-btn-separator"></div>
    <div style="float: left;"><a id="edit" href="javascript:" class="easyui-linkbutton"
                                 data-options="iconCls:'icon-edit',plain:true">修改</a></div>
    <div style="float: left;" class="datagrid-btn-separator"></div>
    <div style="float: left;"><a id="delete" href="javascript:" class="easyui-linkbutton"
                                 data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
    </c:if>
    <!-- 年级与班级名搜索域 -->
    <div style="margin-left: 10px;">
        <div style="float: left;" class="datagrid-btn-separator"></div>
        <a href="javascript:" class="easyui-linkbutton"
           data-options="iconCls:'icon-grade',plain:true">年级名称</a>
        <select id="search-gradename" style="width: 155px;" class="easyui-combobox"
                name="gradename">
            <option value="">未选择年级</option>
            <c:forEach items="${gradeList}" var="grade">
                <option value="${grade.id}">${grade.name}</option>
            </c:forEach>
        </select>
        <a href="javascript:" class="easyui-linkbutton"
           data-options="iconCls:'icon-class',plain:true">班级名称</a>
        <!-- 搜索按钮 -->
        <input id="search-clazzname" class="easyui-textbox" name="clazzname"/>
        <a id="search-btn" href="javascript:" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
    </div>
</div>

<!-- 添加信息窗口 -->
<div id="addDialog" style="padding: 25px 0 0 55px;">
    <form id="addForm" method="post" action="#">
        <table id="addTable" style="border-collapse:separate; border-spacing:0 3px;" cellpadding="6">
            <tr>
                <td>班级名称</td>
                <td colspan="1">
                    <input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox"
                           type="text" name="name" data-options="required:true, missingMessage:'请填写班级名哟~'"/>
                </td>
            </tr>
            <tr>
                <td>所属年级</td>
                <td colspan="1">
                    <select id="add_gradeId" style="width: 200px; height: 30px;" class="easyui-combobox"
                            name="gradeId" data-options="required:true, missingMessage:'请选择所属年级哟~'">
                        <c:forEach items="${gradeList}" var="grade">
                            <option value="${grade.id}">${grade.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>班级人数</td>
                <td colspan="1">
                    <input id="add_number" style="width: 200px; height: 30px;" class="easyui-textbox"
                           type="text" name="number" data-options="missingMessage:'请填写班级人数哟~'"/>
                </td>
            </tr>
            <tr>
                <td>班主任名称</td>
                <td colspan="1">
                    <input id="add_teacherName" style="width: 200px; height: 30px;" class="easyui-textbox" validType="CHS"
                           type="text" name="teacherName" data-options="missingMessage:'请填写班级姓名哟~'"/>
                </td>
            </tr>
            <tr>
                <td>班主任邮箱</td>
                <td colspan="1">
                    <input id="add_email" style="width: 200px; height: 30px;" class="easyui-textbox" validType="email"
                           type="text" name="email" data-options="missingMessage:'请填写班主任邮箱哟~'"/>
                </td>
            </tr>
            <tr>
                <td>联系方式</td>
                <td colspan="1">
                    <input id="add_telephone" style="width: 200px; height: 30px;" class="easyui-textbox" validType="phone"
                           type="text" name="telephone" data-options="missingMessage:'请填写班主任联系方式哟~'"/>
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td><input id="add_remark" style="width: 200px; height: 180px;" class="easyui-textbox" type="text"
                           name="remark" data-options="multiline:true"/></td>
            </tr>
        </table>
    </form>
</div>


<!-- 修改信息窗口 -->
<div id="editDialog" style="padding: 20px 0 0 65px">
    <form id="editForm" method="post" action="#">
        <!-- 获取被修改信息的班级id -->
        <input type="hidden" id="edit_id" name="id"/>
        <table id="editTable" style="border-collapse:separate; border-spacing:0 3px;" cellpadding="6">
            <tr>
                <td>班级名称</td>
                <td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text"
                           name="name" data-options="required:true, missingMessage:'请填写班级姓名哟~'"/>
                </td>
            </tr>
            <tr>
                <td>所属年级</td>
                <td colspan="1">
                    <select id="edit_gradeId" style="width: 200px; height: 30px;" class="easyui-combobox"
                            name="gradeId" data-options="missingMessage:'请选择所属年级哟~'">
                        <c:forEach items="${gradeList}" var="grade">
                            <option value="${grade.id}">${grade.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>班级人数</td>
                <td colspan="1">
                    <input id="edit_number" style="width: 200px; height: 30px;" class="easyui-textbox"
                           type="text" name="number" data-options="missingMessage:'请填写班级人数哟~'"/>
                </td>
            </tr>
            <tr>
                <td>班主任名称</td>
                <td colspan="1">
                    <input id="edit_teacherName" style="width: 200px; height: 30px;" class="easyui-textbox" validType="CHS"
                           type="text" name="teacherName" data-options="missingMessage:'请填写班级姓名哟~'"/>
                </td>
            </tr>
            <tr>
                <td>班主任邮箱</td>
                <td colspan="1">
                    <input id="edit_email" style="width: 200px; height: 30px;" class="easyui-textbox" validType="email"
                           type="text" name="email" data-options="missingMessage:'请填写班主任邮箱哟~'"/>
                </td>
            </tr>
            <tr>
                <td>联系方式</td>
                <td colspan="1">
                    <input id="edit_telephone" style="width: 200px; height: 30px;" class="easyui-textbox" validType="mobile"
                           type="text" name="telephone" data-options="missingMessage:'请填写班主任联系方式哟~'"/>
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td><input id="edit_remark" style="width: 200px; height: 180px;" class="easyui-textbox" type="text"
                           name="remark" data-options="multiline:true"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>