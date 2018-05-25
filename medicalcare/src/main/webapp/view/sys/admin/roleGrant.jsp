<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var resourceTree;
    $(function() {
        resourceTree = $('#resourceTree').tree({
            url : '${path }/sys/systemResource/showAllTrees.action',
            parentField : 'pid',
            lines : true,
            checkbox : true,
            onClick : function(node) {
            },
            onLoadSuccess : function(node, data) {
                progressLoad();
                $.post( '${path }/sys/systemRole/findResourceIdListByRoleId.action', {
                    id : '${id}'
                }, function(result) {
                    var ids;
                    if (result.code == 200 && result.data != undefined) {
                        ids = $.stringToList(result.data + '');
                    }
                    if (ids.length > 0) {
                        for ( var i = 0; i < ids.length; i++) {
                            if (resourceTree.tree('find', ids[i])) {
                                resourceTree.tree('check', resourceTree.tree('find', ids[i]).target);
                            }
                        }
                    }
                }, 'json');
                progressClose();
            },
            cascadeCheck : false
        });

        $('#roleGrantForm').form({
            url : '${path }/sys/systemRole/grant.action',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                var checknodes = resourceTree.tree('getChecked');
                var ids = [];
                if (checknodes && checknodes.length > 0) {
                    for ( var i = 0; i < checknodes.length; i++) {
                        ids.push(checknodes[i].id);
                    }
                }
                $('#resourceIds').val(ids);
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.code == 200) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.message, 'error');
                }
            }
        });
    });

    function checkAll() {
        var nodes = resourceTree.tree('getChecked', 'unchecked');
        if (nodes && nodes.length > 0) {
            for ( var i = 0; i < nodes.length; i++) {
                resourceTree.tree('check', nodes[i].target);
            }
        }
    }
    function uncheckAll() {
        var nodes = resourceTree.tree('getChecked');
        if (nodes && nodes.length > 0) {
            for ( var i = 0; i < nodes.length; i++) {
                resourceTree.tree('uncheck', nodes[i].target);
            }
        }
    }
    function checkInverse() {
        var unchecknodes = resourceTree.tree('getChecked', 'unchecked');
        var checknodes = resourceTree.tree('getChecked');
        if (unchecknodes && unchecknodes.length > 0) {
            for ( var i = 0; i < unchecknodes.length; i++) {
                resourceTree.tree('check', unchecknodes[i].target);
            }
        }
        if (checknodes && checknodes.length > 0) {
            for ( var i = 0; i < checknodes.length; i++) {
                resourceTree.tree('uncheck', checknodes[i].target);
            }
        }
    }
</script>
<div id="roleGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west'" title="系统资源" style="width: 100%; padding: 1px;">
        <div class="well well-small">
            <form id="roleGrantForm" method="post">
                <input name="id" type="hidden"  value="${id}" readonly="readonly">
                <ul id="resourceTree"></ul>
                <input id="resourceIds" name="resourceIds" type="hidden" />
            </form>
        </div>
    </div>
    <div data-options="region:'south'" title="" style="overflow: hidden; padding: 10px;">
        <div>
            <button class="btn btn-success" onclick="checkAll();">全选</button>            
            <button class="btn btn-warning" onclick="checkInverse();">反选</button> 
            <button class="btn btn-inverse" onclick="uncheckAll();">取消</button>
        </div>
    </div>
</div>