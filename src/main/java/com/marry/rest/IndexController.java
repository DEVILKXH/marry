package com.marry.rest;

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
}
