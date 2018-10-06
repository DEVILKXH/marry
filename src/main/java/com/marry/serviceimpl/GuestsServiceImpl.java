
package com.marry.serviceimpl;

import org.springframework.stereotype.Service;

import com.marry.entity.Guests;
import com.marry.inner.base.serviceimpl.BaseServiceImpl;
import com.marry.mapper.GuestsMapper;
import com.marry.service.GuestsService;

/**
 *@date 2018年10月4日
 *@author Devil
 *TODO 
 *
 */
@Service
public class GuestsServiceImpl extends BaseServiceImpl<Guests, GuestsMapper> implements GuestsService{

}
