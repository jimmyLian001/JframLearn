//验证码倒计时
var wait;//时间s
var timer;

function time(thisId, time) {
    wait = time;
    setTime(thisId)
}

//倒计时
function setTime(thisId) {

    if (wait == 0) {
        $('#' + thisId).attr("disabled", false);
        $('#' + thisId).html('点击免费获取');
    } else {
        $('#' + thisId).attr("disabled", true);
        $('#' + thisId).html('重新发送(' + wait + "s)");
        wait--;
        timer = setTimeout(function () {
            setTime(thisId)
        }, 1000)
    }
}

//停止
function clearTime(){
    clearTimeout(timer);
}

//停止
function clearRegisterTime(thisId) {
    clearTimeout(timer);
    $('#' + thisId).html('点击免费获取');
    $('#' + thisId).attr("disabled", false);
}