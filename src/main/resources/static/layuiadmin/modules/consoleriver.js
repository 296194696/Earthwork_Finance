/** layuiAdmin.std-v1.1.0 LPPL License By http://www.layui.com/admin/ */
var result;
$.ajax({
    url:"/charge/info/findSumBySname",
    type:"POST",
    dataType:"JSON",
    cache : false,
    async : false,
    success:function(data){
        // return data;
        result = data;
        return;
    }
});
var chargeCity;
$.ajax({
    url:"/charge/info/findChargeCity",
    type:"POST",
    dataType:"JSON",
    cache : false,
    async : false,
    success:function(data){
        // return data;
        chargeCity = data;
        return;
    }
});
console.log(chargeCity.data.series);
;layui.define(function (e) {
    var a = layui.admin;
    layui.use(["admin", "carousel"], function () {
        var e = layui.$, a = (layui.admin, layui.carousel), l = layui.element, t = layui.device();
        e(".layadmin-carousel").each(function () {
            var l = e(this);
            a.render({
                elem: this,
                width: "100%",
                arrow: "none",
                interval: l.data("interval"),
                autoplay: l.data("autoplay") === !0,
                trigger: t.ios || t.android ? "click" : "hover",
                anim: l.data("anim")
            })
        }), l.render("progress")
    }), layui.use(["carousel", "echarts"], function () {
        var e = layui.$, a = (layui.carousel, layui.echarts), l = [], t =  [{
            title: {text: "作物水费统计", x: "center", textStyle: {fontSize: 14}},
            tooltip: {trigger: "item", formatter: "{a} <br/>{b} : {c} ({d}%)"},
            legend: {orient: "vertical", x: "left", data: result.data.snames},
            series: [{
                name: "作物信息",
                type: "pie",
                radius: "55%",
                center: ["50%", "50%"],
                data: result.data.charges
            }]
        }], i = e("#LAY-index-dataview").children("div"), n = function (e) {
            l[e] = a.init(i[e], layui.echartsTheme), l[e].setOption(t[e]), window.onresize = l[e].resize
        };
        if(i[0]){
            n(0);
            var u = [], x = [{
                title: {text: "政区水费统计", subtext: "数据来系统"},
                tooltip: {trigger: "axis"},
                legend: {data: ["2019年", "2018年"]},
                calculable: !0,
                xAxis: [{type: "value", boundaryGap: [0, .01]}],
                yAxis: [{type: "category", data: chargeCity.data.name}],
                series: chargeCity.data.series,
            }], v = e("#LAY-index-pageone").children("div"), b = function (e) {
                u[e] = a.init(v[e], layui.echartsTheme), u[e].setOption(x[e]), window.onresize = u[e].resize
            };
            v[0] && b(0)
        }
    }), layui.use("table", function () {
        var e = (layui.$, layui.table);
        e.render({
            elem: "#LAY-index-topSearch",
            url: layui.setter.base + "json/console/top-search.js",
            page: !0,
            cols: [[{type: "numbers", fixed: "left"}, {
                field: "keywords",
                title: "关键词",
                minWidth: 300,
                templet: '<div><a href="https://www.baidu.com/s?wd={{ d.keywords }}" target="_blank" class="layui-table-link">{{ d.keywords }}</div>'
            }, {field: "frequency", title: "搜索次数", minWidth: 120, sort: !0}, {
                field: "userNums",
                title: "用户数",
                sort: !0
            }]],
            skin: "line"
        }), e.render({
            elem: "#LAY-index-topCard",
            url: layui.setter.base + "json/console/top-card.js",
            page: !0,
            cellMinWidth: 120,
            cols: [[{type: "numbers", fixed: "left"}, {
                field: "title",
                title: "标题",
                minWidth: 300,
                templet: '<div><a href="{{ d.href }}" target="_blank" class="layui-table-link">{{ d.title }}</div>'
            }, {field: "username", title: "发帖者"}, {field: "channel", title: "类别"}, {
                field: "crt",
                title: "点击率",
                sort: !0
            }]],
            skin: "line"
        })
    }), e("console", {})
});