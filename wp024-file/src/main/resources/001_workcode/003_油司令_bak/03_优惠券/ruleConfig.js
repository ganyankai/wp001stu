/**
 * Created by Zhou mingxiang on 15-12-22.
 */
seajs.use(['$', 'validate', 'msgBox', 'util', 'jquery.json'],
    function ($, validate, msgBox, util) {
        /**
         * 初始化处理器
         */
        var InitHandler = function () {
            return {
                /** 初始化入口 */
                init: function () {
                    this.initPage();
                    this.initEvent();
                },
                initPage: function () {

                },
                /** 初始化事件 */
                initEvent: function () {
                    //优惠券设置保存事件
                    $('#btnSave').click(function () {
                        var params = {};
                        params.id = $('#id').val();
                        params.code = $('#code').val();
                        params.name = $('#name').val();
                        params.value = $.toJSON({
                            register: $('#register').val()
                        });
                        EventHandler.saveUpdate(params);
                    });
                }
            }
        }();

        /**
         * 事件处理器
         */
        var EventHandler = function () {
            return {
                saveUpdate: function (params) {
                    // if ($("#form").valid() && $("#form1").valid()) {
                    DataHandler.saveUpdate(params, function (backData) {
                        if (backData.type == 'success') {
                            msgBox.tips("操作成功");
                        }
                        //window.location.reload();
                        window.location.href = path + '/admin/tbcoupon/ruleConfig';
                    });
                    // }
                }
            }
        }();

        /**
         * 数据处理器
         */
        var DataHandler = function () {
            return {

                saveUpdate: function (param, callback) {
                    $.post(path + '/admin/oilGoldMgr/oilGoldRecord/saveOrUpdate', param, function (backData) {
                        callback(backData);
                    });
                }
            }
        }();

        $(function () {
            InitHandler.init();
        })
    });