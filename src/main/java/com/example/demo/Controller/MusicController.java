package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MusicController {
	
	@GetMapping("/song")
	public void song() {
		System.out.println("GET /song");
	}
}
