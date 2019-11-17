$(function () {
    var val = parseInt($("#auditStatus2").val());
    if(val == 1){
        $("#label3").attr("class","here");
    }else if(val == 0){
        $("#label1").attr("class","here");
    }else if(val == 2){
        $("#label2").attr("class","here");
    }else {
        $("#label4").attr("class","here");
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
        $("#auditForm").submit();
    });

})

function isPass(itemId,auditStatus) {
    $("#itemId").val(itemId);
    $("#auditStatus3").val(auditStatus);
    $("#addItemNoteH2").html("商品审核")
    $("#itemNote").val("");
    tipShow("#addItemNote");

}

