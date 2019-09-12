<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/headModule.jsp" %>
    <title>锁价列表</title>
    <link type="text/css" href="${path}/resources/admin/css/common.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/dingdanjilu.css" rel="stylesheet" />
    <link type="text/css" href="${path}/resources/admin/css/dropMenu.css" rel="stylesheet"/>
    <script src="${path}/resources/admin/js/common.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/js/sonjs.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/common/temDataGrid.js"></script>
    <script src="${path}/resources/admin/js/lockPrice/lockPriceIndex.js"></script>

</head>
<body>
<div class="position_now">您当前的位置 : <a>锁价列表</a></div>


<div class="wrapper">
    <div class="tabDivHide xw_showContent">
        <div class="contentBox">
            <div class="contentTitle searchBar">
                <span class="titleText" >&nbsp;锁价列表</span>

                <%--<div class="searchBox" style="margin-left: 50px;margin-right: 5px; line-height: 30px;">--%>
                    <%--<span class="searchName" style="height:30px;">油品111：</span>--%>
                    <%--<div class="searchBox xw_searchBox" style="line-height:26">--%>
                        <%--<input class="searchInputSelect xw_input" value="" name="productKind" id="productKind" type="text" style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;margin-bottom: 6px;"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="searchBox" style="margin-left: 50px;margin-right: 5px; line-height: 30px;">
                    <span class="searchName" style="height:30px;">油品：</span>
                    <div class="searchBox xw_searchBox" style="line-height:26px;">
                        <input class="searchInputSelect xw_input" value="" name="productKind" id="productKind" type="text" style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;margin-bottom: 6px;"/>
                    </div>
                </div>

                <div class="searchBox" style="margin-left: 50px;margin-right: 5px; line-height: 30px;">
                    <span class="searchName" style="height:30px;">状态：</span>
                    <div class="searchBox xw_searchBox" style="line-height:26px;">
                        <input class="searchInputSelect xw_input" value="" name="status" id="status" type="text" style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;margin-bottom: 6px;"/>
                    </div>
                </div>

                <%--<div class="searchBox" style="margin-left: 50px;margin-right: 5px;">--%>
                    <%--<span class="searchName" style="height:30px;line-height:30px;">状态：</span>--%>
                    <%--<select style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;margin-bottom: 6px;" name="state" id="state" >--%>
                        <%--<option value="">全部</option>--%>
                        <%--<option value="coupon_status_no_use">未启用</option>--%>
                        <%--<option value="coupon_status_used">已启用</option>--%>
                        <%--<option value="coupon_status_invalid">已失效</option>--%>
                    <%--</select>--%>
                <%--</div>--%>

                <div class="searchBox" style="height:30px;line-height:30px;">
                    <span class="searchName">区域/集团：</span>
                    <select style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;" name="groupId" id="groupId">
                        <option value=""></option>
                        <c:forEach items="${oilstationGroupList}" var="og">
                            <option value="${og.id}"
                                    <c:if test="${groupId==og.id}">selected="selected"</c:if>
                            >${og.name}</option>
                        </c:forEach>
                    </select>
                </div>


                <span class="btnGray" style="margin-top:-3px" id="del">删除</span>

                <span class="btnOrange" id="unable">禁用</span>
                <span class="btnOrange" id="enable">启用</span>
                <span class="btnOrange" id="edit">修改</span>
                <span class="btnOrange" id="add">添加</span>
                &nbsp
                <span class="btnOrange" id="search">搜索</span>
            </div>
            <table class="tableStyle" cellspacing="none">
                <tr>
                    <th width="40px"><div class="tickAll xw_tickAll"></div></th>
                    <th>油品</th>
                    <th>加油站</th>
                    <th>状态</th>
                </tr>
                <tbody id="dataList"></tbody>
            </table>
        </div>
        <div class="pageBar" id="pageBar">
        </div>
    </div>
</div>
</body>
</html>
<script type="text/html" id="template_dataList">
    {{each}}
    <%--<tr ondblclick="window.location.href = path +'/admin/groupOilCard/rechargeRule/detail/{{$value.id}}'"--%>
    <%--style="cursor: pointer;" title="双击查看详情">--%>
        <tr>
            <td><div class="tick xw_tick"></div></td>
			<td>{{$value.productKindName}}</td>
            <td>{{$value.stationListStr}}</td>
            <td>{{$value.statusName}}</td>

            <input type="hidden" name="id" value="{{$value.id}}" param="param">
            <input type="hidden" name="status" value="{{$value.status}}" param="param">
        </tr>
    {{/each}}
</script>

