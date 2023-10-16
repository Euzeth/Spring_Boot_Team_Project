package com.example.demo.Controller;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Entity.Membership;
import com.example.demo.Domain.Entity.Mylist;
import com.example.demo.Domain.Service.MylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

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
        return "redirect:/member/login";
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
    public void mylist(Authentication authentication, Model model){
        log.info("GET /mylist");
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        String username = principalDetails.getUsername();

        List<Mylist> list = mylistService.GetMylistByUsername(username);
        List<Mylist> MyList = list.stream().collect(Collectors.toList());

        model.addAttribute("MyList", MyList);

        System.out.println(MyList);

    }



}
