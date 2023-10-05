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

import java.util.List;
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

	@GetMapping("/searchTitle")
	public void searchTitle(MusicDto dto, Model model) {
		log.info("GET /searchTitle");
		List<Music> list = musicService.getTitleSearchList(dto.getSearchText());

		List<Music> searchTitleList = list.stream().collect(Collectors.toList());

		model.addAttribute("searchTitleList", searchTitleList);

		System.out.println(searchTitleList);


	}

	@GetMapping("/searchArtist")
	public String searchArtist(MusicDto dto, Model model) {
		log.info("GET /searchArtist");
		List<Music> list = musicService.getArtistSearchList(dto.getSearchText());

		List<Music> searchArtistList = list.stream().collect(Collectors.toList());

		model.addAttribute("searchArtistList", searchArtistList);

		System.out.println(searchArtistList);

		return "search";

	}
}
