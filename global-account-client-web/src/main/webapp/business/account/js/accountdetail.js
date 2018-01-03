$().ready(function ($) {
    $("#accountDetailForm").validate({
        onblur: true,
        onkeyup: false,
        onclick: false,
        errorElement: "em",
        rules: {
            modifyStoreName: {
                maxlength: 128
            }
        },
        messages: {
            modifyStoreName: {
                maxlength: "网址长度不能查过128个字符"
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
function editInfo() {
    $('#showShop').toggle();
    $('#hideShop').toggle();
}
function modify() {
    var storeNo = $("#storeNo").text();
    var modifyStoreName = $("#modifyStoreName").val();
    if (modifyStoreName.length > 128 || modifyStoreName.length == 0) {
        return false;
    }
    var url = "modifyStoreName.do";
    var storeName = encodeURI(encodeURI(modifyStoreName));
    var params = "storeName=" + storeName + "&storeNo=" + storeNo;
    $.post(url, params, function (data) {
        if (data.errorCode == 1) {
            window.location.reload();
        }
    });
}
