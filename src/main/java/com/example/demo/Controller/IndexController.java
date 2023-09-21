package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
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
	public void f3() {
		log.info("GET /indexlog");
	}
	
	@PostMapping("/indexlog")
	public void f4() {
		log.info("POST /indexlog");
	}

}