seajs.use(['$', 'template', 'msgBox', 'util', 'pageBar', 'jquery.json'],
    function ($, template, msgBox, util, pageBar) {
        /**
         * 初始化处理器
         */
        var InitHandler = function () {
            return {
                /** 初始化入口 */
                init: function () {
                    this.initPage();
                    this.initEvent();
                    // 初始化数据列表
                    EventHandler.search();
                },


                /** 初始化界面  */
                initPage: function () {
                    var params = {}; //搜索条件
                    $('.pageBar').pageBar({
                        onSelectPage: function (page, pageSize) {
                            params.pageNo = page;
                            params.pageSize = pageSize;
                            EventHandler.search(params);
                        }
                    });
                },
                /** 初始化事件 */
                initEvent: function () {
                    $('#couponType').change(function(){
                        EventHandler.search()
                    });

                    $('#state').change(function(){
                        EventHandler.search()
                    });

                    //动态绑定click(动态生成的html)
                    $("#dataList").on('click', '.xw_tick', templateList.check);

                    //删除事件
                    $('#del').click(EventHandler.del);

                    $('#unable').click(function(){
                        EventHandler.updateStatus(2);
                    });
                    $('#enable').click(function(){
                        alert("a");
                        EventHandler.updateStatus(1);
                    });

                    $("#add").click(function () {
                        EventHandler.addEdit(true);
                    });

                    $("#edit").click(function () {
                        EventHandler.addEdit(false);
                    });

                }
            }
        }();

        /**
         * 事件处理器
         */
        var EventHandler = function () {
            return {
                // 查询
                search: function (params) {
                    $(".xw_tickAll").removeClass("tickOn");

                    if(typeof(params)=="undefined"){
                        params = {pageNo : 1, pageSize : 15};
                    }
                    DataHandler.search(params, function (backData) {
                        var data = backData;
                        var html = template('template_dataList', data.content);
                        $('#dataList').html(html);

                        var page = params.pageNo;
                        var pageSize = params.pageSize;

                        $('#pageBar').pageBar({
                            total: data.total,
                            pageNumber: page,
                            pageSize: pageSize
                        });

                        setParenHei();
                    });
                },
                addEdit: function(addFlag){
                    var checked = templateList.getChecked("dataList", "tr");
                    if(addFlag){
                        // if($('#groupId').val()==''){
                        //     msgBox.tips("请选择一个区域/集团进行添加");
                        //     return;
                        // }
                        window.location.href = path + "/admin/tbcoupon/toAdd";

                    }else {
                        if (checked.length != 1) {
                            msgBox.tips("请勾选一条数据再修改");
                            return;
                        }
                        window.location.href = path + "/admin/tbcoupon/toEdit/" + checked[0].id;
                    }
                },
                // 启用/禁用
                updateStatus: function (isEnable) {
                    var name = isEnable == 1 ? "启用" : "禁用";
                    var checked = templateList.getChecked("dataList", "tr");
                    // console.log("checked:");
                    // console.log(checked);
                    if (checked.length != 1) {
                        msgBox.tips("请选择一条需要" + name + "的数据");
                        return;
                    }
                    if(isEnable == 1){
                        state = 'coupon_status_used'
                    }else{
                        state = 'coupon_status_no_use';
                    }

                    // if(checked[0].isEnable == isEnable){
                    //     msgBox.tips("此规则无需再" + name);
                    //     return;
                    // }

                    var params = {id : checked[0].id, state : state};
                    DataHandler.updateStatus(params, function (backData) {
                        msgBox.tips(name + "成功");
                        EventHandler.search();
                    });
                },
                del: function (isEnable) {
                    var checked = templateList.getChecked("dataList", "tr");
                    if (checked.length == 0) {
                        msgBox.tips("请选择需要删除的数据");
                        return;
                    }
                    msgBox.confirm({
                        title: '提示',
                        msg: '确认删除?',
                        callback: function (btnType) {
                            if (btnType == 'ok') {
                                var ruleIds = "";
                                for(var i = 0; i < checked.length; i++){
                                    var rule = checked[i];
                                    if(rule.isEnable == 1){
                                        msgBox.tips("正在启用的规则不能删除");
                                        return;
                                    }
                                    ruleIds += rule.id + ",";
                                }
                                ruleIds = ruleIds.substr(0, ruleIds.length-1);
                                var params = {ruleIds : ruleIds};
                                DataHandler.del(params, function (backData) {
                                    msgBox.tips("删除成功");
                                    EventHandler.search();
                                });
                            }
                        }
                    })
                }
            }
        }();


        /**
         * 数据处理器
         */
        var DataHandler = function () {
            return {
                /**
                 * 获取数据列表
                 */
                search: function (param, callback) {
                    // param.groupId = $("#groupId").val();
                    param.couponType = $("#couponType").val();
                    param.state = $("#state").val();
                    $.post(path + '/admin/tbcoupon/tbCouponList', param, function (backData) {
                        callback(backData);
                    });
                },
                /**
                 * 启用/禁用
                 */
                updateStatus: function (param, callback) {
                    $.post(path + '/admin/tbcoupon/updateTbCoupon', param, function (backData) {
                        callback(backData);
                    });
                },
                /**
                 * 删除
                 */
                del: function (param, callback) {
                    $.post(path + '/admin/groupOilCard/rechargeRule/delete', param, function (backData) {
                        callback(backData);
                    });
                }
            }
        }();

        $(function () {
            InitHandler.init();
        })
    }
);
