/**
 * Created by linziyu on 2019/2/7.
 */


// 注册处理
layui.use(['form','jquery','layer'], function () {
    var form   = layui.form;
    var $      = layui.jquery;
    var layer  = layui.layer;

    // 为姓名添加为空验证
    $('#sname').blur(function() {
        //console.log(($('#sname').val())=="");
        //var reg = /^[\w]{1,12}$/;
        if(""==($('#sname').val())){
            //layer.msg('请输入合法密码');
            $('#swr').removeAttr('hidden');
            $('#sri').attr('hidden','hidden');
            layer.msg('请输入姓名');
            check='name';
        }
        if(""!=($('#sname').val())){
            //layer.msg('请输入合法密码');
            $('#sri').removeAttr('hidden');
            $('#swr').attr('hidden','hidden');
            check="";
        }
    });

    // 为身份证添加为空验证
    $('#identity').blur(function() {
        //var reg = /^[\w]{1,12}$/;
        var id=$('#identity').val();
        if(getLen(id)!=18){
            $('#iwr').removeAttr('hidden');
            $('#iri').attr('hidden','hidden');
            layer.msg('请输入18位身份证号码');
            check='id';
        }
        if(getLen(id)==18){
            $('#iri').removeAttr('hidden');
            $('#iwr').attr('hidden','hidden');
            //layer.msg('请输入18位身份证号码');
            check="";
        }
    });

    //获取字符串长度
    function getLen(str) {
        if (str == null) return 0;
        if (typeof str != "string"){
            str += "";
        }
        return str.replace(/[^\x00-\xff]/g,"01").length;
    };

    // you code ...
    // 为密码添加正则验证
    $('#password').blur(function() {
        var reg = /^[\w]{1,12}$/;
        if(!($('#password').val().match(reg))){
            //layer.msg('请输入合法密码');
            $('#pwr').removeAttr('hidden');
            $('#pri').attr('hidden','hidden');
            layer.msg('请输入合法密码');
            check="pwdcheck";
        }else {
            $('#pri').removeAttr('hidden');
            $('#pwr').attr('hidden','hidden');
            check="";
        }
    });

    //验证两次密码是否一致
    $('#rpwd').blur(function() {
        if($('#password').val() != $('#rpwd').val()){
            $('#rpwr').removeAttr('hidden');
            $('#rpri').attr('hidden','hidden');
            layer.msg('两次输入密码不一致!');
            check='pwd';
        }else {
            $('#rpri').removeAttr('hidden');
            $('#rpwr').attr('hidden','hidden');
            check="";
        };
    });

    //添加表单失焦事件
    //验证表单
    var check = "";//全局参数 用于提交表单时的校验
    $('#username').blur(function() {
        var name = $(this).val();

        $.ajax({
            url:'/checkNameIsExistOrNot',//用户名框失去焦点是进行ajax请求 检验用户名是否重复了
            type:'post',
            dataType:'json',
            data:{username:name},
            //验证用户名是否可用
            success:function(data){
                if (data.body.code == 1000) {
                    $('#ri').removeAttr('hidden');
                    $('#wr').attr('hidden','hidden');
                    check="";

                } else {
                    check = "当前用户名已被占用";
                    $('#wr').removeAttr('hidden');
                    $('#ri').attr('hidden','hidden');
                    layer.msg('当前用户名已被占用! ');
                    check="OK";
                }

            }
        })

    });

    //
    //添加表单监听事件,提交注册信息
    form.on('submit(sub)', function(data) {
        if(check == "OK"){
            layer.alert("该用户名已经被使用了");//提交前的校验
            return;
        }
        if(check == "pwd"){
            layer.alert("两次输入密码不一致");//提交前的校验
            return;
        }
        if(check == "pwdcheck"){
            layer.alert("请输入合法密码");//提交前的校验
            return;
        }
        if(check == "pwdcheck"){
            layer.alert("两次密码不一致");//提交前的校验
            return;
        }
        if(check == "id"){
            layer.alert("请输入正确的身份证号码");//提交前的校验
            return;
        }
        if(check == "name"){
            layer.alert("请输入姓名");//提交前的校验
            return;
        }
        $.ajax({
            url:'/register_form',
            type:'post',
            dataType:'json',
            data:{
                username:$('#username').val(),
                password:$('#password').val(),
                sname:$('#sname').val(),
                identity:$('#identity').val(),
                iroleid:$('#iroleid').val()
            },
            success:function(data){
                //alert(data.body.code);
                if (data.body.code == 1000 ) {
                    layer.alert('注册成功');
                    //两秒后跳转新页面
                    window.setTimeout("window.location='login_page'",2000);
                }else {
                    layer.msg('注册失败');
                }
            }
        });
        //防止页面跳转
        return false;
    });

});
