package com.example.demo.Controller.member;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Dto.MemberDto;
import com.example.demo.Domain.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@GetMapping("/add")
	public void add() {
		log.info("GET /member/add");
	}

	@PostMapping("/add")
	public String add_post(){
		log.info("POST /member/add");
		return null;
	}

	@GetMapping(value = "/auth/email/{username}")
	public @ResponseBody void email_auth(@PathVariable String username, HttpServletRequest request)
	{
		log.info("GET /member/auth/email/" + username);

		//메일설정
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("knightcy1@gmail.com");
		mailSender.setPassword("kagg dmjz hetp uurd");

		Properties props = new Properties();
		props.put("mail.smtp.auth","true");
		props.put("mail.smtp.starttls.enable","true");
		mailSender.setJavaMailProperties(props);

		//난수값생성
		String tmpPassword = (int)(Math.random()*10000000)+"";

		//본문내용 설정
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(username);
		message.setSubject("[WEB_TEST]이메일코드발송");
		message.setText(tmpPassword);

		//발송
		mailSender.send(message);

		//세션에 코드저장
		HttpSession session = request.getSession();
		session.setAttribute("email_auth_code", tmpPassword);
	}

	@GetMapping("/auth/confirm/{code}")
	public @ResponseBody String email_auth_confirm(@PathVariable String code, HttpServletRequest request)
	{
		System.out.println("GET /member/auth/confirm/" + code);
		HttpSession session = request.getSession();
		String auth_code = (String)session.getAttribute("email_auth_code");
		if(auth_code != null)
		{
			if(auth_code.equals(code)){
				session.setAttribute("is_email_auth", true);
				return "success";
			}else{
				session.setAttribute("is_email_auth", false);
				return "failure";
			}
		}

		return "failure";
	}

	@GetMapping("/selectall")
	public void selectall(Model model) {
		log.info("GET /member/selectall");
	}
	
	@GetMapping("/search")
	public void search(@RequestParam String id) {
        log.info("GET /member/search");
        memberService.searchMember(id);
        
    }
	
	@PostMapping("/search")
	public String search_post(@RequestParam String id, Model model) {
		log.info("POST /member/search");
		return "";
	}

	@GetMapping("/update")
	public void update(MemberDto dto) {
		log.info("GET /member/update");
		memberService.modifyMember(dto);
	}
	
	@PostMapping("/update")
	public String update(MemberDto dto, Authentication authentication) {
		log.info("POST /member/update");
		memberService.modifyMember(dto);
		
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();

		
		return "redirect:user";
	}
	
	@GetMapping("/remove")
	public void remove(@RequestParam String id) {
		log.info("GET /member/delete");
		memberService.removeMember(id);
	}
	
	@PostMapping("/remove")
	public String remove1(@RequestParam String id) {
		log.info("POST /member/delete");
		memberService.removeMember(id);
		return "redirect:member";
	}

	@GetMapping("/login")
	public void login() {
		log.info("GET /login");
	}

	@GetMapping("/join")
	public void join() {
		log.info("GET /join");
	}

	@PostMapping("/join")
	public String join(@Valid MemberDto dto, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("POST /join" + dto);

		if(bindingResult.hasFieldErrors()) {
			for( FieldError error  : bindingResult.getFieldErrors()) {
				log.info(error.getField()+ " : " + error.getDefaultMessage());
				model.addAttribute(error.getField(), error.getDefaultMessage());
			}
			return "/member/join";
		}

		boolean isjoin = memberService.addMember(dto,model,request);
		if(!isjoin)
		{
			return "/member/join";
		}
		return "redirect:login?msg=Join_Success!";
	}

	@GetMapping("/mypage")
	public String mypage(HttpSession session, Authentication authentication, Model model) {
		System.out.println("authentication : " + authentication);
		
		
		return MypageRequest(session);
	}	
		

	@PostMapping("/mypage")
	public String mypage(HttpSession session) {
		return MypageRequest(session);
	}

	private String MypageRequest(HttpSession session) {
		String role = (String) session.getAttribute("role");
		if (role.equals("ROLE_USER")) {
			System.out.println("user's mypage");
			return "redirect:/member/user";
		} else if (role.equals("ROLE_MEMBER")) {
			System.out.println("member's mypage");
			return "redirect:/member/member";
		}
		return "redirect:/indexlog";
	}

	@GetMapping("/user")
	public void user() {
		log.info("GET /user");
	}

	@GetMapping("/member")
	public void	 member(Model model) {
		log.info("GET /member");
		List<MemberDto> list = memberService.getAllMember();
		
		List<MemberDto> userDtoList = list.stream()
	            .filter(dto -> "ROLE_USER".equals(dto.getRole()))
	            .collect(Collectors.toList());

		userDtoList.forEach((dto) -> {
	        System.out.println(dto);
	    });

	    model.addAttribute("list", userDtoList);
	}

	@GetMapping("/checkDuplicate")
	public ResponseEntity<String> checkDuplicate(@RequestParam("id") String id) {
	    try {
	        boolean duplicate_id = memberService.idcheck(id);
	        return ResponseEntity.ok(String.valueOf(duplicate_id));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("중복 확인 중에 오류가 발생했습니다.");
	    }
	}

	////////////////////////////////////////////////////////////////////////
	@GetMapping("/mylist")
	public void myList(){
		log.info("/GET mylist");

	}





	
}