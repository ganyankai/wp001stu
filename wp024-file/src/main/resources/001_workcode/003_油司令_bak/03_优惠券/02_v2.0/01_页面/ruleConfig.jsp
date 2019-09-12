<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/headModule.jsp" %>
    <title>优惠券配置</title>
    <link type="text/css" href="${path}/resources/admin/css/common.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/dingdanjilu.css" rel="stylesheet" />
    <link type="text/css" href="${path}/resources/admin/css/dropMenu.css" rel="stylesheet"/>
    <script src="${path}/resources/admin/js/common.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/js/sonjs.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/common/temDataGrid.js"></script>
    <script src="${path}/resources/admin/js/coupon/ruleConfig.js"></script>
    <script type="text/javascript">
        $(function () {
            var value = "${map.register }";
            $("#register option[value='"+value+"']").attr("selected","selected");
        })
    </script>
</head>
<body>
<div class="position_now">您当前的位置 : <a>优惠券配置 </a></div>
<div class="wrapper">
    <div class="contentBox">
        <div class="contentTitle">
            <span class="titleText">&nbsp;优惠券配置</span>
            <span class="btnBlue xw_btnSave" id="btnSave">保存</span>
        </div>
        <form id="form">
            <input type="hidden" param="param" id="id" name="id" value="${pubSetup.id}"/>
            <input type="hidden" param="param" id="code" name="code" value="${pubSetup.code}"/>
            <input type="hidden" param="param" id="name" name="name" value="${pubSetup.name}"/>

            <div class="resultBar xw_beanSetting">
                <span class="resultText">注册赠送</span>
                <select style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;margin-bottom: 6px;" name="register" id="register" >
                    <c:forEach items="${couponList}" var="coupon">
	                    <option value="${coupon.id}" 
	                      <c:if test="${coupon.id==map.value}">selected="selected" </c:if>
	                      >${coupon.name}</option>
                    </c:forEach>
                </select>
            </div>

        </form>
    </div>
</div>
</body>
</html>

