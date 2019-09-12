<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<script>
       var groupId = "${groupId}";
    </script>

    <%@include file="/common/headModule.jsp" %>
    <title>优惠券新增</title>
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
    您当前的位置 : <a onclick="history.go(-1);">新增优惠券</a> &gt; <a>${entity.id == null ? '新增' : '修改'}</a></div>
<div class="wrapper">
    <div class="tabDivHide xw_showContent">
        <div class="contentBox">
            <div class="contentTitle">
                <span class="titleText">&nbsp;${entity.id == null ? '新增' : '修改'}充值规则</span>
                <input type="hidden" id="ruleId" value="${entity.id}">
            </div>
            <div class="content">
                <form id="form" method="post">
                <table class="contentTable">
                    <tr>
                        <td>优惠券类型1234：</td>
                        <td colspan="">
                            <div class="add_searchInputText" style="margin-left: 50px;margin-right: 5px;">
                                <select style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;margin-bottom: 6px;" name="couponType" id="couponType" >
                                    <option value="coupon_type_full_subtraction">满减</option>
                                    <option value="coupon_type_discount">折扣</option>
                                </select>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td>发行数量：</td>
                        <td colspan="3">
                            <input id="publishQty" name="publishQty" class="" value="${entity.title}"/>
                            <%--<font color="#FF0000">*</font>--%>
                        </td>
                    </tr>

                    <tr>
                        <td>面值：</td>
                        <td colspan="3">
                            <input id="couponValue" name="couponValue" class="" value="${entity.title}"/>
                        </td>
                    </tr>

                    <tr>
                        <td>使用条件：</td>
                        <td colspan="3">
                            <input id="couponCondition" name="couponCondition" class="" value="${entity.title}"/>
                        </td>
                    </tr>

                    <tr>
                        <td>生效时间：</td>
                        <td>
                            <input class="timeInput" value="${fn:substring(entity.startDate, 0, 10)}" id="startDate"
                                   name="startDate"
                                   onclick="WdatePicker({ dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
                            <font color="#FF0000">*</font>
                        </td>
                        <td style="width: 130px;text-align: right;">过期时间：</td>
                        <td>
                            <input class="timeInput" value="${fn:substring(entity.endDate, 0, 10)}" id="endDate"
                                   name="endDate"
                                   onclick="WdatePicker({ dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"/>
                            <font color="#FF0000">*</font>
                        </td>
                    </tr>

                    <%--<tr>--%>
                        <%--<th>有效时间：</th>--%>
                        <%--<td></td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td colspan="2">--%>
                            <%--<div class="settingBox xw_settingBox" style="display:block;">--%>
                                <%--<input class="timeInput" name="startDate" id="startDate" readonly--%>
                                       <%--value="<fmt:formatDate value="${entity.startDate}" pattern="yyyy-MM-dd"/>"--%>
                                       <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',--%>
                               <%--minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\') '})"/>--%>
                                <%--<span class="tit" style="margin-left:10px;">至</span>--%>
                                <%--<input class="timeInput" name="endDate" id="endDate" readonly--%>
                                       <%--value="<fmt:formatDate value="${entity.endDate}" pattern="yyyy-MM-dd"/>"--%>
                                       <%--onclick="WdatePicker({--%>
                               <%--dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')|| $dp.$DV(\'%y-%M-%d\') '})"/>--%>
                            <%--</div>--%>
                        <%--</td>--%>
                    <%--</tr>--%>


                </table>
                </form>
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