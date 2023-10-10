package com.example.demo.Controller;


import com.example.demo.Domain.Dto.MusicDto;
import com.example.demo.Domain.Entity.Music;
import com.example.demo.Domain.Service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;


import java.util.HashMap;
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
		if(type!=null){
			dto.setType(dto.getType());
		}
		if(searchText!=null){
			dto.setSearchText(dto.getSearchText());
		}

		//서비스 실행
		Map<String,Object> map = musicService.GetSearchList(dto);

		List<Music> list = (List<Music>)map.get("list");

		List<MusicDto> searchList = list.stream().map(music -> MusicDto.Of(music)).collect(Collectors.toList());

		System.out.println(searchList);


		model.addAttribute("searchList", searchList);
		model.addAttribute("searchText", dto.getSearchText());
		model.addAttribute("type",dto.getType());

	}

//	@GetMapping("/like")
//	public void like(MusicDto dto) {
//		log.info("GET /like");
//
//
//
//	}

	@GetMapping("/like")
	public String like(MusicDto dto) {
		log.info("GET /like");
		musicService.likeMusic(dto.getMusic_code());

		return "redirect:/search?type="+dto.getType()+"&searchText="+dto.getSearchText();
	}

}
