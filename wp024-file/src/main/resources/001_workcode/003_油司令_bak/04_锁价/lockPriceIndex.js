seajs.use(['$', 'adminSystem','template', 'msgBox', 'util', 'pageBar', 'jquery.json'],
    function ($, adminSystem, template, msgBox, util, pageBar) {
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

                    adminSystem.getEnumList([{
                        tabName: "T_OILSTATION_PRODUCT",
                        colName: "I_KIND"
                    }], 2, function (result) {
                        var tempProductKind = result['T_OILSTATION_PRODUCT-I_KIND'].sEnumMapping;
                        // $('div.searchBar #couponType').combobox({
                        $('#productKind').combobox({
                            panelHeight: 80,
                            textField: 'name',
                            valueField: 'value',
                            editable: false,
                            onSelect: function (record) {
                                EventHandler.search();
                            }
                        });
                        tempProductKind.unshift({valueField : "", name : "全部"});
                        $('#productKind').combobox("loadData", tempProductKind);

                    });

                    adminSystem.getEnumList([{
                        tabName: "T_LOCK_PRICE",
                        colName: "STATUS"
                    }], 2, function (result) {
                        var tempStatus = result['T_LOCK_PRICE-STATUS'].sEnumMapping;
                        // $('div.searchBar #couponType').combobox({
                        $('#status').combobox({
                            panelHeight: 80,
                            textField: 'name',
                            valueField: 'value',
                            editable: false,
                            onSelect: function (record) {
                                EventHandler.search();
                            }
                        });
                        tempStatus.unshift({valueField : "", name : "全部"});
                        $('#status').combobox("loadData", tempStatus);

                    });

                },
                /** 初始化事件 */
                initEvent: function () {
                    // $('#couponType').change(function(){
                    //     EventHandler.search()
                    // });
                    //
                    // $('#state').change(function(){
                    //     EventHandler.search()
                    // });

                    $('#search').click(function(){
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
                    var groupId = $("#groupId").val();
                    if($('#groupId').val()==''){
                        msgBox.tips("请选择一个集团");
                        return;
                    }
                    if(addFlag){
                        window.location.href = path + "/admin/lockPrice/toAdd/"+groupId;
                    }else {
                        if (checked.length != 1) {
                            msgBox.tips("请勾选一条数据再修改");
                            return;
                        }
                        window.location.href = path + "/admin/lockPrice/toEdit/" + checked[0].id+"/"+groupId;
                    }
                },
                // 启用/禁用
                updateStatus: function (isEnable) {
                    var name = isEnable == 1 ? "启用" : "禁用";
                    var checked = templateList.getChecked("dataList", "tr");

                    if (checked.length != 1) {
                        msgBox.tips("请选择一条需要" + name + "的数据");
                        return;
                    }
                    if(isEnable == 1){
                        status = '1' ;
                    }else{
                        status = '0';
                    }

                    // if(checked[0].isEnable == isEnable){
                    //     msgBox.tips("此规则无需再" + name);
                    //     return;
                    // }

                    var params = {id : checked[0].id, status : status};
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
                                var ids = "";
                                for(var i = 0; i < checked.length; i++){
                                    var rule = checked[i];
                                    if(rule.status == 1){
                                        msgBox.tips("正在启用的锁价不能删除");
                                        return;
                                    }
                                    ids += rule.id + ",";
                                }
                                ids = ids.substr(0, ids.length-1);
                                var params = {ids : ids};
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
                    param.groupId = $("#groupId").val();
                    param.productKind = $("input[name='productKind']").val();
                    param.status = $("input[name='status']").val();
                    $.post(path + '/admin/lockPrice/lockPriceList', param, function (backData) {
                        callback(backData);
                    });
                },
                /**
                 * 启用/禁用
                 */
                updateStatus: function (param, callback) {
                    $.post(path + '/admin/lockPrice/updateStatus', param, function (backData) {
                        callback(backData);
                    });
                },
                /**
                 * 删除
                 */
                del: function (param, callback) {
                    $.post(path + '/admin/lockPrice/delete', param, function (backData) {
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
