$(function () {
    $("#prov").change(function () {
        var prov = $(this).val();
        loadOption(prov,"mycity")
    });

    $("#mycity").change(function () {
        var city = $(this).val();
        loadOption(city,"mydis")
    })

    $("#jvForm").submit(function () {
        var isSubmit = true;
        var shipAddrId = $("#shipAddrId").val();
        //判断是否为Null，为Null则说明是添加，添加就需要限制添加个数，修改则不用限制
        if(shipAddrId == null || shipAddrId == ""){
            var addrLength = parseInt($("#addrLength").val());
            if(addrLength >= 5){
                isSubmit = false;
                alert("收货地址不能大于 5 个")
            }
        }
        return isSubmit;
    })

})

function loadOption(areaId,selectId) {
    $.ajax({
        url:path+"/user/login/getChildArea.do",
        dataType:"text",
        type:"post",
        async:false,
        data:{
          areaId:areaId
        },
        success:function (responseText) {
            var jsonObj = $.parseJSON(responseText);
            var aList = jsonObj.areaList;

            if(selectId == "mycity"){
                $("#"+selectId).empty();
                $("#mydis").empty();
                $("#"+selectId).append("<option value='-1'>城市</option>");
                $("#mydis").append("<option value='-1'>县/区</option>");
            }else {
                $("#"+selectId).empty();
                $("#"+selectId).append("<option value='-1'>县/区</option>");
            }
            var aText = "";
            if(aList != null && aList.length > 0){
                for(var i = 0 ; i < aList.length;i++){
                    aText = aText + "<option value='"+aList[i].ereaId+"'>"+aList[i].ereaName+"</option>";
                }

                $("#"+selectId).append(aText);
            }
        }
    })
}

function modify(shipAddrId) {
    $.ajax({
        url:path+"/user/login/getAddrById.do",
        dataType:"text",
        type:"post",
        data:{
            shipAddrId:shipAddrId
        },
        success:function (responseText) {
            var jsonObj = $.parseJSON(responseText);
            $("#shipAddrId").val(jsonObj.shipAddr.shipAddrId);
            $("#shipName").val(jsonObj.shipAddr.shipName);
            $("#prov").val(jsonObj.shipAddr.province);

            //加载城市
            loadOption(jsonObj.shipAddr.province,"mycity");

            $("#mycity").val(jsonObj.shipAddr.city);

            loadOption(jsonObj.shipAddr.city,"mydis");

            $("#mydis").val(jsonObj.shipAddr.district);
            $("#addr").val(jsonObj.shipAddr.addr);
            $("#zipCode").val(jsonObj.shipAddr.zipCode);
            $("#phone").val(jsonObj.shipAddr.phone);
            if(jsonObj.shipAddr.defaultAddr == 1){
                $("#defaultAddr").attr("checked","checked");
            }else {
                $("#defaultAddr").removeAttr("checked");
            }
        }
    })
}