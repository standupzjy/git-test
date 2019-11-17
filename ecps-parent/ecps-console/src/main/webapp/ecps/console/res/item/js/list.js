$(function () {
    var val = parseInt($("#showStatus").val());
    if(val == 1){
        $("#label4").attr("class","here");
    }else if(val == 0){
        $("#label5").attr("class","here");
    }else {
        $("#label6").attr("class","here");
    }

        var pageNo = parseInt($("#currentPageNo").val());
        var totalPage = parseInt($("#totalPage").val());
        if(pageNo == 1 && totalPage == 1){
            $("#firstPage").hide();
            $("#previous").hide();
            $("#next").hide();
            $("#lastPage").hide();
        }else if(pageNo == 1 && totalPage > 1){
            $("#firstPage").hide();
            $("#previous").hide();
            $("#next").show();
            $("#lastPage").show();
        }else if (pageNo == totalPage && totalPage > 1){
            $("#firstPage").show();
            $("#previous").show();
            $("#next").hide();
            $("#lastPage").hide();
        }else if(pageNo > 1 && totalPage > totalPage){
            $("#firstPage").show();
            $("#previous").show();
            $("#next").show();
            $("#lastPage").show();
        }

        $("#firstPage").click(function () {
            $("#pageNo").val(1);
            $("#form1").submit();
        });
        $("#lastPage").click(function () {
         $("#pageNo").val(totalPage);
         $("#form1").submit();
        });
        $("#previous").click(function () {
            pageNo--;
            $("#pageNo").val(pageNo);
            $("#form1").submit();
        });
        $("#next").click(function () {
            pageNo++;
            $("#pageNo").val(pageNo);
            $("#form1").submit();
        });

        $("#selectPage").change(function () {
            var myPage = $(this).val();
            $("#pageNo").val(myPage);
            $("#form1").submit();
        });

        $("#selectPage").val(pageNo);

        $("#test").click(function () {
            $("#form1").jqprint();
        });

    $("#addItemNoteConfirm").click(function () {
        var notes = $("#itemNote").val();
        $("#notes").val(notes);
        $("#showForm").submit();
    });

})

function isShow(itemId,showStatus) {
    $("#itemId").val(itemId);
    $("#showStatus3").val(showStatus);
    $("#itemNote").val("");
    tipShow("#addItemNote");
}

function publish(itemId) {
    tipShow("#refundLoadDiv");
    $.ajax({
        url:path + "/item/publishItem.do",
        dataType:"text",//回调值的数据类型
        type:"post",
        data:{
            itemId:itemId
        },
        success:function (responseText) {
            if(responseText == "success"){
                alert("发布成功")
            }else {
                alert("发布失败")
            }
            tipHide("#refundLoadDiv");
        },
        error:function () {
            alert("系统错误");
        }
    })
}