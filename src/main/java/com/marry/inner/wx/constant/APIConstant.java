package com.marry.inner.wx.constant;

public enum APIConstant {
	//----------------------服务号-----------------------
	
	MP_ACCESS_USERGROUPS("MP1", "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN"), // 查询公众号粉丝所有分组

	MP_ACCESS_USERGROUP("MP2", "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN"), // 查询粉丝所在分组
	
	MP_ACCESS_USER_BASEINFORMATION("MP3", "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN"), // 查询粉丝基本信息
	
	MP_ACCESS_URSERLIST("MP4", "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID"), // 获取关注者列表
	
	MP_ACCESS_TOKEN("MP5", "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"), // 通过code换取网页授权access_token

	MP_ACCESS_TOKEN_GET("MP6", "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"), // 主动获取access_token的接口地址（GET） 限200（次/天）
	
	MP_ACCESS_REFRESHTOKEN("MP7", "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN"), // 刷新token
	
	MP_ACCESS_CODE("MP8", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect"), // 获取code
	
	MP_TWO_DIMENSIONAL_CODE_TICKET("MP9", "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN"), // 创建二维码ticket

	MP_TWO_DIMENSIONAL_CODE("MP10", "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET"), // 通过ticket换取二维码
	
	MP_MEMU_CREATE("MP11", "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"), // 菜单创建（POST） 限100（次/天）
	
	MP_POST_MESSAGE("MP12", "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"), // 客服消息
	
	MP_RESOURCE("MP13", "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID"), // 下载资源
	
	MP_JSAPI_TICKET("MP14", "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi"), // JSAPI_TICKET
	
	
	
	
	//----------------------企业号-----------------------	
	
	QY_ACCESS_USER_QYH_BASEINFORMATION("QY1", "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID"), // 获取企业号下的特定成员基本信息

	QY_ADD_USER("QY2", "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN"), // 新增企业号成员

	QY_UPDATE_USER("QY3", "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN"), // 更新企业号成员
	
	QY_DELETE_USER("QY_USER_DELETE","https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=USERID"), // 删除企业号成员
	
	QY_MEMU_CREATE("QY4", "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENTID"), // 菜单创建（POST） 限100（次/天）
	
	QY_ACCESS_TOKEN_GET("QY5", "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET"), // 主动获取access_token的接口地址（GET） 限200（次/天）
	
	QY_USER_INFO("QY6", "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESSTOKEN&code=CODE&agentid=AGENTID"), // 通过code换取企业用户信息 返回userid
	
	QY_POST_MESSAGE("QY7", "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN"), // 客服消息
	
	QY_DEPT_CREATE("QY8", "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN"), // 创建部门
	
	QY_DEPT_UPDATE("QY_UPDATE_DEPT", "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN"),// 修改部门
	
	QY_DEPT_DELETE("QY_DELETE_DEPT", "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=DEPTID"), //删除部门
	
	QY_DEPT_LIST("QY9", "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN"), // 获取部门列表
	
	QY_UPLOADRESOURCE("QY10", "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE"), // 上传
	
	QY_DOWNLOADRESOURCE("QY11", "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID"), // 下载
	
	QY_DEPT_USER_LIST("QY12", "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPT_ID&fetch_child=FETCH_CHILD&status=STATUS"), // 获取部门成员列表
	
	QY_USER_BATCH_SYNC_INCRE("QY_USER_BATCH_SYNC_INCRE", "https://qyapi.weixin.qq.com/cgi-bin/batch/syncuser?access_token=ACCESS_TOKEN"), // 用户批量同步(增量)
	
	QY_USER_BATCH_SYNC_FULL("QY_USER_BATCH_SYNC_FULL", "https://qyapi.weixin.qq.com/cgi-bin/batch/replaceuser?access_token=ACCESS_TOKEN"), // 用户批量同步(全量)
	
	QY_PARTY_BATCH_SYNC_FULL("QY_PARTY_BATCH_SYNC_FULL", "https://qyapi.weixin.qq.com/cgi-bin/batch/replaceparty?access_token=ACCESS_TOKEN"), // 部门批量同步(全量)
	
	QY_USER_BATCH_SYNC_RESULT("QY_USER_BATCH_SYNC_RESULT", "https://qyapi.weixin.qq.com/cgi-bin/batch/getresult?access_token=ACCESS_TOKEN&jobid=JOBID"), // 用户批量同步返回结果
	
	QY_JSAPI_TICKET("QY14", "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN"), // JSAPI_TICKET
	
	
	QY_DEPT_USER_DETAIL_LIST(
			"QY_DEPT_USER_DETAIL_LIST",
			"https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPT_ID&fetch_child=FETCH_CHILD&status=STATUS"), // 通过部门获取用户详情
			
	QY_CARD_CODE_GET("QY_CARD_CODE_GET", "https://qyapi.weixin.qq.com/cgi-bin/card/code/get?access_token=ACCESS_TOKEN"), // 根据卡券Code查询
	QY_CARD_GET("QY_CARD_GET", "https://qyapi.weixin.qq.com/cgi-bin/card/get?access_token=ACCESS_TOKEN"), // 根据cardId查询卡券
	QY_CONVERT_TO_USERID("QY_CONVERT_TO_USERID", "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid?access_token=ACCESS_TOKEN"), // userId查询openid
	QY_USER_GET("QY_USER_GET", "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID"), // openId查询人员
	QY_CARD_CODE_CONSUME("QY_CARD_CODE_CONSUME", "https://qyapi.weixin.qq.com/cgi-bin/card/code/consume?access_token=ACCESS_TOKEN"), // 卡券核销
	
	;
	
	private APIConstant(String name, String url) {
		this.name = name;
		this.url = url;
	}

	private final String name;
	private final String url;

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}
}
