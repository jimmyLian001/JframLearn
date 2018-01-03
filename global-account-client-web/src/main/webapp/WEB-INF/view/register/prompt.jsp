<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    .prompt-close-btn {
        display: inline-block;
        width: 50px;
        text-align: center;
        font-size: 20px;;
        cursor: pointer;
    }

    .prompt-full-mask {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        background: rgba(0, 0, 0, .4);
        z-index: 999;
    }

    .prompt-reg-dialog-title {
        background: #efefef;
        height: 35px;
        font-size: 16px;
        line-height: 35px;
        padding-left: 20px;
    }

    .prompt-sub-btn button{
        width: 20%;
        height: 35px;
        line-height: 35px;
        background-color: #1b9af7;
        color: #fff;
        font-size: 16px;
        cursor: pointer;
        border-radius: 4px;
        margin-top: 20px;
    }
</style>
<div class="prompt-full-mask" style="display:none" id="prompt-full-mask" >
    <div class="reg-dialog" style="width: 500px;">
        <div class="prompt-reg-dialog-title">
            <span class="fl">提示</span>
            <span class="fr prompt-close-btn"><i class="fa fa-times"></i></span>
        </div>
        <div class="dialog-content" style=" text-align: center;">
            <span style="color: black" id="errorPrompt"></span>
            <div class="sub-btn">
                <button style="width: 20%;height: 33px;line-height: 33px;"
                        onclick="removePrompt()">
                    确定
                </button>
            </div>
        </div>
    </div>
</div>