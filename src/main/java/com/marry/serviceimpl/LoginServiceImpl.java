package com.marry.serviceimpl;

import org.springframework.stereotype.Service;

import com.marry.entity.Login;
import com.marry.inner.base.serviceimpl.BaseServiceImpl;
import com.marry.mapper.LoginMapper;
import com.marry.service.LoginService;

@Service
public class LoginServiceImpl extends BaseServiceImpl<Login, LoginMapper> implements LoginService{

}
