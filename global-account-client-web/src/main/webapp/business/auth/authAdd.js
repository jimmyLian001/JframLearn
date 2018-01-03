    $(function(){
        /*初始省信息*/
        initProvince();

        $('.general-btn').click(function () {
            $('.general-display').show();
            $(".mul-btn").removeClass('current');
            $(".general-btn").addClass('current');
        });

        $('.mul-btn').click(function () {
            $('.general-display').hide();
            $(".general-btn").removeClass('current');
            $(".mul-btn").addClass('current');
        });

    });

    /*初始省信息*/
    function initProvince() {
        var selector = $("select[name='province']");
        $.ajax({
            url: ctx+"/auth/initProvince.do",
            type: "post",
            dataType: "text",
            success: function(data){
                var provinceList = eval(data);
                selector.empty();
                selector.append("<option value='' class=\"select-default\">请选择省</option>");
                for (var i = 0; i < provinceList.length; i++) {
                    selector.append(
                        "<option value='" + provinceList[i].provinceId + "_" + provinceList[i].provinceName + "'>"
                        + provinceList[i].provinceName
                        + "</option>");
                }
            }
        });
    }

    /*初始城市信息*/
    function initCity() {
        var selector = $("select[name='city']");
        var provinceId = $("select[name='province']").val();
        var params = [
            {name:'provinceId', value:provinceId}
        ];
        $.ajax({
            url: ctx+"/auth/initCity.do",
            type: "post",
            dataType: "text",
            data: params,
            success: function(data){
                var cityList = eval(data);
                selector.empty();
                selector.append("<option value='' class=\"select-default\">请选择市</option>");
                for (var i = 0; i < cityList.length; i++) {
                    selector.append("<option value='" + cityList[i].cityId + "_" + cityList[i].cityName +"'>" + cityList[i].cityName + "</option>");
                }
            }
        });
    }

    /*初始地区信息*/
    function initArea() {
        var selector = $("select[name='area']");
        var cityId = $("select[name='city']").val();
        var params = [
            {name:'cityId', value:cityId}
        ];
        $.ajax({
            url: ctx+"/auth/initArea.do",
            type: "post",
            dataType: "text",
            data: params,
            success: function(data){
                var areaList = eval(data);
                selector.empty();
                selector.append("<option value='' class=\"select-default\">请选择地区</option>");
                for (var i = 0; i < areaList.length; i++) {
                    selector.append("<option value='" + areaList[i].areaId + "_" + areaList[i].areaName +"'>" + areaList[i].areaName + "</option>");
                }
            }
        });
    }

    //上传图片后预览图片
    function viewImage(file, id){

        var preview = document.getElementById(id);
        $("#"+id).attr("src","");
        $("#"+id).attr("style","");
        if(file.files && file.files[0]){
            //火狐下
            preview.style.display = "block";
            preview.style.width = "120px";
            preview.style.height = "100px";
            preview.src = window.URL.createObjectURL(file.files[0]);
        }else{
            //ie下，使用滤镜
            file.select();
            var imgSrc = document.selection.createRange().text;
            var localImagId = document.getElementById("image_" + id);
            //必须设置初始大小
            localImagId.style.width = "120px";
            localImagId.style.height = "100px";
            try{
                localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
            }catch(e){
                errorMsgShow("您上传的图片格式不正确，请重新选择!");
                setTimeout(function () {
                    $('#topTipErrMsg').removeClass("top-tip show").addClass("top-tip hidden")
                }, 3000);
                return false;
            }
            preview.style.display = 'none';
            document.selection.empty();
        }
        return true;
    }

    /*企业证件类型设置*/
    function changeMultiLicenseType(flag) {
        var multiLicenseType;
        // 多证合一营业执照
        if (flag) {
            multiLicenseType = '1';
        } else {
            // 普通营业执照
            multiLicenseType = '2';
        }
        $("input[name='multiLicenseType']").val(multiLicenseType);
        return false;
    }

    function changePersonButtonState() {
        if ($('#agreeCheck').is(':checked')) {
            $("#person_reg_sub").attr("disabled", false);
        } else {
            $("#person_reg_sub").attr("disabled", true);
        }
    }

    function changeOrgButtonState() {
        if ($('#agreeCheck').is(':checked')) {
            $("#org_reg_sub").attr("disabled", false);
        } else {
            $("#org_reg_sub").attr("disabled", true);
        }
    }