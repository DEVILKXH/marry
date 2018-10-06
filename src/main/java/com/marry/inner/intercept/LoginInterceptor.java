package com.marry.inner.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.marry.inner.constant.Constant;

public class LoginInterceptor implements HandlerInterceptor{

	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	            throws Exception {
	        //获取session
	        HttpSession session = request.getSession(true);
	        //判断用户ID是否存在，不存在就跳转到登录界面
	        if(session.getAttribute(Constant.USER_KEY) == null){
	        	response.sendRedirect(request.getContextPath()+"/login");
	            return false;
	        }else{
	            session.setAttribute(Constant.USER_KEY, session.getAttribute("userId"));
	            return true;
	        }
	    }
}
