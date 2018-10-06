var wxShare = function () {
    return {
        Encode64: function (value) {
            var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv" + "wxyz0123456789+/" + "=";
            value = escape(value);
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            do {
                chr1 = value.charCodeAt(i++);
                chr2 = value.charCodeAt(i++);
                chr3 = value.charCodeAt(i++);

                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;

                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }

                output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            } while (i < value.length);

            return output;
        },
        load: function(){
            function loadJS(a, b) {
                var c = new XMLHttpRequest;
                if (c.open("GET", b, !1), c.send(null), 4 == c.readyState) {
                    if (c.status >= 200 && c.status < 300 || 0 == c.status || 304 == c.status) {
                        var d = document.getElementsByTagName("HEAD").item(0), e = document.createElement("script");
                        e.language = "javascript", e.type = "text/javascript", e.id = a;
                        try {
                            e.appendChild(document.createTextNode(c.responseText))
                        } catch (f) {
                            e.text = c.responseText
                        }
                        return d.appendChild(e), !0
                    }
                    return !1
                }
                return !1
            }
            if(!document.getElementById('wxjscfg')){
                var url = location.host + location.pathname + location.search;
                console.log('location.protocol:'+location.protocol)
                var _http='http:',_cfg='cfg.js'
                if(location.protocol=='https:'){
                    _http = 'https:';
                    _cfg = 'httpscfg.js';
                }
                loadJS("wxjscfg", _http+"//www.hunliji.com/p/wedding/index.php/home/WeixinWall/jsticket/0/" + wxShare.Encode64(url) + "/"+_cfg);
            }
        },

        //初始化
        setWeiXinData: function (args) {
            var config = {
                appId: '',
                imgUrl : 'http://qnm.hunliji.com/o_1a8dlm230v0l146fes31311j4c7.png',
                link : location.href,
                desc : '国内最佳跨平台婚礼APP，婚礼筹备、电子请帖、婚礼故事等功能帮您打造完美婚礼，谱写婚礼故事。',
                title : '婚礼纪--打造完美婚礼！',
                timelineTitle: ''
            };
            Object.keys(args).forEach(function (key) {
                config[key]=args[key]
            })
            if (config['timelineTitle'].length == 0){
                config['timelineTitle'] = config['desc']
            }
            var wxDataHtml = [
                'var wxData = {',
                '"appId": "' + config.appId + '",',
                '"imgUrl": "' + config.imgUrl + '",',
                '"link": "' + config.link + '",',
                '"desc": "' + config.desc + '",',
                '"title": "' + config.title + '"',
                '};',
                '',
                'var wxDataer = {',
                '"appId": "' + config.appId + '",',
                '"imgUrl": "' + config.imgUrl + '",',
                '"link": "' + config.link + '",',
                '"desc": "' + config.desc + '",',
                '"title": "' + config.title + '"',
                '};',
                'function getShareData(){',
                'return JSON.stringify(wxData);',
                '}'
            ]
            //是否显示分享按钮
            if(location.href.indexOf("no_share") > -1){
                console.log('noshare');
                insertScript(wxDataHtml.slice(0,15).join("\n"));
            }else{
                insertScript(wxDataHtml.join("\n"));
                console.log('share');
            }
            function insertScript(script_text) {
                var script_tag = document.createElement('script');
                script_tag.type = "text/javascript";

                var script = document.createTextNode(script_text);
                script_tag.appendChild(script);
                document.body.appendChild(script_tag); 
            }
            wxShare.load();
            wx.ready(function () {
                SetWeixinShare(true, true);
            });

            var s_bWixinReady = false;
            var s_bInfoReady = false;

            function SetWeixinShare(wxready, invready) {
                if (wxready) {
                    s_bWixinReady = true;
                }
                if (invready) {
                    s_bInfoReady = true;
                }
                if (!s_bWixinReady || !s_bInfoReady) {
                    return;
                }
                var _img = config['imgUrl'];
                var _link = config['link'];
                var sSTitle = config['title'];
                var sDesc = config['desc'];
                wx.onMenuShareTimeline({
                    title: sSTitle,//config['timelineTitle'], 
                    link: _link, imgUrl: _img, success: function () {
                    }, cancel: function () {
                    }
                });
                wx.onMenuShareAppMessage({
                    title: sSTitle,
                    desc: sDesc,
                    link: _link,
                    imgUrl: _img,
                    type: "",
                    dataUrl: "",
                    success: function () {
                    },
                    cancel: function () {
                    }
                });
                wx.onMenuShareQQ({
                    title: sSTitle, desc: sDesc, link: _link, imgUrl: _img, success: function () {
                    }, cancel: function () {
                    }
                });
                wx.onMenuShareWeibo({
                    title: sSTitle, desc: sDesc, link: _link, imgUrl: _img, success: function () {
                    }, cancel: function () {
                    }
                });
            }
        },

        wxOnReady: function (call) {
            wxShare.load();
            wx.ready(function () {
                call();
            });
        },

        select: function (success, fail) {
            var success = arguments[0] ? arguments[0] : function(){};//设置默认参数
            var fail = arguments[1] ? arguments[1] : function(){};//设置默认参数
            wx.chooseImage({
                success: function (res) {
                    //alert("选择了" + res.localIds.length + "张图片，正在上传...");
                    wxShare.upload(res.localIds, success, fail);
                }
            });
        },
        upload: function (imgids, success, fail) {
            if (imgids.length == 0) {
                return;
            }
            wx.uploadImage({
                localId: imgids[0],
                success: function (res) {
                    //alert("上传成功" + res.serverId);
                    success(imgids[0], res.serverId);
                },
                fail: function (res) {
                    fw.common.dialog.error("上传失败，图像未能上传到微信服务器");
                    fail();
                }
            });
        }
    }
}();



