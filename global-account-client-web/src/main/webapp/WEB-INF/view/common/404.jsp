<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>错误页面</title>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        body {
            min-width: 75pc;
            background: #f0f1f3;
            font: 14px/1.5 'Microsoft YaHei', '微软雅黑', Helvetica, Sans-serif;
        }

        ol, ul {
            list-style: none;
        }

        h1, h2, h3, h4, h5, h6, strong {
            font-weight: 700;
        }

        a {
            color: #428bca;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .error-page-main {
            position: absolute;
            top: 50%;
            left: 50%;
            margin: 0 auto;
            margin-top: -280px;
            margin-left: -308px;
            padding: 50px 50px 70px;
            width: 517px;
            height: 247px;
            background: #f9f9f9;
        }

        .error-page-main h3 {
            border-bottom: 1px solid #d0d0d0;
            font-weight: 400;
            font-size: 24px;
        }

        .error-page-main h3 strong {
            margin-right: 20px;
            font-weight: 400;
            font-size: 54px;
        }

        .error-page-main h4 {
            color: #333;
            font-weight: 400;
            font-size: 20px;
        }

        .error-page-actions {
            z-index: 100;
            font-size: 0;
        }

        .error-page-actions div {
            display: inline-block;
            box-sizing: border-box;
            padding: 30px 0 0 10px;
            width: 50%;
            color: #838383;
            font-size: 14px;
            -ms-box-sizing: border-box;
        }

        .error-page-actions ol {
            padding-left: 20px;
            list-style: decimal;
        }

        .error-page-actions li {
            line-height: 2.5em;
        }

        .error-page-actions ul li a {
            margin-right: 15px;
        }

        .error-page-actions:before {
            left: 50px;
            -webkit-transform: rotate(-4deg);
            transform: rotate(-4deg);
        }

        .error-page-actions:after, .error-page-actions:before {
            position: absolute;
            bottom: 17px;
            z-index: -1;
            display: block;
            width: 200px;
            height: 10px;
            box-shadow: 4px 5px 31px 11px #999;
            content: '';
        }

        .error-page-actions:after {
            right: 50px;
            -webkit-transform: rotate(4deg);
            transform: rotate(4deg);
        }
    </style>
</head>
<body>

<div class="error-page-main">
    <h3>
        <strong>404</strong>无法打开页面
    </h3>
    <div class="error-page-actions">
        <div>
            <h4>可能原因：</h4>
            <ol>
                <li>网络信号差</li>
                <li>找不到请求的页面</li>
                <li>输入的网址不正确</li>
            </ol>
        </div>
        <div>
            <h4>可以尝试：</h4>
            <ul>
                <li><a href="${ctx}/account/index.do">返回首页</a> <a href="javascript:window.history.go(-1)">返回上一页</a></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>