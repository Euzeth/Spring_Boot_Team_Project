package com.example.demo.Controller;

import com.example.demo.Config.auth.PrincipalDetails;
import com.example.demo.Domain.Dto.QnADto;
import com.example.demo.Domain.Dto.Criteria;
import com.example.demo.Domain.Dto.PageDto;
import com.example.demo.Domain.Entity.QnA;
import com.example.demo.Domain.Service.QnAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/qna")
@Slf4j
public class QnAController {

    @Autowired
    private QnAService qnaService;

    public static String READ_DIRECTORY_PATH ;

    @GetMapping("/user/info")
    @ResponseBody
    public String userInfo(Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        String username = principalDetails.getUsername();
        return username;
    }

    //-------------------
    //-------------------
    @GetMapping("/list")
    public String list(Integer pageNo,String type, String keyword, Model model, HttpServletResponse response, HttpSession session)
    {
        log.info("GET /qna/list... " + pageNo + " " + type +" " + keyword);

        //----------------
        //PageDto  Start
        //----------------
        Criteria criteria = null;
        if(pageNo==null) {
            //최초 /qna/list 접근
            pageNo=1;
            criteria = new Criteria();  //pageno=1 , amount=10
        }
        else {
            criteria = new Criteria(pageNo,10); //페이지이동 요청 했을때
        }
        //--------------------
        //Search
        //--------------------
        if(type!=null)
            criteria.setType(type);
        if(keyword!=null)
            criteria.setKeyword(keyword);


        //서비스 실행
        Map<String,Object> map = qnaService.GetQnAList(criteria);

        PageDto pageDto = (PageDto) map.get("pageDto");
        List<QnA> list = (List<QnA>) map.get("list");


        //Entity -> Dto
        List<QnADto> qnaList =  list.stream().map(qna -> QnADto.Of(qna)).collect(Collectors.toList());
        System.out.println(qnaList);

        //View 전달
        model.addAttribute("qnaList",qnaList);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("pageDto",pageDto);

        //--------------------------------
        //COUNT UP - //쿠키 생성(/qna/read.do 새로고침시 조회수 반복증가를 막기위한용도)
        //--------------------------------
        Cookie init = new Cookie("isRead","false");
        response.addCookie(init);


        String role = (String)session.getAttribute("role");
        if (role == null) {
            System.out.println("Role is null");
            return "qna/list";
        }
        if(role.equals("ROLE_USER")){
            model.addAttribute("role", "USER");
        } else if(role.equals("ROLE_MEMBER")){
            model.addAttribute("role", "MEMBER");
        }
        System.out.println(role);


        return "qna/list";
    }


    //-------------------
    // POST
    //-------------------
    @GetMapping("/post")
    public void get_addQnA(){
        log.info("GET /qna/post");
    }

    @PostMapping("/post")
    public String post_addQnA(@Valid QnADto dto, BindingResult bindingResult, Model model) throws IOException {
        log.info("POST /qna/post " + dto + " " + dto);

        //유효성 검사
        if(bindingResult.hasFieldErrors()) {
            for( FieldError error  : bindingResult.getFieldErrors()) {
                log.info(error.getField()+ " : " + error.getDefaultMessage());
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            return "qna/post";
        }
        //서비스 실행
        boolean isadded = qnaService.addQnA(dto);

        return "redirect:/qna/list";

    }

    //-------------------
    // READ
    //-------------------

    @GetMapping("/read")
    public String read(Long no, Integer pageNo, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        log.info("GET /qna/read : " + no);

        //서비스 실행
        QnA qna =  qnaService.getQnAOne(no);

        QnADto dto = new QnADto();
        dto.setNo(qna.getNo());
        dto.setTitle(qna.getTitle());
        dto.setContent(qna.getContent());
        dto.setRegdate(qna.getRegdate());
        dto.setUsername(qna.getUsername());
        dto.setCount(qna.getCount());

        System.out.println("FILENAMES : " + qna.getFilename());
        System.out.println("FILESIZES : " + qna.getFilesize());

        String filenames[] = null;
        String filesizes[] = null;
        if(qna.getFilename()!=null){
            //첫문자열에 [ 제거
            filenames = qna.getFilename().split(",");
            filenames[0] = filenames[0].substring(1, filenames[0].length());
            //마지막 문자열에 ] 제거
            int lastIdx = filenames.length-1;
            System.out.println("filenames[lastIdx] : " + filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]")));
            filenames[lastIdx] = filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]"));

            model.addAttribute("filenames", filenames);
        }
        if(qna.getFilesize()!=null){
            //첫문자열에 [ 제거
            filesizes = qna.getFilesize().split(",");
            filesizes[0] = filesizes[0].substring(1, filesizes[0].length());
            //마지막 문자열에 ] 제거
            int lastIdx = filesizes.length-1;
            System.out.println("filesizes[lastIdx] : " + filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]")));
            filesizes[lastIdx] = filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]"));



            model.addAttribute("filesizes", filesizes);
        }


