<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/headModule.jsp" %>
    <title>优惠券新增</title>
    <link type="text/css" href="${path}/resources/admin/css/dingdanjilu.css" rel="stylesheet" />
    <link type="text/css" href="${path}/resources/admin/css/common.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/dropMenu.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/discountRule02.css" rel="stylesheet"/>
    <script src="${path}/resources/admin/js/common.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/js/sonjs.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/js/coupon/add.js"></script>
</head>
<body>
<div class="position_now"><div class="goback" onclick="history.go(-1);">返回上一层</div>
    您当前的位置 : <a onclick="history.go(-1);">新增优惠券</a> &gt;</div>
<div class="wrapper">
    <div class="tabDivHide xw_showContent">
        <div class="contentBox">
            <div class="contentTitle">
                <span class="titleText">&nbsp;新增优惠券</span>
            </div>
            <div class="content">
                <form id="form" method="post">
                <table class="contentTable">
                    <tr>
                        <td>优惠券类型：</td>
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
                            <input id="publishQty" name="publishQty" class="" value=""/>
                        </td>
                    </tr>

                    <tr>
                        <td>面值：</td>
                        <td colspan="3">
                            <input id="couponValue" name="couponValue" class="" value=""/>
                        </td>
                    </tr>

                    <tr>
                        <td>使用条件：</td>
                        <td colspan="3">
                            <input id="couponCondition" name="couponCondition" class="" value=""/>
                        </td>
                    </tr>

                    <tr>
                        <td>生效时间：</td>
                        <td>
                            <input class="timeInput" value="" id="startDate"
                                   name="startDate"
                                   onclick="WdatePicker({ dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'expirationDate\')}'})"/>
                            <font color="#FF0000">*</font>
                        </td>
                        <td style="width: 130px;text-align: right;">过期时间：</td>
                        <td>
                            <input class="timeInput" value="" id="expirationDate"
                                   name="expirationDate"
                                   onclick="WdatePicker({ dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"/>
                            <font color="#FF0000">*</font>
                        </td>
                    </tr>

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