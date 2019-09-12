<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/headModule.jsp" %>
    <title>锁价新增</title>
    <link type="text/css" href="${path}/resources/admin/css/groupadd.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/common.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/dropMenu.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${path}/resources/admin/css/flipsnap.css"/>
    <link type="text/css" href="${path}/resources/skinCatt/skin/default/easyui.css" rel="stylesheet"/>
    <script src="${path}/resources/admin/common/temDataGrid.js"></script>
    <script src="${path}/resources/admin/js/lockPrice/add.js"></script>
</head>
<body>
<div class="position_now"><div class="goback" onclick="history.go(-1);">返回上一层</div>
    您当前的位置 : <a onclick="history.go(-1);">新增锁价</a> </div>
<div class="wrapper">
    <div class="tabDivHide xw_showContent">
        <div class="contentBox">
            <div class="contentTitle">
                <span class="titleText">&nbsp;新增锁价</span>
            </div>
            <form id="form" method="post">
            	<input name="id" type="hidden" value="${lockPrice.id}"/>
            	<input name="groupId" type="hidden" value="${groupId}"/>
            	<%--<input name="state" type="hidden" value="${tbCoupon.state}"/>--%>
                <table class="stationMsgTable" cellspacing="0">
                    <tr>
                    	<td width="50%">
	                        <div class="stationInfo">
	                        	<%--<span class="stationLine">--%>
	                                <%--<h3>--%>
	                                    <%--<span class="textGray floatL">油品2：</span>--%>
                                    	<%--<input id="productKind2" name="productKind2" class="searchInputText" type="text" value=""/>--%>
	                                <%--</h3>--%>
	                            <%--</span>--%>

								<span class="stationLine">
	                                <h3>
	                                    <span class="textGray floatL">油品：</span>
                                    	<input class="searchInputSelect xw_input" value="" name="productKind" id="productKind" type="text" style=""/>
	                                </h3>
	                            </span>

	                            <%--<span class="stationLine">--%>
	                                <%--<h3>--%>
	                                    <%--<span class="textGray floatL">油站：</span>--%>
	                                    <%--<input id="groupId" name="groupId" class="searchInputText" type="text" value=""/>--%>
	                                <%--</h3>--%>
	                            <%--</span>--%>

									<%--<span class="stationLine">--%>
										<%--<span class="textGray floatL">油站：</span>--%>
										<%--<select style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;" name="groupId" id="groupId">--%>
											<%--<option value=""></option>--%>
											<%--<c:forEach items="${stationList}" var="sl">--%>
												<%--<option value="${sl.id}"--%>
														<%--<c:if test="${groupId==sl.id}">selected="selected"</c:if>--%>
												<%-->${sl.name}</option>--%>
											<%--</c:forEach>--%>
										<%--</select>--%>
									<%--</span>--%>

								<span class="stationLine">
									<span class="textGray floatL">油站：</span>
									<c:forEach items="${stationList}" var="sl">
										<input type="checkbox" name="oids" value="${sl.id}"
												<%--<c:forEach items="${zsProvinces}" var="province">--%>
													<%--<c:if test="${province.pid == provinces.pid}">--%>
														<%--checked="checked"--%>
													<%--</c:if>--%>
												<%--</c:forEach>--%>
										/>${sl.name}
									</c:forEach>
								</span>

	                            <span class="stationLine">
	                                <h3>
	                                    <span class="textGray floatL">锁价价格(锁死)：</span>
                                    	<input id="deathRange" name="deathRange" class="searchInputText" type="text" value="${tbCoupon.couponValue}"/>元
	                                </h3>
	                            </span>
								<span class="stationLine">
	                                <h3>
	                                    <span class="textGray floatL">锁价价格(跟跌不跟涨)：</span>
                                    	<input id="floatRange" name="floatRange" class="searchInputText" type="text" value="${tbCoupon.couponValue}"/>元
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