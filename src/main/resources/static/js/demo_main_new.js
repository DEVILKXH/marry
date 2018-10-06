function boot() {
  this.result = ''; //数据源
  this.card_id = ''; //请帖ID
  this.type = '';
  this.guests = {}; //宾客页
  this.guestState = true;
  this.allPage = []; //请帖单页
  this.width = window.innerWidth;
  this.height = window.innerHeight;
  this.UI_WIDTH = 750; //UI稿尺寸
  this.UI_HEIGHT = 1220; //UI稿尺寸
  this.seating = 0; //当前请帖页
  this.autoState = false; //是否自动播放
  this.musicStatePause = "false";
  this.log = []; //editIcon参数
  this.videoNext = false; //视频存在
  this.videoHave = false; //视频存在
  this.editState = false; //编辑图标状态
  this.hideEditState = false; // 隐藏编辑状态
  this.infiniteArr = []; //动态元素
  this.localhost = "/";
  this.API = {
    sdkData: 'js/batch.json',
    template: 'js/data.json',
  }
}
boot.prototype = function () {
  ajax_info = function () {
    var $this = this,
      _html = document.createElement('div'),
      _allPage = document.createElement('div'),
      _other = document.createElement('div');
    _html.setAttribute('id', 'wrap');
    _allPage.setAttribute('id', 'all-page');
    _other.setAttribute('id', 'other');
    _html.appendChild(_allPage);
    document.body.appendChild(_other);
    document.body.appendChild(_html);
    document.getElementById('all-page').style.height = $this.height + 'px'
    if ($this.getCookie('editState') && $this.type) {
      this.editState = $this.getCookie('editState')
    }
    this.card_id = this.getParams('card_id');
    this.type = this.getParams('type');
    console.log($this.localhost + $this.API.template);
    $.ajax({
      url: $this.localhost + $this.API.template,
      type: 'get',
      success: function (res) {
        if (res.status.RetCode == 0) {
          $this.result = res.data;
          $this.init()

          $this.musicOpen = $this.result.music.img || '//qnm.hunliji.com/o_1bi67lq091qtt1gfs60cpadqjj7.png';
          $this.musicClose = $this.result.music.close_img || '//qnm.hunliji.com/o_1bi67m2q63tilg81vh1q3v10g6c.png';
          $this.pageIcon = $this.result.page_icon || '//qnm.hunliji.com/o_1agpam0fsibn2814j110101jcr7.png';

          if ($this.getCookie('musicStatePause')) {
            $this.musicStatePause = 'false'
            if ($this.type) {
              $this.musicStatePause = "false"
              $this.writeCookie('musicStatePause', 'false', 360)
            }
          }
          if (res.data.music.audio) {
            if ($this.type) {
              $('body').on('touchstart', function () {
                if ($this.musicStatePause == 'false') {
                  document.getElementById('playMusic').play();
                }
              })
            }
            document.getElementById('playMusic').setAttribute('src', res.data.music.audio.replace('http:', ''))
            if ($this.musicStatePause == 'false') {
              document.getElementById('playMusic').play();
            }
            if (navigator.userAgent.indexOf('iPhone') > -1) {
              wx.config({
                debug: false,
                appId: '',
                timestamp: 1,
                nonceStr: '',
                signature: '',
                jsApiList: []
              });
              wx.ready(function () {
                if ($this.musicStatePause == 'false') {
                  document.getElementById('playMusic').play();
                }
              });
            }
            var _div = document.createElement('div'),
              _img = document.createElement('img'),
              _on = $this.musicOpen,
              _off = $this.musicClose;
            _img.setAttribute('src', ($this.musicStatePause != "true" ? _on : _off));
            _div.setAttribute('id', 'musicBtn');
            _div.appendChild(_img);
            _div.style.width = 84 / $this.UI_WIDTH * $this.width + 'px';
            _div.style.top = 20 / $this.UI_WIDTH * $this.width + 'px';
            _div.style.right = 20 / $this.UI_WIDTH * $this.width + 'px';
            document.getElementById('other').appendChild(_div);
            var _myvideo = document.getElementById('playMusic');
            _myvideo.addEventListener("timeupdate", function () {
              if ($this.musicStatePause == 'false') {
                $('#musicBtn').addClass('rotate')
              }
            });
          }
        }
      }
    })
  }
  init = function () {
    var $this = this,
      _share = {},
      clientY = 0,
      _timer = null;
    this.is_get_confirm = this.getCookie('is_get_confirm') == 1 || false;
    this.with_myb = this.getParams('with_myb')
    sessionStorage.backUrl = location.href
    if (this.result.background_path) {
      $('body').css({
        'background': 'url(' + this.result.background_path + ') 0 0 no-repeat',
        'background-size': '100% 100%'
      })
    }
    this.result.page.forEach(function (page, i) {
      if (page.layout.layTemplate) {
        $this.guests.no = i;
        $this.guests.html = page;
        $this.buttonBg = page.layout.attendButton.boxColor;
        $this.textColor = page.layout.attendButton.textColor;
      }
    })
    $this.result.guest_template == 0 ? $this.guestPageHide(true) : null
    var _imgs = [],
      _ele = $this.result.page[0].layout.elements;
    for (var i = 0; i < _ele.length; i++) {
      _imgs.push(_ele[i].img)
    }
    this.loading(_imgs, function () {
      $('.loadmore-loading').remove()
      $('#musicBtn').css({
        'margin-top': 0
      })
      $('#all-page').empty().append($this.createPage($this.result.page).slice(0, 1))
      setTimeout(function () {
        if ($('#all-page div.layout').length == 1) {
          $('#all-page').append($this.createPage($this.result.page).slice(1))
        }
      }, 300)
        $this.addStyleNode()
      if (document.getElementById('vid')) {
        document.getElementById('vid').muted = "muted";
      }
      $this.upDownIcon()
      if ($('#vid').length > 0) {
        document.getElementById('vid').addEventListener('loadedmetadata', function (e) {})
        document.getElementById('vid').addEventListener('timeupdate', function (e) {
          $('#video').parent().removeClass('vhave');
        })
        document.getElementById('vid').addEventListener('play', function (e) {})
      }
      $this.touchAction();
      if ($this.videoHave && navigator.userAgent.indexOf('Android') <= -1) {
        $('#video').parent().addClass('vhave');
      }
      setTimeout(function () {
        if ($this.getCookie('editState') && $this.type) {
          $this.editState = $this.getCookie('editState') == 'true' ? true : false
        }
        if ($this.type && $this.getParams('edit')) {
          if ($this.getParams('edit') == 1) {
            $this.editState = true
          } else {
            $this.editState = false
          }
        }
        // $this.editIconState($this.editState)
      }, 3600)
      $this.sdk()
      setTimeout(function () {
        if (sessionStorage.getItem('current_seating')) {
          $this.gotoPage(sessionStorage.getItem('current_seating'))
        }
      }, 300)
    })
    this.allImg(this.result)
    var preview = $this.getParams('preview')
    var weapp_preview = $this.getParams('weapp_preview')
    if (preview) {
      $('.new_box, .back_v2').show()
    } else {
      $('.new_box, .back_v2').hide()
    }
    if(weapp_preview){
      $this.createBtn()
    }
    var clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
    $(window).on('resize', function () {
      var nowClientHeight = document.documentElement.clientHeight || document.body.clientHeight;
      if (clientHeight > nowClientHeight) {
        if (navigator.userAgent.indexOf('Android') > -1) {
          console.log($('#gusetBox').height())
          $('#gusetBox').css({
            'bottom': clientHeight - nowClientHeight + 'px',
            'top': nowClientHeight - $('#gusetBox').height() + 'px'
          })
          $('#send_gift').css('top', '0px')
        }
      } else {
        if (navigator.userAgent.indexOf('Android') > -1) {
          $('#gusetBox').css({
            'bottom': '0',
            'top': 'auto'
          })

        }
        $('#send_gift').css('top', 'auto')
      }
    });
    $(document).on('touchstart', '#musicBtn', function () {
      var _this = $(this);
      if (_this.hasClass('rotate')) {
        $this.musicStatePause = 'true';
        $this.writeCookie('musicStatePause', 'true', 360)
        document.getElementById('playMusic').pause()
        _this.removeClass('rotate')
        $('#musicBtn img').attr('src', $this.musicClose)
      } else {
        $this.musicStatePause = 'false';
        $this.writeCookie('musicStatePause', 'false', 360)
        document.getElementById('playMusic').play()
        $('#musicBtn img').attr('src', $this.musicOpen)
      }
    })
    $(document).on('touchstart', '#video', function () {
      document.getElementById('vid').muted = "muted";
      document.getElementById('vid').play()
      $('.videoMH').removeClass('nosee')
    })
    $(document).on('touchstart', '.noVideo', function () {
      location.reload()
    })
    $(document).on('touchstart', '.navigation', function () {
      if ($this.type) {
        return false
      }
      location.href = $this.mapUrl
    })
    $(document).on('touchstart', '.dwIcon', function () {
      if ($this.type) {
        return false
      }
      location.href = $this.mapUrl
    })
    this.get_infinite()
  }
  get_infinite = function () {
    this.infiniteArr.length > 0 ? this.infiniteArr = [] : null
    for (var i = 0; i < this.result.page.length; i++) {
      for (var r = 0; r < this.result.page[i].layout.elements.length; r++) {
        if (this.result.page[i].layout.elements[r].infinite) {
          var obj = {},
            _eles = this.result.page[i].layout.elements;
            obj.page_id = 'box_ele_'+i;
            obj.id = _eles[r].id|| Math.round(Math.random()*10000000000000000)
            obj.infinite = _eles[r].infinite;
            obj.inf_duration = _eles[r].inf_duration || '1000ms';
            obj.inf_delay = _eles[r].inf_delay || '0ms';
            obj.delay = _eles[r].delay || '0ms';
            obj.duration = _eles[r].duration || '1000ms';
            obj.animate = _eles[r].animate;
            this.infiniteArr.push(obj)
        }
      }
    }
  }
  loadAnimate = function (func) {
    var obj = {},
      n = 1,
      rad = Math.PI * 2 / 100;
    obj.cx = 200;
    obj.cy = 200;
    obj.cr = 60;
    obj.speed = 1;
    var _divCanvas = document.createElement('div'),
      _canvas = document.createElement('canvas'),
      context = _canvas.getContext("2d");
    _divCanvas.setAttribute('id', 'canvasBox');
    _divCanvas.style.width = 100 / this.UI_WIDTH * this.width + 'px'
    _divCanvas.style.height = 100 / this.UI_WIDTH * this.width + 'px'
    _canvas.style.position = 'absolute';
    _canvas.style.margin = 'auto';
    _canvas.style.top = '0';
    _canvas.style.right = '0';
    _canvas.style.bottom = '0';
    _canvas.style.left = '0';
    _canvas.style.zIndex = '9';
    _canvas.setAttribute('width', obj.cx);
    _canvas.setAttribute('height', obj.cy);
    _canvas.setAttribute('id', 'canvas');
    document.body.appendChild(_divCanvas)
    document.getElementById('canvasBox').appendChild(_canvas);

    function writeArc(n) {
      context.save();
      context.strokeStyle = "#fca7a7";
      context.lineWidth = 16;
      context.beginPath();
      context.arc(obj.cx / 2, obj.cy / 2, obj.cr, -Math.PI / 2, -Math.PI / 2 + rad * n + .5, false);
      context.stroke();
      context.restore();
    }

    function DreamLoading() {
      context.clearRect(0, 0, canvas.width, canvas.height)
      writeArc(n)
      if (n < 100) {
        n = n + .2;
      } else {
        if (func) {
          $('#canvas').remove()
          func();
          return false;
        }
      }
      setTimeout(DreamLoading, obj.speed);
    }
    DreamLoading();
  }
  allImg = function (result) {
    var _imgs = ['//qnm.hunliji.com/o_1bid6p3ojgs8uptnu919pd5os7.png', '//qnm.hunliji.com/o_1bke091mk1p7jimp192lvqrkl87.png'];
    for (var i = 0; i < result.page.length; i++) {
      for (var r = 0; r < result.page[i].layout.elements.length; r++) {
        var _img;
        if (result.page[i].layout.elements[r].img) {
          _img = result.page[i].layout.elements[r].img;
          _imgs.push(_img)
        }
      }
    }
    this.loading(_imgs, function () {
      console.log('ok')
    })
  }
  loading = function (arr, func) {
    var _count = 0;
    for (var i = 0; i < arr.length; i++) {
      if (!arr[i]) {
        arr.splice(i, 1);
      }
      var _img = new Image();
      _img.onload = function () {
        _count++;
        if (_count >= arr.length) {
          if (func) {
            func()
          }
        }
      }
      _img.src = arr[i];
    }
  }
  rov_infinite = function (deltaY) {
    this.timeAni ? clearTimeout(this.timeAni) : null;
    var $this = this,
      _infArr = $this.infiniteArr,
      _infArrW = _infArr.length,
      _seating = $this.seating;
    deltaY > 0 ? _seating++ : _seating--;
    for (var i = 0; i < _infArrW; i++) {
      if (_infArr[i].page_id == this.result.page[this.seating].id && $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).hasClass('infinite')) {
        $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).removeClass(_infArr[i].infinite)
        $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).removeClass('infinite')
        $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).css({
          '-webkit-animation-delay': _infArr[i].delay
        })
        $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).css({
          '-webkit-animation-duration': _infArr[i].duration
        })
        $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).addClass(_infArr[i].animate)
      }
      if (i >= _infArrW - 1) {
        $this.timeAni = setTimeout(function () {
          $this.add_infinite()
        }, 1600)
      }
    }
  }
  add_infinite = function () {
    var $this = this,
      _infArr = this.infiniteArr,
      _infArrW = _infArr.length;
    var _anis = $('.animated');
    $this.timeAni ? clearTimeout($this.timeAni) : null;
    for (var i = 0; i < _infArrW; i++) {
      if ($this.result.page[$this.seating].id == _infArr[i].page_id && _infArr[i].page_id != undefined) {
        $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).removeClass(_infArr[i].animate);
        $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).addClass(_infArr[i].infinite);
        $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).addClass('infinite');
        $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).css({
          '-webkit-animation-duration': _infArr[i].inf_duration
        })
        $('.ani_' + $this.result.page[$this.seating].id + '_' + _infArr[i].id).css({
          '-webkit-animation-delay': _infArr[i].inf_delay
        })
      }
    }
  }
  addStyle = function (obj, animateName) {
    var _style = document.createElement('style'),
      _infinite = "",
      _timing = "linear",
      _aniCode = '';
    switch (obj.infinite) {
      case 'animate_leftTop':
        _infinite = 'leftTop_rotate'
        _timing = "ease-in-out"
        break;
      case 'animate_leftBottom':
        _infinite = 'leftBottom_rotate'
        _timing = "ease-in-out"
        break;
      case 'animate_rightTop':
        _infinite = 'rightTop_rotate'
        _timing = "ease-in-out"
        break;
      case 'animate_rightBottom':
        _infinite = 'rightBottom_rotate'
        _timing = "ease-in-out"
        break;
      case 'rotate':
        _infinite = 'rotate'
        break;
      case 'bounce':
        _infinite = 'bounce'
        break;
      case 'flash':
        _infinite = 'flash'
        break;
      case 'pulse':
        _infinite = 'pulse'
        break;
      case 'rubberBand':
        _infinite = 'rubberBand'
        break;
      case 'shake':
        _infinite = 'shake'
        break;
      case 'headShake':
        _infinite = 'headShake'
        break;
      case 'swing':
        _infinite = 'swing'
        break;
      case 'tada':
        _infinite = 'tada'
        break;
      case 'wobble':
        _infinite = 'wobble'
        break;
      case 'jello':
        _infinite = 'jello'
        break;
      case 'bounceIn':
        _infinite = 'bounceIn'
        break;
      case 'bounceInDown':
        _infinite = 'bounceInDown'
        break;
      case 'bounceInLeft':
        _infinite = 'bounceInLeft'
        break;
      case 'bounceInRight':
        _infinite = 'bounceInRight'
        break;
      case 'bounceInUp':
        _infinite = 'bounceInUp'
        break;
      case 'bounceOut':
        _infinite = 'bounceOut'
        break;
      case 'bounceOutDown':
        _infinite = 'bounceOutDown'
        break;
      case 'bounceOutLeft':
        _infinite = 'bounceOutLeft'
        break;
      case 'bounceOutRight':
        _infinite = 'bounceOutRight'
        break;
      case 'bounceOutUp':
        _infinite = 'bounceOutUp'
        break;
      case 'fadeIn':
        _infinite = 'fadeIn'
        break;
      case 'fadeInDown':
        _infinite = 'fadeInDown'
        break;
      case 'fadeInDownBig':
        _infinite = 'fadeInDownBig'
        break;
      case 'fadeInLeft':
        _infinite = 'fadeInLeft'
        break;
      case 'fadeInLeftBig':
        _infinite = 'fadeInLeftBig'
        break;
      case 'fadeInRighting':
        _infinite = 'fadeInRight'
        break;
      case 'fadeInRightBig':
        _infinite = 'fadeInRightBig'
        break;
      case 'fadeInUp':
        _infinite = 'fadeInUp'
        break;
      case 'fadeInUpBig':
        _infinite = 'fadeInUpBig'
        break;
      case 'fadeOut':
        _infinite = 'fadeOut'
        break;
      case 'fadeOutDown':
        _infinite = 'fadeOutDown'
        break;
      case 'fadeOutDownBig':
        _infinite = 'fadeOutDownBig'
        break;
      case 'fadeOutLeft':
        _infinite = 'fadeOutLeft'
        break;
      case 'fadeOutLeftBig':
        _infinite = 'fadeOutLeftBig'
        break;
      case 'fadeOutRight':
        _infinite = 'fadeOutRight'
        break;
      case 'fadeOutRightBig':
        _infinite = 'fadeOutRightBig'
        break;
      case 'fadeOutUp':
        _infinite = 'fadeOutUp'
        break;
      case 'fadeOutUpBig':
        _infinite = 'fadeOutUpBig'
        break;
      case 'flipInX':
        _infinite = 'flipInX'
        break;
      case 'flipInY':
        _infinite = 'flipInY'
        break;
      case 'flipOutX':
        _infinite = 'flipOutX'
        break;
      case 'flipOutY':
        _infinite = 'flipOutY'
        break;
      case 'lightSpeedIn':
        _infinite = 'lightSpeedIn'
        break;
      case 'lightSpeedOut':
        _infinite = 'lightSpeedOut'
        break;
      case 'rotateIn':
        _infinite = 'rotateIn'
        break;
      case 'rotateInDownLeft':
        _infinite = 'rotateInDownLeft'
        break;
      case 'rotateInDownRight':
        _infinite = 'rotateInDownRight'
        break;
      case 'rotateInUpLeft':
        _infinite = 'rotateInUpLeft'
        break;
      case 'rotateInUpRight':
        _infinite = 'rotateInUpRight'
        break;
      case 'hinge':
        _infinite = 'hinge'
        break;
      case 'jackInTheBox':
        _infinite = 'jackInTheBox'
        break;
      case 'rollIn':
        _infinite = 'rollIn'
        break;
      case 'rollOut':
        _infinite = 'rollOut'
        break;
      case 'zoomIn':
        _infinite = 'zoomIn'
        break;
      case 'zoomInDown':
        _infinite = 'zoomInDown'
        break;
      case 'zoomInLeft':
        _infinite = 'zoomInLeft'
        break;
      case 'zoomInRight':
        _infinite = 'zoomInRight'
        break;
      case 'zoomInUp':
        _infinite = 'zoomInUp'
        break;
      case 'zoomOut':
        _infinite = 'zoomOut'
        break;
      case 'zoomOutDown':
        _infinite = 'zoomOutDown'
        break;
      case 'zoomOutLeft':
        _infinite = 'zoomOutLeft'
        break;
      case 'zoomOutRight':
        _infinite = 'zoomOutRight'
        break;
      case 'zoomOutUp':
        _infinite = 'zoomOutUp'
        break;
      case 'slideInDown':
        _infinite = 'slideInDown'
        break;
      case 'slideInLeft':
        _infinite = 'slideInLeft'
        break;
      case 'slideInRight':
        _infinite = 'slideInRight'
        break;
      case 'slideInUp':
        _infinite = 'slideInUp'
        break;
      case 'slideOutDown':
        _infinite = 'slideOutDown'
        break;
      case 'slideOutLeft':
        _infinite = 'slideOutLeft'
        break;
      case 'slideOutRight':
        _infinite = 'slideOutRight'
        break;
      case 'slideOutUp':
        _infinite = 'slideOutUp'
        break;
      case 'fadeInNormal':
        _infinite = 'fadeInNormal'
        obj.animationDuration[animateName.indexOf('fadeInNormal')] = "1000"
        break;
      case 'pullDown':
        _infinite = 'pullDown'
        obj.animationDuration[animateName.indexOf('pullDown')] = "1000"
        break;
    }
    if (!obj.animationDuration || obj.animationDuration.indexOf('0') > -1 || obj.animationDuration.indexOf('0ms') > -1 || obj.animationDuration.indexOf('ms') > -1 || obj.animationDuration.indexOf('') > -1) {
      if (animateName.indexOf('fadeInNormal') > -1) {
        obj.animationDuration[animateName.indexOf('fadeInNormal')] = '1000ms'
      } else if (animateName.indexOf('slideLeft') > -1) {
        obj.animationDuration[animateName.indexOf('slideLeft')] = '1000ms'
      } else if (animateName.indexOf('bounceInRight') > -1) {
        obj.animationDuration[animateName.indexOf('bounceInRight')] = '1000ms'
      } else if (animateName.indexOf('stretchLeft') > -1) {
        obj.animationDuration[animateName.indexOf('stretchLeft')] = '1000ms'
      } else if (animateName.indexOf('fadeIn') > -1) {
        obj.animationDuration[animateName.indexOf('fadeIn')] = '1000ms'
      } else if (animateName.indexOf('bounceInLeft') > -1) {
        obj.animationDuration[animateName.indexOf('bounceInLeft')] = '1000ms'
      } else if (animateName.indexOf('stretchRight') > -1) {
        obj.animationDuration[animateName.indexOf('stretchRight')] = '1000ms'
      }
    }
    if (obj.animationDuration.indexOf('0') > -1) {
      obj.animationDuration[obj.animationDuration.indexOf('0')] = '1000ms'
    } else if (obj.animationDuration.indexOf('0ms') > -1) {
      obj.animationDuration[obj.animationDuration.indexOf('0ms')] = '1000ms'
    } else if (obj.animationDuration.indexOf('ms') > -1) {
      obj.animationDuration[obj.animationDuration.indexOf('ms')] = '1000ms'
    } else if (obj.animationDuration.indexOf('') > -1) {
      obj.animationDuration[obj.animationDuration.indexOf('')] = '1000ms'
    }
    if (_infinite != '') {
      _infinite = ',' + _infinite + ' ' + obj.inf_duration + ' ' + _timing + ' ' + obj.inf_delay + ' forwards infinite'
    }
    // 组合动画处理
    var _animate = []
    if (Array.isArray(animateName)) {
      for (var i = 0; i < animateName.length; i++) {
        if (obj.animationDelay[i] == 'ms') {
          obj.animationDelay[i] = '0ms'
        } else if (obj.animationDelay[i] && obj.animationDelay[i].indexOf('ms') <= '-1') {
          obj.animationDelay[i] = obj.animationDelay[i] + 'ms'
        }
        if (obj.animationDuration[i] == 'ms') {
          obj.animationDuration[i] = '0ms'
        } else if (obj.animationDuration[i] && obj.animationDuration[i].indexOf('ms') <= '-1') {
          obj.animationDuration[i] = obj.animationDuration[i] + 'ms'
        }
        if (i == 0) {
          _animate.push(animateName[i] + ' ' + obj.animationDuration[i] + ' ease-in-out ' + obj.animationDelay[i] + ' both 1' + _infinite)
        } else {
          _animate.push(animateName[i] + ' ' + obj.animationDuration[i] + ' ease-in-out ' + obj.animationDelay[i] + ' forwards 1' + _infinite)
        }
      }
      _style.innerHTML = '.ani_' + obj.page_id + '_' + obj.id + ' {-webkit-animation:' + _animate.join(',') + '; }'
    } else {
      _style.innerHTML = '.ani_' + obj.page_id + '_' + obj.id + ' {-webkit-animation:' + animateName + ' ' + obj.animationDuration + ' ease-in-out ' + obj.animationDelay + ' both 1' + _infinite + '; }'
    }
    document.getElementById('other').appendChild(_style);
  }
  createPage = function (arr) {
    var $this = this,
      UI_WIDTH = $this.UI_WIDTH,
      UI_HEIGHT = $this.UI_HEIGHT,
      width = $this.width,
      height = $this.height;
    return this.allPage = arr.map(function (v, i) {
      var _ele = '';
      v.layout.layTemplate ? $this.guestsPage(v, i) : null
      v.layout.elements.forEach(function (a, b) {
        a.video_path ? $this.videoNext = i - 1 : null;
        var _y = Number(a.y / UI_WIDTH) * width,
          _maskAttr = '';
        if (a.is_scale == 1) {
          _y = Number(a.y / UI_HEIGHT) * height;
        }
        if (a.mask) {
          _maskAttr = '-webkit-mask-image:url(' + a.mask.img + ');-webkit-mask-size:100% 100%;';
        }
        var obj = {
          width: Number(a.width / UI_WIDTH) * width + 'px',
          height: Number(a.height / UI_WIDTH) * width + 'px',
          left: Number(a.x / UI_WIDTH) * width + 'px',
          top: _y + 'px',
          zIndex: a.z_order,
          animationDelay: a.delay || 0,
          animationDuration: a.duration || 0,
          type: a.type || null,
          isdown: a.is_down == 0 ? false : true,
          id: a.id || Math.round(Math.random()*10000000000000000),
          page_id:'demo_'+i,
          text_type: a.text_type || null,
          infinite: a.infinite || null,
          mask: _maskAttr,
          inf_delay: a.inf_delay || 0,
          inf_duration: a.inf_duration || 0,
          edit_btn_position: a.edit_btn_position || null
        };
        // 判断是否是组合动画
        obj.animationDelay = obj.animationDelay
        obj.animationDuration = obj.animationDuration
        if (obj.inf_delay == 'ms') {
          obj.inf_delay = '0ms'
        } else if (obj.inf_delay != '' && obj.inf_delay.indexOf('ms') <= '-1') {
          obj.inf_delay = obj.inf_delay + 'ms'
        }
        if (obj.inf_duration == 'ms') {
          obj.inf_duration = '0ms'
        } else if (obj.inf_duration != '' && obj.inf_duration.indexOf('ms') <= '-1') {
          obj.inf_duration = obj.inf_duration + 'ms'
        }

        var pos = 'fixed'
        if ($this.getParams('weapp_preview')) {
          pos = 'absolute'
        }
        var bottom = $this.UI_HEIGHT - a.y - a.height;
        bottom = Number(bottom / $this.UI_WIDTH) * width + 'px';
        var isDown = obj.isdown ? 'bottom:' + bottom : 'top:' + obj.top,
          style = 'position:'+pos+';width:' + obj.width + ';height:' + obj.height + ';left:' + obj.left + ';' + isDown + ';z-index:' + obj.zIndex;
        // $this.positionIcon(obj, isDown, a, b, v.layout.elements.length)
        var bgImg = '',
          rol = '';
        if (a.height >= $this.UI_HEIGHT) {
          if (Number(a.height / $this.UI_WIDTH) * width > height) {
            height = Number(a.height / $this.UI_WIDTH) * width
          }
          rol = Math.round(a.width / a.height * $this.height) - $this.width;
          rol = rol / 2;
          bgImg = '-webkit-transform:translateX(-' + rol + 'px);width:auto;height:' + height + 'px';
        }
        var animateName = a.animate;
        var infFun = '';
        if (obj.infinite) {
          infFun = "inf=" + obj.infinite + ""
        }
        if ($this.type) {
          if (a.original_path && a.original_path !== '') {
            var _videoPath = a.video_path ? a.video_path : a.original_path
            $this.videoHave = true;
            _ele += '<div class="videoMH nosee"></div><div id="video" class="animated ' + animateName + ' ani_' + obj.page_id + '_' + obj.id + '" videoW="' + a.video_width + '" videoP="' + a.video_path + '" videoH="' + a.video_height + '" style="width:' + ($this.width) + 'px;height:' + $this.height + 'px;position:relative;overflow:hidden;">\
                  <video id="vid" class="IIV"\
                  x-webkit-airplay="true" \
                  webkit-playsinline\
                  playsinline\
                  preload="true"\
                  height="100%"\
                  loop="true"\
                   x5-video-player-type="h5"\
                   x5-video-player-fullscreen="true"\
                   style="min-height:100%;min-width:100%;margin-left:-' + (a.video_width / a.video_height * $this.height - $this.width) / 2 + 'px;"\
                   poster="' + a.original_path + '?vframe/jpg/offset/1|imageView2/1/w/' + a.video_width + '/h/' + a.video_height + '"\
                   src="' + a.original_path + '"></video>\
                </div>'
          } else {
            var _height = '',
              _width = '',
              _marginLeft = '';
            if (a.width >= 750 && a.height >= 1220) {
              _height = $this.height + 'px';
              _width = 'auto';
              _marginLeft = (($this.UI_WIDTH / $this.UI_HEIGHT * $this.height) - $this.width) / 2;
              if (_marginLeft <= 0) {
                _width = '100%'
              }
            }
            if (obj.type == 'map') {
              _height = "100%";
            }
            if (!a.img) {
              a.img = ""
            }
            $this.addStyle(obj, animateName)
            _ele += '<div ' + infFun + ' class="animated ani_' + obj.page_id + '_' + obj.id + '" style="' + style + '">\
                      ' + (obj.type == 'map' ? '<img class="dwIcon" src="http://qnm.hunliji.com/o_1blaaggv063m34kok21s8k1irnc.png"><div class="navigation" style="position: absolute; width: ' + 100 / $this.UI_WIDTH * $this.width + 'px; height: ' + 56 / $this.UI_WIDTH * $this.width + 'px; right: 0; top:0; background: #7c7c7c; opacity: 0.6; color: white;font-size: ' + 28 / $this.UI_WIDTH * $this.width + 'px;line-height: ' + 56 / $this.UI_WIDTH * $this.width + 'px; text-align: center; border-bottom-left-radius: ' + 5 / $this.UI_WIDTH * $this.width + 'px;">导航</div>' : '') + '\
                      <img style="' + obj.mask + ';width:' + _width + ';height:' + _height + ';margin-left:-' + _marginLeft + 'px" class="pageImg ' + (obj.type == 'map' ? 'mapSeat' : '') + '" type="' + obj.type + '" page_id="' + obj.page_id + '" id="' + obj.id + '" style="' + bgImg + '" src="' + a.img + '" />\
                  </div>'
          }
        } else {
          if (a.video_path && a.video_path != '') {
            $this.videoHave = true;
            _ele += '<div class="videoMH nosee"></div><div id="video" class="animated ' + animateName + ' ani_' + obj.page_id + '_' + obj.id + '" videoW="' + a.video_width + '" videoP="' + a.video_path + '" videoH="' + a.video_height + '" style="width:' + ($this.width) + 'px;height:' + $this.height + 'px;position:relative;overflow:hidden;">\
                  <video id="vid" class="IIV"\
                  x-webkit-airplay="true" \
                  webkit-playsinline\
                  playsinline\
                  preload="true"\
                  loop="true"\
                   x5-video-player-type="h5"\
                   x5-video-player-fullscreen="true"\
                   style="min-height:100%;min-width:100%;margin-left:-' + (a.video_width / a.video_height * $this.height - $this.width) / 2 + 'px;"\
                   poster="' + a.video_path + '?vframe/jpg/offset/1|imageView2/1/w/' + a.video_width + '/h/' + a.video_height + '"\
                   src="' + a.video_path + '"></video>\
                </div>'
          } else {
            if (a.video_path == '' && a.video_path != undefined || a.video_path === null) {
              _ele += '<div style="position:fixed;top:0;bottom:0;left:0;right:0;background:#f2f3f6;z-index:9">\
                          <img class="noVideo" style="position:fixed;top:0;bottom:0;left:0;right:0; width:35%;margin:auto;" src="//qnm.hunliji.com/o_1bk442tda19uemst1jia1db31d007.png" />\
                      </div>'
            } else {
              var _height = '',
                _width = '',
                _marginLeft = '';
              if (a.height >= 1220) {
                _height = $this.height + 'px';
                _width = 'auto';
                _marginLeft = (($this.UI_WIDTH / $this.UI_HEIGHT * $this.height) - $this.width) / 2;
                if (_marginLeft <= 0) {
                  _width = '100%'
                }
              }
              if (obj.type == 'map') {
                _height = "100%";
              }
              if (!a.img) {
                a.img = ""
              }
              $this.addStyle(obj, animateName)
              _ele += '<div ' + infFun + ' class="animated ani_' + obj.page_id + '_' + obj.id + '" style="' + style + '">\
                      ' + (obj.type == 'map' ? '<img class="dwIcon" src="http://qnm.hunliji.com/o_1blaaggv063m34kok21s8k1irnc.png"><div class="navigation" style="position: absolute; width: ' + 100 / $this.UI_WIDTH * $this.width + 'px; height: ' + 56 / $this.UI_WIDTH * $this.width + 'px; right: 0; top:0; background: #7c7c7c; opacity: 0.6; color: white;font-size: ' + 28 / $this.UI_WIDTH * $this.width + 'px;line-height: ' + 56 / $this.UI_WIDTH * $this.width + 'px; text-align: center; border-bottom-left-radius: ' + 5 / $this.UI_WIDTH * $this.width + 'px;">导航</div>' : '') + '\
                      <img style="' + obj.mask + ';width:' + _width + ';height:' + _height + ';margin-left:-' + _marginLeft + 'px" class="pageImg ' + (obj.type == 'map' ? 'mapSeat' : '') + '" type="' + obj.type + '" page_id="' + obj.page_id + '" id="' + obj.id + '" style="' + bgImg + '" src="' + a.img + '" />\
                  </div>'
            }
          }
        }
      })
      var _height = $this.height,
        _opacity = 0;
      if (i <= $this.seating) {
        _height = 0;
        _opacity = 1;
      }
      var _map = '',
        _yy = '';
      if (v.layout.layTemplate) {
        _map = "guestPage";
      }
      if ($this.type) {
        return '<div class="ebg ele_background_' + i + '" style="position:fixed;top:0;left:0;z-index:0;-webkit-transition-duration:600ms;opacity:' + _opacity + '"><img style="width:' + $this.width + 'px;height:' + $this.height + 'px;" src="' + v.layout.background + '"/></div><div id="' + _map + '" page_id="' + v.id + '" style="height:' + $this.height + 'px" class="layout ' + ($this.seating == i ? " " : "hide ") + ' ' + _map + '">' + _yy + _ele + '</div>'
      } else {
        return '<div class="ebg ele_background_' + i + '" style="position:fixed;top:0;left:0;z-index:0;-webkit-transition-duration:600ms;-webkit-transform:translateY(' + _height + 'px);"><img style="width:' + $this.width + 'px;height:' + $this.height + 'px;" src="' + v.layout.background + '"/></div><div id="' + _map + '" page_id="' + v.id + '" style="height:' + $this.height + 'px" class="layout ' + ($this.seating == i ? " " : "hide ") + ' ' + _map + '">' + _yy + _ele + '</div>'
      }
    })
  }
  guestsPage = function (v, i) {
    var $this = this;
    this.mapHave = true;
    this.mapNum = i;
    var $this = this,
      obj = {};
    obj.w = 686;
    obj.h = 386;
    obj.b = 132;
    obj.x = 25.493450;
    obj.y = 118.234510;
    var _div = document.createElement('div');
    _div.setAttribute('id', 'map');
    _div.setAttribute('class', 'animated fadeIn');
    _div.style.bottom = obj.b / this.UI_WIDTH * this.width + 'px';
    _div.style.width = obj.w / this.UI_WIDTH * this.width + 'px';
    var _mapSrc = '/images/map.png';
    $this.srcMap = _mapSrc;
    var _addr = '福建省泉州市德化县龙鹏街184号';
    $this.mapUrl = '//apis.map.qq.com/tools/poimarker?type=0&marker=coord:' + obj.x + ',' + obj.y + ';coordtype:3;title:戴云大酒店;addr:' + _addr + '&key=GMZBZ-ZCD3U-GY3VD-4PJK3-BRTK3-SQFWZ&referer=hunliji'
    var _animateIcon = document.createElement('div');
    _animateIcon.setAttribute('id', 'updownIcon');
    _animateIcon.style.bottom = (160 / this.UI_WIDTH * this.width + 34) + 'px';
    _animateIcon.innerHTML = '<img src="' + this.pageIcon + '"/>'
    var _guest_div = document.createElement('div');
    _guest_div.setAttribute('id', 'guest_action');
    _guest_div.setAttribute('class', 'fadeIn')
    _guest_div.style.bottom = 48 / $this.UI_WIDTH * $this.width + 'px';
    _guest_div.innerHTML = '<p id="guestBtn" style="margin: 0 auto;width:' + 500 / $this.UI_WIDTH * $this.width + 'px;height:' + 88 / $this.UI_WIDTH * $this.width + 'px;line-height:' + 88 / $this.UI_WIDTH * $this.width + 'px;border-radius: ' + 44 / $this.UI_WIDTH * $this.width + 'px;">宾客回复</p>';
    setTimeout(function () {
      if ($('#guestPage').find('#guestBtn').length >= 1) {
        console.log($('#guestPage').find('#guestBtn').length)
      } else {
        document.getElementById('guestPage') && document.getElementById('guestPage').appendChild(_guest_div)
        $this.isIphoneX() && $('#guest_action').css({
          'bottom': '58px'
        })
        if (!$this.type) {
          document.getElementById('guestPage') && document.getElementById('guestPage').appendChild(_animateIcon)
        }
        $('#guestBtn').css({
          'background': $this.buttonBg,
          'color': $this.textColor,
          'box-shadow': '1px 1px 3px rgba(0,0,0,.35)'
        })
        $('.mapSeat').attr('src', $this.srcMap)
        $('#guestPage #updownIcon').hide()
        $this.guestAction()
      }
      if ($this.type) {
          $('#guest_action').css({
            'bottom': $this.isIphoneX()?'108px':'98px'
          }).hide()
      }
    }, 300)
  }
    otherMap = function (srcMap) {
        var $this = this
        $.ajax({
            url: $this.API.otherMapImage + '?id=' + $this.card_id,
            type: 'get',
            success: function (res) {
                if (res.data.map_image) {

                    if ($this.result.cardInfo.map_image) {
                        $this.mapSrc = $this.result.cardInfo.map_image
                    } else {
                        if (res.data.map_image) {
                            $this.mapSrc = res.data.map_image
                        } else {
                            $this.mapSrc = srcMap
                        }
                    }
                } else {
                    $this.mapSrc = $this.result.cardInfo.map_image ? $this.result.cardInfo.map_image : srcMap
                }
                $('.mapSeat').attr('src', $this.mapSrc)
            }
        })
    }
  guestAction = function () {
    var _div = document.createElement('div'),
      $this = this,
      _num = 1,
      _bg = document.createElement('div'),
      _divMain = document.createElement('div'),
      _timer = null,
      bfscrolltop = document.body.scrollTop; //获取软键盘唤起前浏览器滚动部分的高度
    _divMain.style.display = 'none'
    _divMain.style.zIndex = '999'
    _divMain.style.position = 'relative'
    _divMain.style.height = this.height + 'px';
    _bg.style.height = this.height + 'px'
    _bg.setAttribute('id', 'guestBg')
    _div.setAttribute('id', 'gusetBox');
    var _p30 = 30 / $this.UI_WIDTH * $this.width,
      _p140 = 140 / $this.UI_WIDTH * $this.width,
      _p64 = 64 / $this.UI_WIDTH * $this.width;
    var _code = '<div id="gusetCode" style="padding:0 ' + _p30 + 'px 0 ' + _p30 + 'px; ">\
    			<div class="guest-reply"><i style="font-weight:700">您的姓名</i><input id="gusetName" name="gusetName" class="guset_name" style="width:70%;margin-left:' + _p30 + 'px;padding:' + _p30 + 'px 0 ' + _p30 + 'px 0;" type="text" placeholder="请输入您的姓名..."/></div>\
                                <div class="guest-reply"><i style="font-weight:700">您的祝福</i><input id="congratulation" name="congratulation" class="guset_name" style="width:70%;margin-left:' + _p30 + 'px;padding:' + _p30 + 'px 0 ' + _p30 + 'px 0;" type="text" placeholder="请输入您的祝福..."/></div>\
                                <div class="guset_set" style="padding:' + _p30 + 'px 0 ' + _p30 + 'px 0;">\
                                  <div style="font-weight:700">是否赴宴</div><div class="guset_icon" style="-webkit-box-flex:18"><p state=0><span class="sk">赴宴</span></p><p state=1><span>待定</span></p><p state=2><span>有事</span></p></div></div></div>';
    _div.innerHTML = _code + "<div class='send_box'>\
                <div class='guest_num' style='height:" + Number(100 / this.UI_WIDTH * this.width) + "px;line-height:" + Number(100 / this.UI_WIDTH * this.width) + "px'><div class='numpre'>赴宴人数</div><div><p class='disNum_col' style='width:" + Number(192 / this.UI_WIDTH * this.width) + "px;line-height:" + Number(64 / this.UI_WIDTH * this.width) + "px;height:" + Number(64 / this.UI_WIDTH * this.width) + "px'><i class='gnl addNum'>-</i><i class='disNum'>1</i><i class='gnr addNum ak'>+</i></p></div></div><div id='sendNum' style='line-height:" + Number(100 / this.UI_WIDTH * this.width) + "px'>确定</div>\
              </div>";
    _divMain.appendChild(_div)
    _divMain.appendChild(_bg)
    document.getElementById('guestPage') && document.getElementById('guestPage').appendChild(_divMain);
    $this.isIphoneX() && $('#gusetBox').css({
      'padding-bottom': '34px',
      'background': '#fff'
    })
    console.log($('#gusetBox'))
    $('#gusetBox').css({
      'bottom': '0'
    })
    $(document).on('touchstart', '.guset_icon p', function () {
      var index = $('.guset_icon p').index(this);
      $this.send_state = $(this).attr('state');
      $('.guset_icon p span').removeClass('sk')
      $(this).find('span').addClass('sk')
      if (index == 1 || index == 2) {
        $('.guest_num div').css({
          'opacity': '0'
        })
        $('.guest_num .numpre').css({
          'opacity': '0'
        })
      } else {
        $('.guest_num div').css({
          'opacity': '1'
        })
        $('.guest_num .numpre').css({
          'opacity': '1'
        })
      }
    })
    $(document).on('touchstart', '#guestBg', function () {
      var _this = $(this);
      $('#gusetBox').css({
        'bottom': '-200px'
      })
      _this.hide()
      $('#gusetName').blur()
      document.body.scrollTop = bfscrolltop
      clearInterval(_timer)
      setTimeout(function () {
        _divMain.style.display = 'none'
        $('#guestPage').find('.animated').show()
        $('#map').show()
        $('#updownIcon').show()
      }, 600)
    })
    $('#guestBtn').on('touchend', function () {
      if ($this.type) {
        $this.outputMsg('请先发送请帖');
        return false;
      }
      $('#updownIcon').hide()
      if ($this.send_name != '') {
        $('#gusetName').val($this.send_name)
      }
      _divMain.style.display = 'block'
      setTimeout(function () {
        $('#guestBg').show();
        $('#gusetBox').css({
          'bottom': 0
        })
      })
      $('.disNum_col').css({
        'left': $('.numpre').width() + 'px'
      })
      if (navigator.userAgent.indexOf('Android') > -1) {
        setTimeout(function () {
          $('#gusetName').focus()
        }, 300)
      } else if (navigator.userAgent.indexOf('iPhone') > -1) {
        $('#gusetName').focus();
        if (navigator.userAgent.indexOf('iPhone OS 11') <= -1) {
          _timer = setInterval(function () {
            document.body.scrollTop = document.body.scrollHeight
          }, 200)
        }
      }
      if (navigator.userAgent.indexOf('iPhone OS 11') <= -1) {
        document.querySelector('#gusetBox').scrollIntoView(false)
        document.querySelector('.send_box').scrollIntoView(false)
        document.querySelector('#gusetName').scrollIntoView(false)
      }

    })
    $(document).on('touchend', '#gusetName', function () {
      if (navigator.userAgent.indexOf('iPhone OS 11') <= -1) {
        document.querySelector('#gusetBox').scrollIntoView(false)
        document.querySelector('.send_box').scrollIntoView(false)
        document.querySelector('#gusetName').scrollIntoView(false)
      }
    })
    $('#sendNum').on('touchstart', function () {
      if ($this.type) {
        $this.outputMsg('请先发送请帖');
        return false;
      }
      if ($('#gusetName').val() != '') {
        $this.send_name = $('#gusetName').val()
        $('#gusetBox').css({
          'bottom': '-200px'
        })
        $('#guestBg').hide()
        setTimeout(function () {
          _divMain.style.display = 'none'
          $('#guestPage').find('.animated').show()
          $('#map').show()
          $this.fy = true
          $post({
        	  url: '/index/addInvate',
        	  data: {
        		  name: $("#gusetName").val(),
        		  congratulation: $("#congratulation").val(),
        		  state: $(".sk").parent().attr("state"),
        		  num: $(".disNum").text()
        	  },
        	  success: function(res){
        		  console.log(res);
        	  }
          });
          $('#gusetName').blur()
          $('#updownIcon').show()
        }, 600)
        document.body.scrollTop = bfscrolltop
        clearInterval(_timer)
      } else {
        $this.outputMsg('请填写您的姓名')
      }
    })
    $(document).on('touchstart', '.gnl', function () {
      var _index = $('.addNum').index(this);
      if (_num > 1) {
        _num--;
        _num == 1 ? $('.addNum').eq(0).removeClass('ak') : null;
      }
      $('.disNum').text(_num);
    })
    $(document).on('touchstart', '.gnr', function () {
      var _index = $('.addNum').index(this);
      _num++;
      $('.addNum').eq(0).addClass('ak');
      $('.disNum').text(_num);
    })
  }
  musicStatePause = function () {
    return this.musicStatePause
  }
  musicPause = function ($state) {
    var $this = this;
    if ($state) {
      this.musicStatePause = true;
      this.writeCookie('musicStatePause', 'true', 360)
      document.getElementById('playMusic').pause()
      $('#musicBtn').removeClass('rotate')
      $('#musicBtn img').attr('src', $this.musicClose)
    } else {
      this.musicStatePause = false;
      this.writeCookie('musicStatePause', 'false', 360)
      document.getElementById('playMusic').play()
      $('#musicBtn').addClass('rotate')
      $('#musicBtn img').attr('src', $this.musicOpen)
    }
  }
    gotoPage = function (n) {
        var $this = this,
            _seating = $this.seating;
        if (n == _seating) return false;
        var s = 0;
        if (n == 0) {
            $('#upImg').show()
        } else {
            $('#upImg').hide()
        }
        while (s <= n) {
            $('.ele_background_' + s).css({
                '-webkit-transform': 'translateY(0)'
            });
            s++
        }
        $('#video').parent().removeClass('vhave');
        $('#video').on('touchstart', function () {
            if (navigator.userAgent.indexOf('Android') > -1) {
                document.getElementById('vid').muted = "muted";
                document.getElementById('vid').play()
            }
        })
        if (n < _seating) {
            $('.layout').eq(n).removeClass('hide').css({
                '-webkit-transform': 'scale(1) translateY(0)'
            })
            $('.layout').eq(_seating).addClass('hide').css({
                '-webkit-transform': 'translateY(0)'
            })
        } else {
            $('.layout').eq(n).removeClass('hide').css({
                '-webkit-transform': 'translateY(0)'
            })
            $('.layout').eq(_seating).addClass('hide')
        }
        $this.seating = n;
        $this.seatState = false
        $('#other .editIcon').remove()
        $('#all-page').empty().append($this.createPage($this.result.page))
        $this.addStyleNode()
        // $this.lastAbout()
        if (n >= $this.result.page.length - 1) {
            $this.otherMap($this.srcMap)
            $('.mapSeat').attr('src', $this.mapSrc)
            $('#upImg').hide()
            setTimeout(function () {
                $('#updownIcon').hide()
            }, 300)
        }
        if (!$this.editState) {
            return false
        }
        var editIcon = $('.editIcon');
        editIcon.addClass('hide');
        for (var i = 0; i < editIcon.length; i++) {
            if (editIcon.eq(i).attr('page_id') == $this.result.page[$this.seating].id) {
                editIcon.eq(i).removeClass('hide');
            }
        }
        var editIconPopTip = $('.edit_icon_pop_container')
        editIconPopTip.addClass('hide')
        var j = 0;
        if (editIconPopTip.length % 2 === 0) {
            j = editIconPopTip.length / 2 - 1
        } else {
            j = Math.floor(editIconPopTip.length / 2)
        }
        if (editIconPopTip.eq(j).attr('page_id') == $this.result.page[$this.seating].id) {
            if (localStorage.getItem('edit_icon_pop_tip') === 'true') {

                editIconPopTip.eq(j).addClass('hide');
            } else {
                if (editIconPopTip.eq(j).attr('page_id') == $this.result.page[$this.seating].id) {
                    setTimeout(function () {
                        if (editIconPopTip.eq(j).attr('page_id') == $this.result.page[$this.seating].id) {
                            editIconPopTip.eq(j).removeClass('hide');
                            editIconPopTip.eq(j).find('.edit_icon_poptip').addClass('pop_tip_zoom')
                        } else {
                            editIconPopTip.eq(j).addClass('hide');
                        }

                    }, 3000)
                } else {
                    editIconPopTip.eq(j).addClass('hide');
                }


            }
        }
        if ($this.hideEditState) {
            editIcon.addClass('nosee');
            editIconPopTip.eq(j).addClass('hide');
        }
    }
  autoPlayPage = function () {
    if (this.editState == true) {
      return false
    }
    var $this = this,
      ts,
      n = $this.seating;
    if ($this.autoState) {
      if ($this.seating <= $this.result.page.length - 1) {
        $('#upImg').show()
      }
      clearTimeout(ts)
      return false
    } else {
      $('#upImg').hide()
    }
    $('#video').parent().removeClass('vhave');
    if (n < $this.result.page.length - 1) {
      n++
      $this.gotoPage(n);
      ts = setTimeout(function () {
        $this.autoPlayPage();
        clearTimeout(ts)
      }, 7000)
    } else {
      n = 0;
      $('.ebg').css({
        "-webkit-transform": 'translateY(' + $this.height + 'px)'
      })
      $this.gotoPage(n);
      ts = setTimeout(function () {
        $this.autoPlayPage();
        clearTimeout(ts)
      }, 7000)
    }
  }
  upDownIcon = function () {
    var $this = this,
      _animateIcon = document.createElement('div');
    _animateIcon.setAttribute('id', 'upImg');
    _animateIcon.style.bottom = 80 / this.UI_WIDTH * this.width + 'px';
    _animateIcon.innerHTML = '<img src="' + $this.pageIcon + '"/>'
    document.body.appendChild(_animateIcon)
  }
  iosReg = function () {
    var $this = this,
      str = navigator.userAgent.toLowerCase();
    var ver = str.match(/cpu iphone os (.*?) like mac os/);
    if (ver && ver[1].split(/_/g)[0] < 9) {
      return ver[1].split(/_/g)[0]
    }
  }
  isIphoneX = function () {
    return navigator.userAgent.indexOf('iPhone') > -1 && (screen.height == 812 && screen.width == 375)
  }
  touchAction = function () {
    var hammertime = new Hammer(document.getElementById("wrap"));
    var $this = this,
      _time = $this.type ? 300 : 600;
    if ($this.type) {
      $('.layout').css({
        '-webkit-transition-duration': '300ms'
      })
    }
    hammertime.get('swipe').set({
      direction: Hammer.DIRECTION_ALL
    })
    hammertime.on("swipe", function (e) {
      var deltaY = e.deltaY;
      if ($this.seating >= $this.result.page.length - 2) {
        //   $this.otherMap($this.srcMap)
      }
      $this.autoState = true;
      if (deltaY < 0) {
        $('#upImg').hide()
      } else {
        if ($this.seating <= 1) {
          $('#upImg').show()
        }
      }
      if (deltaY > 0 && $this.seating > 0) {
        if ($this.seatState) {
          var _seat = $this.seating;
          if ($this.exPage) {
            _seat = $this.exPage + 1
          } else {
            if ($this.sortS) {
              _seat = Number($this.sortS + 1)
            }
          }
          $this.seatState = false
          $('.layout').eq(_seat - 1).css({
            '-webkit-transform': 'translateY(' + $this.height + 'px)'
          })
          $('#all-page').css({
            'opacity': 0
          })
          $('.ele_background_' + Number(_seat - 1)).css({
            '-webkit-transform': 'translateY(' + $this.height + 'px)'
          })
          setTimeout(function () {
            $('#all-page').css({
              'opacity': 1
            })
            $('#all-page').empty().append($this.createPage($this.result.page))
            $this.addStyleNode()
          }, 600)
        }
        if (navigator.userAgent.indexOf('Android') > -1) {
          if ($this.seating - 2 == $this.videoNext && document.getElementById('vid')) {
            setTimeout(function () {
              document.getElementById('vid').muted = "muted";
              document.getElementById('vid').play()
            }, 600)
          }
        }
        $this.seating--;
        if ($this.type) {
          $('.layout').eq($this.seating + 1).css({
            'opacity': '0'
          })
          $('.ele_background_' + Number($this.seating + 1)).css({
            'opacity': '0'
          })
          setTimeout(function () {
            $('.layout').eq($this.seating).removeClass('hide').css({
              'opacity': '1'
            })
            $('.layout').eq($this.seating + 1).addClass('hide').css({
              'opacity': '1'
            })
          }, _time)
        } else {
          $('.layout').eq($this.seating + 1).css({
            '-webkit-transform': 'translateY(' + $this.height + 'px)'
          })
          $('.ele_background_' + Number($this.seating + 1)).css({
            '-webkit-transform': 'translateY(' + $this.height + 'px)'
          })

          setTimeout(function () {
            $('.layout').eq($this.seating).removeClass('hide').css({
              '-webkit-transform': 'scale(1) translateY(0)'
            })
            $('.layout').eq($this.seating + 1).addClass('hide').css({
              '-webkit-transform': 'translateY(0)'
            })
          }, _time)
        }
      } else if (deltaY < 0 && $this.seating < $this.result.page.length && $this.seating != $this.result.page.length - 1) {
        if ($this.seatState) {
          var _seat = $this.seating;
          if ($this.exPage) {
            _seat = $this.exPage + 1
          }
          $this.seatState = false
          $('.layout').eq(_seat - 1).css({
            '-webkit-transform': 'translateY(-' + $this.height + 'px)'
          })
          $('#all-page').css({
            'opacity': 0
          })
          $('.ele_background_' + Number(_seat - 1)).css({
            '-webkit-transform': 'translateY(0px)'
          })
          setTimeout(function () {
            $('#all-page').css({
              'opacity': 1
            })
            $('#all-page').empty().append($this.createPage($this.result.page))
          }, 600)
        }
        if (navigator.userAgent.indexOf('Android') > -1) {
          if ($this.seating == $this.videoNext && document.getElementById('vid')) {
            setTimeout(function () {
              document.getElementById('vid').muted = "muted";
              document.getElementById('vid').play()
            }, 600)
          }
        }
        $this.seating++;
        if ($this.type) {
          $('.layout').eq($this.seating - 1).css({
            'opacity': '0'
          });
          $('.ele_background_' + $this.seating).css({
            'opacity': '1'
          })
          setTimeout(function () {
            $('.layout').eq($this.seating).removeClass('hide')
            $('.layout').eq($this.seating - 1).addClass('hide')
          }, _time)
        } else {
          $('.layout').eq($this.seating - 1).css({
            '-webkit-transform': 'scale(1) translateY(-' + $this.height * 1.2 + 'px)'
          });
          $('.ele_background_' + $this.seating).css({
            '-webkit-transform': 'translateY(0)'
          })
          setTimeout(function () {
            $('.layout').eq($this.seating).removeClass('hide').css({
              '-webkit-transform': 'translateY(0)'
            })
            $('.layout').eq($this.seating - 1).addClass('hide')
          }, _time)
        }
      }
      if ($this.editState && ($this.type || $this.getParams('edit'))) {
        var editIcon = $('.editIcon');
        editIcon.addClass('hide');
        for (var i = 0; i < editIcon.length; i++) {
          if (editIcon.eq(i).attr('page_id') == $this.result.page[$this.seating].id) {
            editIcon.eq(i).removeClass('hide');
          }
        }
      }
    });
  }
  getParams = function ($obj) {
    var _params = get_url_param();
    return _params[$obj];

    function get_url_param() {
      var urlParams;
      var match,
        pl = /\+/g,
        search = /([^&=]+)=?([^&]*)/g,
        decode = function (s) {
          return decodeURIComponent(s.replace(pl, " "));
        },
        query;
      window.location.hash.substring() ?
        query = (window.location.hash.substring().split('?'))[1] :
        query = window.location.search.substring(1);
      urlParams = {};
      while (match = search.exec(query))
        urlParams[decode(match[1])] = decode(match[2]);
      return urlParams;
    }
  }
  writeCookie = function (name, value, hours) {
    var expire = "";
    if (hours != null) {
      expire = new Date((new Date()).getTime() + hours * 3600000);
      expire = "; expires=" + expire.toGMTString();
    }
    document.cookie = name + "=" + escape(value) + expire + ";path=/";
  }
  getCookie = function (cookieName) {
    var cookieValue = document.cookie;
    var cookieStartAt = cookieValue.indexOf("" + cookieName + "=");
    if (cookieStartAt == -1) {
      cookieStartAt = cookieValue.indexOf(cookieName + "=");
    }
    if (cookieStartAt == -1) {
      cookieValue = null;
    } else {
      cookieStartAt = cookieValue.indexOf("=", cookieStartAt) + 1;
      cookieEndAt = cookieValue.indexOf(";", cookieStartAt);
      if (cookieEndAt == -1) {
        cookieEndAt = cookieValue.length;
      }
      cookieValue = unescape(cookieValue.substring(cookieStartAt, cookieEndAt));
    }
    return cookieValue;
  }
  sdkData = function (_data, _met, _error) {
    var $this = this,
      obj = '{"events":[' + JSON.stringify(_data) + ']}',
      _appName = this.getParams('appName') || '';
    $.ajax({
      url: $this.API.sdkData,
      type: 'get',
      headers: {
        appName: _appName
      },
      success: function (result) {
        if (_met) {
          _met()
        }
      },
      error: function (data) {
        _error && _error()
      }
    })
  }
  rans = function (len) {
    if (localStorage.getItem('rans')) {
      return localStorage.getItem('rans');
    } else {
      len = len || 32;
      var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';/****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
      var maxPos = $chars.length;
      var pwd = '';
      for (i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
      }
      localStorage.setItem('rans', pwd)
      return pwd;
    }
  }
  sdk = function () {
    var $this = this;
    $.ajax({
      url: 'https://www.hunliji.com/sms/ip',
      type: 'get',
      success: function (result) {
        $this.ip = result;
        localStorage.setItem('ip', result)
      }
    })
    setTimeout(function () {
      $this.sdkData({
        action: 'view',
        eventable_type: 'Card',
        additional: {
          ip: $this.ip,
          card_id: $this.card_id,
          num: $this.rans(32),
        }
      })
    }, 3000)
  }
  outputMsg = function (str, $state) {
    if ($('.msgWin').length > 0) {
      return false
    }
    var _div = document.createElement('div'),
      _style = "";
    if ($state) {
      _div.style.width = '60%';
      _div.style.background = 'rgba(0,0,0,.6)';
    }
    _div.style.zIndex = 999;
    _div.setAttribute('class', 'msgWin');
    _div.innerHTML = '<p>' + str + '</p>';
    document.getElementById('other').appendChild(_div);
    setTimeout(function () {
      if (!$state) {
        $('.msgWin').remove()
      }
    }, 1500)
  }
  createBtn = function(){
    var $this = this
    var flag = true
    var _html = '<div id="createBtn">\
        <a href="javascript:;">开始制作</a>\
        <div class="fill"></div>\
      </div>'
    $('#createBtn').length <= 0 ? $('body').append(_html): null
    $this.isIphoneX() && $('#createBtn .fill').show()
    $(document).on('touchstart','#createBtn',function(){
      if (flag) {
        wx.miniProgram.redirectTo({url: '/pages/cardInfo?tpl_id='+$this.getParams('card_id')})
        flag = false
      } else {
        setTimeout(function(){
          flag = true
        },1000)
        return false
      }
    })
  }
    addStyleNode = function () {
        // html中不能有其他style节点，其他样式都必须写到css中
//        var _style = document.createElement('style'),
//            $this = this
//        _style.innerHTML = $this.aniStyle
//        if ($('head').find('style').length > 0) {
//            $('head').find('style').remove()
//        }
//        $('head').append(_style);
    }
  return {
    addStyleNode: addStyleNode,
    createBtn:createBtn,
    ajax_info: ajax_info,
    init: init, //初始化
    get_infinite: get_infinite,
    loadAnimate: loadAnimate,
    allImg: allImg,
    loading: loading,
    add_infinite: add_infinite,
    rov_infinite: rov_infinite,
    otherMap: otherMap, // 获取地图数据
    addStyle: addStyle,
    createPage: createPage, //插入DOM
    guestsPage: guestsPage, //加载宾客页
    guestAction: guestAction, //宾客相关事件
    musicPause: musicPause, //关闭/开启音乐
    outputMsg: outputMsg,
    musicStatePause: musicStatePause,
    gotoPage: gotoPage, //激活已选单页
    autoPlayPage: autoPlayPage, //是否自动翻页
    upDownIcon: upDownIcon,
    iosReg: iosReg, //IOS版本判断
    touchAction: touchAction, //触发事件
    getParams: getParams,
    writeCookie: writeCookie,
    getCookie: getCookie,
    rans: rans,
    sdkData: sdkData,
    sdk: sdk,
    isIphoneX: isIphoneX
  }
}()
var INVITATION_CARD = new boot()
if (INVITATION_CARD.getParams('weapp_preview')) {
  setTimeout(function(){
    //利用iframe的onload事件刷新页面
    document.title = '模板预览';
    var iframe = document.createElement('iframe');
    iframe.style.visibility = 'hidden';
    iframe.style.width = '1px';
    iframe.style.height = '1px';
    iframe.onload = function () {
      setTimeout(function () {
          document.body.removeChild(iframe);
      }, 0);
    };
    document.body.appendChild(iframe);
  },0);
}
INVITATION_CARD.ajax_info()