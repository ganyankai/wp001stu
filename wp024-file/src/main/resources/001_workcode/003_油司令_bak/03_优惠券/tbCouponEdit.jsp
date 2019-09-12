<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<script>
       var groupId = "${groupId}";
    </script>

    <%@include file="/common/headModule.jsp" %>
    <title>优惠券修改</title>
    <link type="text/css" href="${path}/resources/admin/css/dingdanjilu.css" rel="stylesheet" />
    <link type="text/css" href="${path}/resources/admin/css/common.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/dropMenu.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/discountRule02.css" rel="stylesheet"/>
    <script src="${path}/resources/admin/js/common.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/js/sonjs.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/js/coupon/addEdit.js"></script>

</head>
<body>
<div class="position_now"><div class="goback" onclick="history.go(-1);">返回上一层</div>
    您当前的位置 : <a onclick="history.go(-1);">优惠券修改</a> &gt; <a>${entity.id == null ? '新增' : '修改'}</a></div>
<div class="wrapper">
    <div class="tabDivHide xw_showContent">
        <div class="contentBox">
            <div class="contentTitle">
                <span class="titleText">&nbsp;${entity.id == null ? '新增' : '修改'}充值规则</span>
                <input type="hidden" id="ruleId" value="${entity.id}">
            </div>
            <div class="ruleBox">
                <table class="mainTable noborder" cellspacing="0">
                    <tr>
                        <th>适用公司：</th>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="settingBox xw_settingBox" style="display:block;">
                                <span class="tit" id="company">${company.names}</span>
                                <input type="hidden" id="companyId" value="${company.ids}">
                                <span class="btnOrange" style="margin-top:0px;" id="selectCompany">选择</span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>有效时间：</th>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="settingBox xw_settingBox" style="display:block;">
                                <input class="timeInput" name="startDate" id="startDate" readonly
                                       value="<fmt:formatDate value="${entity.startDate}" pattern="yyyy-MM-dd"/>"
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',
                               minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\') '})"/>
                                <span class="tit" style="margin-left:10px;">至</span>
                                <input class="timeInput" name="endDate" id="endDate" readonly
                                       value="<fmt:formatDate value="${entity.endDate}" pattern="yyyy-MM-dd"/>"
                                       onclick="WdatePicker({
                               dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')|| $dp.$DV(\'%y-%M-%d\') '})"/>
                            </div>
                        </td>
                    </tr>
                    <tr class="separator">
                    </tr>
                    <tr>
                        <th>规则内容：</th>
                        <td>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <c:forEach items="${details}" var="detail">
                                <div class="settingBox" style="display:block;">
                                    <span class="tit">充值满</span>
                                    <input class="moneyInputText recharge" value="${detail.amountMin}"
                                           onkeyup="testNumber(this)"
                                           onafterpaste="testNumber(this)" />
                                    <span class="tit">元送</span>
                                    <input class="moneyInputText give" value="${detail.giveAmount}"
                                           onkeyup="testNumber(this)"
                                           onafterpaste="testNumber(this)" />
                                    <span class="tit">元</span>
                                    <div class="deleBtn">删除</div>
                                </div>
                            </c:forEach>
                            <div class="addBar">新增一条规则</div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="bottomBar">
            <span class="btnOrange xw_saveInfo" id="save">保存</span>
            <span class="btnGray" onclick="history.go(-1)">取消</span>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/html" id="template_dataList">
    {{each}}
    <div class="settingBox" style="display:block;">
        <span class="tit">充值满</span>
        <input class="moneyInputText recharge" value="${detail.amountMin}"
               onkeyup="testNumber(this)"
               onafterpaste="testNumber(this)" />
        <span class="tit">元送</span>
        <input class="moneyInputText give" value="${detail.giveAmount}"
               onkeyup="testNumber(this)"
               onafterpaste="testNumber(this)" />
        <span class="tit">元</span>
        <div class="deleBtn">删除</div>
    </div>
    {{/each}}
</script>