$(function () {
    $.ajax({
        url:path + "/user/getUser.do",
        dateType:"text",
        type:"post",
        success:function (responseText) {
            var josnObj = $.parseJSON(responseText);
            if(josnObj.user != null){
                $("#loginAlertIs").html(josnObj.user.username)
            }
        }

    });
});