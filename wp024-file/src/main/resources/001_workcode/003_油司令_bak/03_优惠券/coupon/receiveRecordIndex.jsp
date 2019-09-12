<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/headModule.jsp" %>
    <title>领取记录</title>
    <link type="text/css" href="${path}/resources/admin/css/common.css" rel="stylesheet"/>
    <link type="text/css" href="${path}/resources/admin/css/dingdanjilu.css" rel="stylesheet" />
    <link type="text/css" href="${path}/resources/admin/css/dropMenu.css" rel="stylesheet"/>
    <script src="${path}/resources/admin/js/common.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/js/sonjs.js" type="text/javascript"></script>
    <script src="${path}/resources/admin/common/temDataGrid.js"></script>
    <script src="${path}/resources/admin/js/coupon/receiveRecordIndex.js"></script>
    
</head>
<body>
<div class="position_now">您当前的位置 : <a>领取记录</a></div>
<div class="wrapper">
    <div class="tabDivHide xw_showContent">
        <div class="contentBox">
            <div class="contentTitle">
                <span class="titleText" >&nbsp;优惠券列表</span>

                <div class="searchBox" style="margin-left: 50px;margin-right: 5px;">
                    <span class="searchName" style="height:30px;line-height:30px;">优惠券类型：</span>
                    <select style="border: 1px solid #cae5bc;vertical-align: middle;height: 28px;line-height: 28px;margin-bottom: 6px;" name="couponType" id="couponType" >
                        <option value="">全部</option>
                        <option value="coupon_type_full_subtraction">满减</option>
                        <option value="coupon_type_discount">折扣</option>
                    </select>
                </div>
                <%--<div class="searchBox" style="margin-left: 50px;margin-right: 5px;">--%>
                <div class="" style="margin-left: 50px;margin-right: 5px;">
                    <tr>
                        <td>领取时间：</td>
                        <td>
                            <input class="timeInput" value="" id="startDate"
                                   name="startDate"
                                   onclick="WdatePicker({ dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
                        </td>
                        <td>
                            <input class="timeInput" value="" id="endDate"
                                   name="endDate"
                                   onclick="WdatePicker({ dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"/>
                        </td>
                    </tr>
                </div>

                <span class="btnOrange" id="search">搜索</span>
            </div>
            <table class="tableStyle" cellspacing="none">
                <tr>
                    <th width="40px"><div class="tickAll xw_tickAll"></div></th>
                    <th>优惠券类型</th>
                    <th>领取人</th>
                    <th>生效时间</th>
                    <th>领取时间</th>
                    <th>使用时间</th>
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
            <td>{{$value.startDate}}</td>
            <td>{{$value.getTime}}</td>
            <td>{{$value.usedTime}}</td>

            <input type="hidden" name="id" value="{{$value.id}}" param="param">
        </tr>
    {{/each}}
</script>