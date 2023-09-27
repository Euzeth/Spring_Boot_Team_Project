package com.example.demo.Controller;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Dto.MemberDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

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
	public void indexlog_get(@AuthenticationPrincipal PrincipalDetails principalDetails, MemberDto memberDto, Model model) {
			log.info("GET /indexlog");
			log.info("PrincipalDetails: " + principalDetails);
			memberDto = new MemberDto();
			memberDto.setName(principalDetails.getName());
			memberDto.setUsername(principalDetails.getUsername());
			memberDto.setPhone(principalDetails.getPhone());
			memberDto.setAddr1(principalDetails.getAddr1());
			memberDto.setAddr2(principalDetails.getAddr2());


			model.addAttribute("memberDto : ", memberDto);
			System.out.println(memberDto);
		}

	
	@PostMapping("/indexlog")
	public void f4() {
		log.info("POST /indexlog");
	}

}