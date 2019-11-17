function submitUpload(){
    var option = {
        url:path + "/upload/uploadPic.do", //做文件上传的url
        dataType:"text",//回调值的数据类型
        success:function (responseText) {
            var jsonObj = $.parseJSON(responseText);
            $("#imgsImgSrc").attr("src",jsonObj.realPath);
            $("#imgs").val(jsonObj.relativePath);
            $("#lastRealPath").val(jsonObj.realPath);
        },
        error:function () {
            alert("系统错误");
        }
    }

    $("#form111").ajaxSubmit(option);

}

$(function () {
    $("#reset1").click(function () {
        var selectPath = $("#lastRealPath").val();
        deletePic(selectPath);
        var brandId = parseInt($("#brandId").val());
        window.location.href = "http://localhost:8081/item/editBrand.do?brandId="+brandId;
    });

    $("#form111").submit(function () {
        var isSubmit = true;

        //检验必填字段
        $(this).find("[reg2]").each(function () {
            //获得输入的值,并去掉空格
            var val = $(this).val().trim();
            //获得正则
            var reg2 = $(this).attr("reg2");
            //获得提示
            var tip = $(this).attr("tip");
            //创建正则表达式的对象
            var regExp = new RegExp(reg2);
            if(!regExp.test(val)){
                $(this).next("span").html("<font color='red'>"+tip+"</font>");
                isSubmit = false;
                //jquery中跳出循环要使用 return false
                return false;
            }else {
                if($(this).attr("name") == "brandName"){
                    var result = validBrandName(val);
                    if(result == "no"){
                        $(this).next("span").html("<font color='red'>品牌名称已存在</font>");
                        isSubmit = false;
                        return false;
                    }else {
                        $(this).next("span").html("");
                    }
                }else {
                    $(this).next("span").html("");
                }
            }
        });

        //检验非必填字段
        $(this).find("[reg1]").each(function () {
            //获得输入的值,并去掉空格
            var val = $(this).val().trim();
            //获得正则
            var reg1 = $(this).attr("reg1");
            //获得提示
            var tip = $(this).attr("tip");
            //创建正则表达式的对象
            var regExp = new RegExp(reg1);
            if(val != null && val != "" && !regExp.test(val)){
                $(this).next("span").html("<font color='red'>"+tip+"</font>");
                isSubmit = false;
                //jquery中跳出循环要使用 return false
                return false;
            }else {
                $(this).next("span").html("");
            }
        });
        //防止表单二次提交
        if(isSubmit){
            tipShow("#refundLoadDiv");
        }
        return isSubmit;
    });
    //检验必填字段
    $("#form111").find("[reg2]").blur(function () {
        //获得输入的值,并去掉空格
        var val = $(this).val().trim();
        //获得正则
        var reg2 = $(this).attr("reg2");
        //获得提示
        var tip = $(this).attr("tip");
        //创建正则表达式的对象
        var regExp = new RegExp(reg2);
        if(!regExp.test(val)){
            $(this).next("span").html("<font color='red'>"+tip+"</font>");

        }else {
            if($(this).attr("name") == "brandName"){
                var result = validBrandName(val);
                if(result == "no"){
                    $(this).next("span").html("<font color='red'>品牌名称已存在</font>");
                }else {
                    $(this).next("span").html("");
                }
            }else {
                $(this).next("span").html("");
            }
        }
    });

    //检验非必填字段
    $("#form111").find("[reg1]").blur(function () {
        //获得输入的值,并去掉空格
        var val = $(this).val().trim();
        //获得正则
        var reg1 = $(this).attr("reg1");
        //获得提示
        var tip = $(this).attr("tip");
        //创建正则表达式的对象
        var regExp = new RegExp(reg1);
        if(val != null && val != "" && !regExp.test(val)){
            $(this).next("span").html("<font color='red'>"+tip+"</font>");
        }else {
            $(this).next("span").html("");
        }
    });
})

function validBrandName(brnadName) {
    var result = "yes";
    $.ajax({
        url:path+"/item/validBrandName.do",
        dataType:"text",
        async:false,//把ajax变为同步的
        type:"post",
        data:{
            brandName:brnadName
        },
        success:function (reponseText) {
            result = reponseText;
        },
        error:function () {
            alert("系统错误")
        }
    })
    return result;
}

function deletePic(selectPath) {
    $.ajax({
        url:path+"/item/deletePic.do",
        dataType:"text",
        async:false,//把ajax变为同步的
        type:"post",
        data:{
            selectPath:selectPath
        },
        success:function (reponseText) {
            alert(reponseText)
        },
        error:function () {
            alert("系统错误")
        }
    })
}