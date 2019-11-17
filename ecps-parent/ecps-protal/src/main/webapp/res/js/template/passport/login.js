$(function () {

    $(".bg_text input").blur(function () {
        var val = $(this).val();
        val = val.trim();
        var attr = $(this).attr("name");
        if (val == null || val == ""){
            if(attr == "username"){
                $("#errorName").html("请输入用户名");
            }else if(attr == "password"){
                $("#errorName").html("请输入密码");
            }else if(attr == "captcha"){
                $("#errorName").html("请输入验证码");
            }
            $("#errorName").show(200);
        }else {
            $("#errorName").hide(200);
        }
    });
    var tip = $("#tip").val();
    if(tip == "cap_error"){
        $("#errorName").html("验证码错误");
        $("#errorName").show(500);
    }else if(tip =="user_error"){
        $("#errorName").html("用户名或密码错误");
        $("#errorName").show(500);
    }
});

function changImg() {
    $("#captchaChangImg").attr("src",path + "/user/getImg.do?date="+new Date());

}