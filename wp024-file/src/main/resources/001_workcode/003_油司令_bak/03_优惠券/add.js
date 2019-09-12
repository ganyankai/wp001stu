
seajs.use(['$', 'template', 'msgBox', 'jquery.json'],
    function ($, template, msgBox) {

        /** 初始化处理器 */
        var InitHandler = (function () {
            return {
                /** 初始化入口 */
                init: function () {
                    this.initPage();
                    this.initEvent();
                    this.initData();
                },

                /** 初始化界面 */
                initPage: function () {

                },
                /** 初始化事件绑定 */
                initEvent: function () {
                    //增加
                    $(".addBar").click(EventHandler.add);
                    //删除
                    $(".ruleBox").on('click', '.deleBtn', EventHandler.del);
                    //基础信息保存
                    $("#save").click(EventHandler.save);
                    //选择公司
                    // $("#selectCompany").click(EventHandler.selectCompany);
                },

                /** 初始化数据加载 */
                initData: function () {
                }
            };

        })();

        /** 事件处理器 */
        var EventHandler = (function () {
            return {
                /**
                 * 选择公司
                 */
                // selectCompany: function(){
                //     var param = {companyIds : [], companyNames : []}
                //     if($("#companyId").val() != ""){
                //         param = {companyIds : $("#companyId").val().split(","), companyNames : $("#company").html().split("、")}
                //     }
                //     param.type = 1;
                //     msgBox.exWindow.open({
                //         title: '选择适用公司',
                //         width: '700px',
                //         height: '500px',
                //         url: path + "/admin/groupCardMgr/companyCus/choose.html?groupId="+groupId,
                //         extraParams: param,
                //         close: function (data) {
                //             if (data) {
                //                 var param = $.evalJSON(data);
                //                 $("#companyId").val(param.companyIds.join(","));
                //                 $("#company").html(param.companyNames.join("、"));
                //             }
                //         }
                //     });
                // },
                /**
                 * 新增
                 */
                add: function(){
                    var html = template('template_dataList', [{}]);
                    $(this).before(html);
                    setParenHei();
                },
                del: function(){
                    $(this).parent().remove();
                    setParenHei();
                },
                /**
                 * 保存
                 */
                save: function () {
                    var couponType = $("#couponType").val();
                    var publishQty = $("#publishQty").val();
                    var couponValue = $("#couponValue").val();
                    var couponCondition = $("#couponCondition").val();
                    var startDate = $("#startDate").val();

                    var expirationDate = $("#expirationDate").val();


                    if(publishQty == ""){
                        $("#publishQty").focus();
                        msgBox.tips("请输入发行数量");
                        return;
                    }
                    if(couponValue == ""){
                        $("#couponValue").focus();
                        msgBox.tips("请输入发行面值");
                        return;
                    }

                    if(startDate == ""){
                        $("#startDate").focus();
                        msgBox.tips("请输入开始时间");
                        return;
                    }
                    if(expirationDate == ""){
                        $("#expirationDate").focus();
                        msgBox.tips("请输入截止时间");
                        return;
                    }

                    var params = {};
                    params.couponType = couponType;
                    params.publishQty = publishQty;
                    params.couponValue = couponValue;
                    params.couponCondition = couponCondition;
                    params.startDate = startDate;

                    params.expirationDate = expirationDate;
                    params.state ='coupon_status_no_use';
                    // params.rechargeRuleDetailJson = JSON.stringify(detailArr);

                    DataHandler.save(params, function (backData) {
                        msgBox.tips("保存成功");
                        window.location.href = path + "/admin/tbcoupon/tbCouponIndex";
                    });
                }
            };
        })();

        /** 数据处理器 */
        var DataHandler = (function () {
            return {
                /**
                 * 保存
                 * @param params
                 * @param callback
                 */
                save: function (params, callback) {
                    $.post(path + "/admin/tbcoupon/addTbCoupon", params, function (result) {
                        callback(result);
                    });
                }
            };
        })();

        /** 页面入口 */
        $(function () {
            InitHandler.init();
        });

    });

//小数验证
function testNumber(ob){
    if (!ob.value.match(/^[\+]?\d*?\.?\d*?$/)) ob.value = ob.t_value; else ob.t_value = ob.value;
    if (ob.value.match(/^(?:[\+]?\d+(?:\.\d+)?)?$/)) ob.o_value = ob.value;
    if(ob.value == 'undefined'){
        ob.value = '';
    }
}