    /*初始城市信息*/
    function toMyAccount() {
        var userNo = $("input[name='userNo']").val();
        var loginNo = $("input[name='loginNo']").val();
        var userType = $("input[name='userType']").val();
        var params = [
            {name:'userNo', value:userNo},
            {name:'loginNo', value:loginNo},
            {name:'userType', value:userType}
        ];
        $.ajax({
            url: ctx+"/auth/toMyAccount.do",
            type: "post",
            dataType: "json",
            data: params,
            success: function(data){
                if (data.code == 'OK') {
                    window.location.href = ctx + "/account/index.do"
                }
            }
        });
    }