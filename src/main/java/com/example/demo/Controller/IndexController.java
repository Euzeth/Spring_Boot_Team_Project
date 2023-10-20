package com.example.demo.Controller;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Dto.MemberDto;
import com.example.demo.Domain.Dto.MusicDto;
import com.example.demo.Domain.Entity.Music;
import com.example.demo.Domain.Service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class IndexController{

	@Autowired
	MusicService musicService;

	private String IndexRequest(HttpSession session) {
		String role = (String) session.getAttribute("role");
		if (role == null) {
			System.out.println("Role is null, redirecting to /index");
			return "redirect:/index";
		}

		if (role.equals("ROLE_USER") || role.equals("ROLE_MEMBER")) {
			System.out.println("login completed");
			return "redirect:/indexlog";
		}
		return "redirect:/index";
	}

	@GetMapping("/main")
	public String go_index(HttpSession session, Authentication authentication) {
		System.out.println("Authentication : " + authentication);
		return IndexRequest(session);
	}


	@GetMapping("/index")
	public void index_get(Model model) {
		log.info("GET /main/index");

		//TOP10 가져오기
		List<Music> list = musicService.Top100();
		List<MusicDto> Top10List = list.stream()
				.limit(10) // 처음 10개 요소만 가져오기
				.map(music -> MusicDto.Of(music))
				.collect(Collectors.toList());

		model.addAttribute("Top10List",Top10List);
		System.out.println(Top10List);
	}


	@GetMapping("/indexlog")
	public void indexlog_get(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		log.info("GET /indexlog");
		log.info("principalDetails " + principalDetails);

		model.addAttribute("principalDetails", principalDetails);

		//TOP10 가져오기
		List<Music> list = musicService.Top100();
		List<MusicDto> Top10List = list.stream()
				.limit(10) // 처음 10개 요소만 가져오기
				.map(music -> MusicDto.Of(music))
				.collect(Collectors.toList());

		model.addAttribute("Top10List",Top10List);

	}

}