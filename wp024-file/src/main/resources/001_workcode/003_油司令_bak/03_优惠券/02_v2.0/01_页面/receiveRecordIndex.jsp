<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/headModule.jsp" %>
    <title>领取记录</title>
    <link type="text/css" href="${path}/resources/admin/css/common.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/stationIDlist.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/user_list.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/dropMenu.css" rel="stylesheet"/>
    <script src="${path}/resources/admin/js/common.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/js/sonjs.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/common/temDataGrid.js"></script>
    <script src="${path}/resources/admin/js/coupon/receiveRecordIndex.js"></script>
    
</head>
<body>
<div class="wrapper">
	<div class="positionNow">主页 &gt; <a>优惠券管理</a> &gt; <a>优惠券领取列表</a></div>
	<div class="searchBar">
        <form id="form_searchField">
            <div class="options">
                <div class="searchWrapper" style="margin-right: 30px">
                    <span class="searchName">优惠券类型：</span>
					<select style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;margin-bottom: 6px;" name="couponType" id="couponType" >
                        <option value="">全部</option>
                        <option value="coupon_type_full_subtraction">满减</option>
                        <option value="coupon_type_discount">折扣</option>
                    </select>
                </div>
                <div class="searchWrapper" style="margin-right: 30px">
                    <span class="searchName">领取时间：</span>
                    <input class="timeInput" value="" id="startDate"
                                   name="startDate"
                                   onclick="WdatePicker({ dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
                    <span class="searchName">-</span>
                    <input class="timeInput" value="" id="endDate"
                                   name="endDate"
                                   onclick="WdatePicker({ dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"/>
                </div>
                <span class="btnOrange" id="search">搜索</span>
            </div>
        </form>
    </div>
    
    <div class="resultBar">
        <div class="tickAll xw_tickAll"></div>
        <span class="resultText">共有<span class="textOrange" id="total">0</span>条数据</span>
    </div>
    <div class="consumptionList" style="min-height: 600px">
    	<table class="tableStyle xw_grouplist" cellspacing="none">
                <tr>
                    <th width="40px"></th>
                    <th>优惠券类型</th>
                    <th>领取人</th>
                    <th>手机号</th>
                    <th>生效时间</th>
                    <th>过期时间</th>
                    <th>领取时间</th>
                    <th>使用时间</th>
                </tr>
                <tbody id="dataList"></tbody>
            </table>
    </div>
    <div class="pageBar" id="pageBar"></div>
    
</div>
</body>
</html>
<script type="text/html" id="template_dataList">
    {{each}}
        <tr>
            <td><div class="tick xw_tick"></div></td>
            <td>
                {{if $value.couponType=='coupon_type_full_subtraction'}}
                <span class="shengxiao">满减</span>
                {{else if $value.couponType=='coupon_type_discount'}}
                <span class="shengxiao">折扣</span>
                {{/if}}
            </td>
            <td>{{$value.nickName}}</td>
			<td>{{$value.account}}</td>
            <td>{{$value.startDate.substring(0, 10)}}</td>
			<td>{{$value.expirationDate.substring(0, 10)}}</td>
            <td>{{$value.getTime}}</td>
            <td>{{$value.usedTime}}</td>

            <input type="hidden" name="id" value="{{$value.id}}" param="param">
        </tr>
    {{/each}}
</script>