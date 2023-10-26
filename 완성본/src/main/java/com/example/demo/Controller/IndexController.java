package com.example.demo.Controller;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Dto.MemberDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController{
	
	
	@GetMapping("/index")
	public void f1() {
		log.info("GET /main/index");
	}
	
	@PostMapping("/index")
	public void f2() {
		log.info("POST /main/index");
	}
	
	@GetMapping("/indexlog")
	public void indexlog_get(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model)
	{
		log.info("GET /indexlog");
		log.info("principalDetails " + principalDetails);

		model.addAttribute("principalDetails", principalDetails);
	}
	
	@PostMapping("/indexlog")
	public void indexlog_post()
	{
		log.info("POST /indexlog");
	}

}