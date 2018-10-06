package com.marry.serviceimpl;

import org.springframework.stereotype.Service;

import com.marry.entity.Users;
import com.marry.inner.base.serviceimpl.BaseServiceImpl;
import com.marry.mapper.UsersMapper;
import com.marry.service.UsersService;

@Service
public class UsersServiceImpl extends BaseServiceImpl<Users, UsersMapper> implements UsersService{

}
