package com.forms.prms.web.sysmanagement.user.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forms.prms.web.sysmanagement.user.service.UserInfoService;

@Controller
@RequestMapping("/common/userImg")
public class UserImgController {
	@Autowired
	private UserInfoService service;
	@RequestMapping("/fetchImg.do")
	public void fetchImg(@RequestParam("userId") String userId, HttpServletResponse response) throws Exception {
		//service.getImgById(userId, response);
	}
}