        if(qna.getDirpath()!=null)
            READ_DIRECTORY_PATH = qna.getDirpath();

        //--------------------------------------------------------
        //COUNT UP
        //--------------------------------------------------------
        Cookie[] cookies = request.getCookies();
        if(cookies!=null)
        {
            for(Cookie cookie : cookies)
            {
                if(cookie.getName().equals("isRead"))
                {
                    if(cookie.getValue().equals("false"))
                    {
                        System.out.println("COOKIE READING TRUE | COUNT UP");
                        qnaService.countUp(qna);
                        cookie.setValue("true");

                        response.addCookie(cookie);
                    }
                }
            }
        }
        model.addAttribute("qnaDto",dto);

        String role = (String)session.getAttribute("role");
        if(role.equals("ROLE_USER")){
            model.addAttribute("role", "USER");
        } else if(role.equals("ROLE_MEMBER")){
            model.addAttribute("role", "MEMBER");
        }
        System.out.println(role);


        return "qna/read";
    }

    @GetMapping("/update")
    public void update(Long no,Model model){
        log.info("GET /qna/update no " + no);


        //서비스 실행
        QnA qna =  qnaService.getQnAOne(no);

        QnADto dto = new QnADto();
        dto.setNo(qna.getNo());
        dto.setTitle(qna.getTitle());
        dto.setContent(qna.getContent());
        dto.setRegdate(qna.getRegdate());
        dto.setUsername(qna.getUsername());
        dto.setCount(qna.getCount());
        log.info("GET /qna/update dto : " + dto);

        System.out.println("FILENAMES : " + qna.getFilename());
        System.out.println("FILESIZES : " + qna.getFilesize());

        String filenames[] = null;
        String filesizes[] = null;
        if(qna.getFilename()!=null){
            //첫문자열에 [ 제거
            filenames = qna.getFilename().split(",");
            filenames[0] = filenames[0].substring(1, filenames[0].length());
            //마지막 문자열에 ] 제거
            int lastIdx = filenames.length-1;
            System.out.println("filenames[lastIdx] : " + filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]")));
            filenames[lastIdx] = filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]"));

            model.addAttribute("filenames", filenames);
        }
        if(qna.getFilesize()!=null){
            //첫문자열에 [ 제거
            filesizes = qna.getFilesize().split(",");
            filesizes[0] = filesizes[0].substring(1, filesizes[0].length());
            //마지막 문자열에 ] 제거
            int lastIdx = filesizes.length-1;
            System.out.println("filesizes[lastIdx] : " + filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]")));
            filesizes[lastIdx] = filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]"));



            model.addAttribute("filesizes", filesizes);
        }


        if(qna.getDirpath()!=null)
            READ_DIRECTORY_PATH = qna.getDirpath();

        model.addAttribute("qnaDto",dto);

    }

    @PostMapping("/update")
    public String Post_update(@Valid QnADto dto, BindingResult bindingResult, Model model) throws IOException {
        log.info("POST /qna/update dto " + dto);

        if(bindingResult.hasFieldErrors()) {
            for( FieldError error  : bindingResult.getFieldErrors()) {
                log.info(error.getField()+ " : " + error.getDefaultMessage());
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            return "redirect:/qna/update?no="+dto.getNo();
        }
        //서비스 실행
        boolean isUpdate = qnaService.updateQnA(dto);

        if(isUpdate)
            return "redirect:/qna/read?no="+dto.getNo();

        return "redirect:/qna/update?no="+dto.getNo();

    }

    //--------------------------------
    // /qna/reply/delete
    //--------------------------------
    @GetMapping("/reply/delete/{qno}/{rno}")
    public String delete(@PathVariable Long qno, @PathVariable Long rno){
        log.info("GET /qna/reply/delete qno,rno " + qno + " " + rno);

        qnaService.deleteReply(rno);

        return "redirect:/qna/read?no="+qno;
    }

}