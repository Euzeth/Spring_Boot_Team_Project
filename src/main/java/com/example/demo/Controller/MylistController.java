package com.example.demo.Controller;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Entity.Membership;
import com.example.demo.Domain.Entity.Mylist;
import com.example.demo.Domain.Entity.MylistId;
import com.example.demo.Domain.Service.MylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class MylistController {
    @Autowired
    private MylistService mylistService;

    private String MylistRequest(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null) {
            System.out.println("Role is null, redirecting to /member/login");
            return "redirect:/member/login";
        }

        if (role.equals("ROLE_USER") || role.equals("ROLE_MEMBER")) {
            System.out.println("mylist enter..");
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


    //view에 username정보 보내기
    @GetMapping("/user/info")
    @ResponseBody
    public String userInfo(Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        String username = principalDetails.getUsername();
        return username;
    }


    //MylistDB에 들어갈 request정의
    public static class MylistRequest {
        private List<Long> musicCodes;
        private String username;

        public List<Long> getMusicCodes() {
            return musicCodes;
        }

        public void setMusicCodes(List<Long> musicCodes) {
            this.musicCodes = musicCodes;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    //MylistDB에 ADD
    @PostMapping("/search/addlist")
    public String addMylist(@RequestBody MylistRequest request) {
        List<Long> musicCodes = request.getMusicCodes();
        String username = request.getUsername();

        mylistService.addMylist(musicCodes, username);

        return "search";
    }

    //Mylist 페이지 진입 시 유저별 MylistDB GET
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


    //MylistDB에서 DELETE
    @PostMapping("/mylist/delete")
    public String mylistDelete(@RequestBody Map<String, Object> requestData){
        log.info("GET /mylist/delete");
        List<String> musicCodesStringList = (List<String>) requestData.get("musicCodes");
        List<Long> musicCodes = musicCodesStringList.stream().map(Long::valueOf).collect(Collectors.toList());
        String username = (String) requestData.get("username");

        mylistService.removeMylistById(musicCodes, username);
        return "mylist";
    }

}
