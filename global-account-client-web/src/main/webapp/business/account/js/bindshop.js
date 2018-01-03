$().ready(function ($) {
    $("#applyForm").validate({
        onblur: true,
        onkeyup: false,
        onclick: false,
        errorElement: "em",
        rules: {
            accountHolderWebsite: {
                required: true,
                maxlength:256,
                url:true
            },
            storeName: {
                required: true,
                maxlength:128
            }
        },
        messages: {
            accountHolderWebsite: {
                required:"请输入网址",
                maxlength:"网址长度不能查过256个字符",
                url:"请输入正确的网址",
            },
            storeName: {
                required:"请输入店铺名",
                maxlength:"店铺名长度不能查过128个字符"

            }
        },
        errorPlacement: function (error, element) {
            if (element.attr("name") == "fname" || element.attr("name") == "lname") {
                error.insertAfter("#lastname");
            } else {
                error.insertAfter(element);
            }
        }
    })
})