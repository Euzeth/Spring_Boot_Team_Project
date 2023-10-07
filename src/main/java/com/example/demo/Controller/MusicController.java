package com.example.demo.Controller;

import com.example.demo.Domain.Dto.MembershipDto;
import com.example.demo.Domain.Dto.MusicDto;
import com.example.demo.Domain.Entity.Music;
import com.example.demo.Domain.Service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class MusicController {

	@Autowired
	MusicService musicService;

	@GetMapping("/song")
	public void song() {
		System.out.println("GET /song");
	}

	@GetMapping("/search")
	public void search(String searchText, String type, MusicDto dto, Model model){
		log.info("GET /search");
		//search(searchText, type, dto)
//		if(type!=null){
//			dto.setType(type);
//		}
//		if(searchText!=null){
//			dto.setSearchText(searchText);
//		}

		//서비스 실행
		Map<String,Object> map = musicService.GetSearchList(dto);

		List<Music> list = (List<Music>)map.get("list");

		List<MusicDto> searchList = list.stream().map(music -> MusicDto.Of(music)).collect(Collectors.toList());

		System.out.println(searchList);

		model.addAttribute("searchList", searchList);

	}


}
