// 同意协议确定按钮是否可以使用
$().ready(function ($) {
    var applybnt = $("#applybnt");

    if ($("#authStatus").val() != 2) {
        applybnt.attr({"disabled": "disabled"});
    }


})
function onImgError(dom) {
    var $dom = $(dom);//转转成Jquery对象
    $dom.attr("src", "/resource/images/bank/BANK.png");
    dom.onerror = null;

}