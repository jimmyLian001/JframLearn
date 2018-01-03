$(function(){
    $('head').find('title').after('<link rel="shortcut icon" href="/resource/images/favicon-bf.ico"/>');
    var kf = '<div class="contact-us">\n' +
        '    <ul>\n' +
        '        <li class="customer-service">\n' +
        '            <div class="customer-pic same-service"></div>\n' +
        '            <div class="telphone-number">\n' +
        '                <a href="javascript:;" class="none">021-6881 9999</a>\n' +
        '            </div>\n' +
        '        </li>\n' +
        '        <li class="return-top">\n' +
        '            <div class="return-pic same-service"></div>\n' +
        '        </li>\n' +
        '    </ul>\n' +
        '</div>'
    $('body').append(kf);
    $('.return-top').click(function(){
       $('body').animate({scrollTop:0},300);
    });
});