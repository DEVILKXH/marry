package com.marry.inner.wx.util;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.marry.inner.wx.constant.WeiXinWorkConstant;
import com.marry.inner.wx.vo.JsApiTicket;


public class WeiXinApiUtil{
	
	public static JsApiTicket getJsapiTicket(String appId, String appSecret, String agentid) throws Exception {
		String access_token = TokenUtils.checkTokenGZH(appId, appSecret, agentid);
		String url = WeiXinWorkConstant.CODE_TO_JSAPI_TICKET.replace("ACCESS_TOKEN", access_token);
		JSONObject object = WeixinUtil.httpRequest(url, "GET", null);
		return JSONObject.toJavaObject(object, JsApiTicket.class);
	}
	
	public static String getStringByUrl(String myUrl,String key) throws Exception{
		URL url = new URL(myUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		int code = conn.getResponseCode();
		if(code == 200){
			InputStream is = conn.getInputStream();
			int bufferSize = 1024;
			char[] buffer = new char[bufferSize];
			StringBuilder out = new StringBuilder();
			Reader in = new InputStreamReader(is, "UTF-8");
			for (; ; ) {
			    int rsz = in.read(buffer, 0, buffer.length);
			    if (rsz < 0)
			        break;
			    out.append(buffer, 0, rsz);
			}
			String result = out.toString();
			return (String) JSONObject.parseObject(result).get(key);
		}
		return null;
	}

	/**
	 * 随机字符串
	 * @return
	 */
	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}
	
	/** 
	 * 获取签名字符串 by wuhengbo@joeone.net 2016-01-27
	 * @param bizObj
	 * @return
	 * @throws Exception
	 */
	public static String GetJsAPISign(HashMap<String, String> bizObj) throws Exception {
        HashMap<String, String> bizParameters = new HashMap<String, String>();

        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(bizObj.entrySet());

        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> item = infoIds.get(i);
            if (item.getKey() != "") {
//                bizParameters.put(item.getKey().toLowerCase(), item.getValue());
            	bizParameters.put(item.getKey(), item.getValue());
            }
        }

        String bizString = FormatBizQueryParaMap(bizParameters, false);
       
        return Sha1(bizString);

    }
	
	public static String FormatBizQueryParaMap(HashMap<String, String> paraMap,
			boolean urlencode) throws Exception {

		String buff = "";
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
					paraMap.entrySet());

			Collections.sort(infoIds,
					new Comparator<Map.Entry<String, String>>() {
						public int compare(Map.Entry<String, String> o1,
								Map.Entry<String, String> o2) {
							return (o1.getKey()).toString().compareTo(
									o2.getKey());
						}
					});

			for (int i = 0; i < infoIds.size(); i++) {
				Map.Entry<String, String> item = infoIds.get(i);
				//System.out.println(item.getKey());
				if (item.getKey() != "") {
					
					String key = item.getKey();
					String val = item.getValue();
					if (urlencode) {
						val = URLEncoder.encode(val, "utf-8");// utf-8

					}
//					buff += key.toLowerCase() + "=" + val + "&";
					buff += key + "=" + val + "&";

				}
			}

			if (!buff.equals("")){
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return buff;
	}

	public final static String Sha1(String s) {
		 System.out.println("加密前的签名："+s);

        char hexDigits[]={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };       
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("sha-1");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            String rtn = new String(str);
   		 	System.out.println("加密后的签名："+rtn);
            return rtn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
       }
	}
}
