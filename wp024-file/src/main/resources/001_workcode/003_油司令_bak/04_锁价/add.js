seajs.use(['$','adminSystem','template', 'msgBox', 'jquery.json', 'validate'],
    function ($,adminSystem, template, msgBox) {

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
                            // onSelect: function (record) {
                            //     EventHandler.search();
                            // }
                        });
                        tempProductKind.unshift({valueField : "", name : "全部"});
                        $('#productKind').combobox("loadData", tempProductKind);

                    });
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
                $("form#form").validate({
                    rules: {
                    	name: {required: true},
                    	publishQty: {required: true, digits: true},
                    	couponValue: {required: true, isNumber: true},
                    	couponCondition: {required: true, isNumber: true},
                    	startDate:{required: true},
                    	expirationDate: {required: true}
                    },
                    ignore: '.ignore',
                    errorPlacement: function (error, element) {
                    },
                    invalidHandler: function (event, validator) {
                        var tip = '';
                        for (var i in validator.errorMap) {
                            msgBox.tips($("#" + i).parent().find("span").html() + " " + validator.errorMap[i]);
                            $('#' + i).focus();
                            return;
                        }
                    }
                });
                
            return {
                /**
                 * 保存
                 */
                save: function () {
                	if(!$("form#form").valid()){
                		return;
                	}
                	// var params = {};
                    var params = function (list) {
                		var map = {};
                		$.each(list, function (i, n) {
                			map[n.name] = n.value;
                		});
                		return map;
                	}($("form#form").serializeArray());
                    
                    var a =$("input:checkbox:checked").map(function(index,element){
                        return $(element).val()
                    });
                    var arr = [];
                    for (var i = 0; i < a.length; i++) {
                        arr.push(a[i])
                    }
                    var oids =arr.join(",").toString();

                    params.oids = oids;
                	params.productKind = $("input[name='productKind']").val();
                	params.status = '1';
                    // alert($("input[name='oids']").val()+",oids");
                    DataHandler.save(params, function (backData) {
                        msgBox.tips("保存成功");
                        window.location.href = path + "/admin/lockPrice/lockPriceIndex";
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
                    $.post(path + "/admin/lockPrice/addLockPrice", params, function (result) {
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