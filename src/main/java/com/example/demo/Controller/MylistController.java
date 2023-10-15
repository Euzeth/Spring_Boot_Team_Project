package com.example.demo.Controller;

import com.example.demo.Domain.Service.MylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MylistController {
    @Autowired
    private MylistService mylistService;

    private String MylistRequest(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role.equals("ROLE_USER") || role.equals("ROLE_MEMBER")) {
            System.out.println("user's mylist");
            return "redirect:/mylist";
        }
        return "redirect:/indexlog";
    }
    @GetMapping("/inmylist")
    public String mylist(HttpSession session, Authentication authentication){
        System.out.println("authentication: " + authentication);
        return MylistRequest(session);
    }

    @PostMapping("/inmylist")
    public String mylist(HttpSession session){
        return MylistRequest(session);
    }

    @GetMapping("/mylist")
    public void mylist(){
        log.info("GET /mylist");

    }



}
