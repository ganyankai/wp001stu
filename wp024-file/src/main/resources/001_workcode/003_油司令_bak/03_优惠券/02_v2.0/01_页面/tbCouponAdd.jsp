<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/headModule.jsp" %>
    <title>优惠券新增</title>
    <link type="text/css" href="${path}/resources/admin/css/groupadd.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/common.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/dropMenu.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${path}/resources/admin/css/flipsnap.css"/>
    <link type="text/css" href="${path}/resources/skinCatt/skin/default/easyui.css" rel="stylesheet"/>
    <script src="${path}/resources/admin/common/temDataGrid.js"></script>
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
            <form id="form" method="post">
            	<input name="id" type="hidden" value="${tbCoupon.id}"/>
            	<input name="state" type="hidden" value="${tbCoupon.state}"/>
                <table class="stationMsgTable" cellspacing="0">
                    <tr>
                    	<td width="50%">
	                        <div class="stationInfo">
	                        	<span class="stationLine">
	                                <h3>
	                                    <span class="textGray floatL">优惠券名称：</span>
                                    	<input id="name" name="name" class="searchInputText" type="text" value="${tbCoupon.name}"/>
	                                </h3>
	                            </span>
	                            <span class="stationLine">
	                                <h3><span class="textGray floatL">优惠券类型：</span>
	                                	<select style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;margin-bottom: 6px;" name="couponType" id="couponType" >
		                                    <option value="1">满减券</option>
		                                    <option value="2">打折券</option>
		                                </select>
	                                </h3>
	                            </span>
	                            <span class="stationLine">
	                                <h3>
	                                    <span class="textGray floatL">发行数量：</span>
	                                    <input id="publishQty" name="publishQty" class="searchInputText" type="text" value="${tbCoupon.publishQty}"/>
	                                </h3>
	                            </span>
	                            <span class="stationLine">
	                                <h3>
	                                    <span class="textGray floatL">面值：</span>
                                    	<input id="couponValue" name="couponValue" class="searchInputText" type="text" value="${tbCoupon.couponValue}"/>元
	                                </h3>
	                            </span>
	                            <span class="stationLine">
	                                <h3>
	                                    <span class="textGray floatL">使用条件：</span>
                                        <span class="textGray floatL">满</span>
	                                    <input id="couponCondition" name="couponCondition" class="searchInputText" type="text" value="${tbCoupon.couponCondition}"/>元
	                                </h3>
	                            </span>
	                            <span class="stationLine">
	                                <h3>
	                                    <span class="textGray floatL">生效时间：</span>
	                                    <input class="timeInput" 
			                                   name="startDate" id="startDate"
			                                   onclick="WdatePicker({ dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'expirationDate\')}'})"
			                                   value="<fmt:formatDate value="${tbCoupon.startDate}" pattern="yyyy-MM-dd" />"/>
			                            <font color="#FF0000">*</font>
	                                </h3>
	                            </span>
	                            <span class="stationLine">
	                                <h3>
	                                    <span class="textGray floatL">过期时间：</span>
	                                    <input class="timeInput" 
			                                   name="expirationDate" id="expirationDate"
			                                   onclick="WdatePicker({ dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"
			                                   value="<fmt:formatDate value="${tbCoupon.expirationDate}" pattern="yyyy-MM-dd" />"/>
			                            <font color="#FF0000">*</font>
	                                </h3>
	                            </span>
	                            <span class="stationLine">
	                                <h3>
	                                    <span class="textGray floatL">备注：</span>
	                                    <input id="remark" name="remark" class="searchInputText" type="text" value="${tbCoupon.remark}"/>
	                                </h3>
	                            </span>
	                        </div>
	                    </td>
                    </tr>

                </table>
             </form>
        </div>
        <div class="bottomBar">
            <span class="btnOrange xw_saveInfo" id="save">保存</span>
            <span class="btnGray" onclick="history.go(-1)">取消</span>
        </div>
    </div>
</div>
</body>
</html>