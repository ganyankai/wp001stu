
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
                    // var companyId = $("#companyId").val();
                    // if(companyId == ""){
                    //     msgBox.tips("请选择适用公司");
                    //     return;
                    // }




                    var startDate = $("#startDate").val();
                    var endDate = $("#endDate").val();
                    if(startDate == ""){
                        $("#startDate").focus();
                        msgBox.tips("请输入开始时间");
                        return;
                    }
                    if(endDate == ""){
                        $("#endDate").focus();
                        msgBox.tips("请输入截止时间");
                        return;
                    }

                    var moneyInputArr = $(".moneyInputText");
                    for(var i = 0; i < moneyInputArr.length; i ++){
                        if($(moneyInputArr[i]).val() == "" || isNaN(parseFloat($(moneyInputArr[i]).val()))){
                            $(moneyInputArr[i]).focus();
                            msgBox.tips("请输入大于0的金额");
                            return;
                        }
                    }

                    var ruleId = $("#ruleId").val();
                    var detailArr = [];
                    var rechargeArr = $(".recharge");
                    var giveArr = $(".give");
                    var prePayMoney = 0;
                    var preGiveMoney = 0;
                    for(var j = 0; j < rechargeArr.length; j++){
                        var rechargeMoney = parseFloat($(rechargeArr[j]).val());
                        var giveMoney = parseFloat($(giveArr[j]).val());
                        //if(giveMoney >= rechargeMoney){
                        //    $(giveArr[j]).focus();
                        //    msgBox.tips("充值金额不能大于或等于赠送金额");
                        //    return;
                        //}
                        if(rechargeMoney <= prePayMoney){
                            $(rechargeArr[j]).focus();
                            msgBox.tips("充值金额不能小于上一条规则的");
                            return;
                        }
                        if(giveMoney <= preGiveMoney){
                            $(giveArr[j]).focus();
                            msgBox.tips("赠送金额不能小于上一条规则的");
                            return;
                        }
                        prePayMoney = rechargeMoney;
                        preGiveMoney = giveMoney;
                        var amountMax;
                        if(j < rechargeArr.length - 1){
                            amountMax = $(rechargeArr[j+1]).val();
                        }
                        var detail = {
                            ruleId: ruleId,
                            amountMin: rechargeMoney,
                            amountMax: amountMax,
                            giveAmount: giveMoney
                        };
                        detailArr[detailArr.length] = detail;
                    }
                    if(detailArr.length == 0){
                        msgBox.tips("至少要新增一条规则");
                        return;
                    }
                    var params = {};
                    params.id = ruleId;
                    params.groupId = groupId;
                    params.startDate = startDate;
                    params.endDate = endDate;
                    params.companyIds = companyId;
                    params.rechargeRuleDetailJson = JSON.stringify(detailArr);
                    DataHandler.save(params, function (backData) {
                        msgBox.tips("保存成功");
                        window.location.href = path + "/admin/groupOilCard/rechargeRule/index.html";
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
                    $.post(path + "/admin/groupOilCard/rechargeRule/save", params, function (result) {
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