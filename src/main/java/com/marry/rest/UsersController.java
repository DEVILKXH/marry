package com.marry.rest;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marry.entity.Login;
import com.marry.entity.Users;
import com.marry.inner.base.controller.BaseController;
import com.marry.inner.constant.Constant;
import com.marry.inner.vo.AjaxResult;
import com.marry.service.LoginService;
import com.marry.service.UsersService;

@Controller
@RequestMapping(value = "")
public class UsersController extends BaseController<Users, UsersService>{

	@Autowired
	private HttpSession session;
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = {"/login",""})
	public String login() {
		if(session.getAttribute(Constant.USER_KEY) == null){
			return Constant.LOGIN;
		}else {
			return Constant.INDEX;
		}
	}
	
	@RequestMapping(value = {"/logout"})
	public String logout() {
		session.invalidate();
		return Constant.LOGIN;
	}
	
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<Users> doLogin(@RequestBody Users user){
		AjaxResult<Users> ajax = new AjaxResult<>();
		user.setUserCode(user.getUserCode().toUpperCase());
		Users _user = new Users();
		_user.setUserCode(user.getUserCode());
		Users myUser = service.selectOne(_user);
		if(null == myUser || !myUser.getPassword().equals(user.getPassword())) {
			ajax.setStatus("404");
			ajax.setMessage("账号或密码错误");
		}else {
			ajax.setStatus("200");
			ajax.setMessage("登陆成功");
			ajax.setObject(myUser);
			Login login = new Login();
			login.setId(UUID.randomUUID().toString());
			loginService.insertSelective(login);
			session.setAttribute(Constant.USER_KEY, myUser.getId());
		}
		return ajax;
	}
	
}
