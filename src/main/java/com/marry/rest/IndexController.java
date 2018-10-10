package com.marry.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marry.entity.Guests;
import com.marry.inner.constant.Constant;
import com.marry.inner.wx.constant.WeiXinWorkConstant;
import com.marry.inner.wx.util.TokenUtils;
import com.marry.inner.wx.util.WeiXinApiUtil;
import com.marry.inner.wx.vo.WeChatConfig;
import com.marry.service.GuestsService;

@Controller
@RequestMapping(value = "/index")
public class IndexController {

	@Autowired
	private GuestsService guestsService;
	
	@RequestMapping(value = "")
	public String index() {
		return Constant.INDEX;
	}
	
	@RequestMapping(value = "/addInvate", method = RequestMethod.POST)
	@ResponseBody
	public Guests addInvate(@RequestBody Guests guests){
		guests.setId(UUID.randomUUID().toString());
		guestsService.insertSelective(guests);
		return guests;
	}
	
	@RequestMapping(value = "/getCongratulation", method = RequestMethod.POST)
	@ResponseBody
	public List<Guests> getCongratulation(){
		return guestsService.selectAll();
	}
	
	@RequestMapping(value = "/getWxconfig", method = RequestMethod.POST)
	@ResponseBody
	public WeChatConfig getWxconfig(@RequestBody String location) throws Exception {
		WeChatConfig wc = new WeChatConfig();
		HashMap<String, String> bizObj = new HashMap<String, String>();
		bizObj.put("jsapi_ticket", TokenUtils.checkTicketAPI(WeiXinWorkConstant.APPID, WeiXinWorkConstant.SECRET, null));
		bizObj.put("noncestr", WeiXinApiUtil.CreateNoncestr());
		bizObj.put("timestamp", Long.toString(new Date().getTime() / 1000));
		bizObj.put("url", location.split("#")[0].replaceAll("\"", ""));
		bizObj.put("signature", WeiXinApiUtil.GetJsAPISign(bizObj));
		wc.setAppId(WeiXinWorkConstant.APPID);
		wc.setNonceStr(bizObj.get("noncestr"));
		wc.setTimestamp(bizObj.get("timestamp"));
		wc.setSignature(bizObj.get("signature"));
		return wc;
	}
}
