package com.example.demo.Controller;

import com.example.demo.Domain.Dto.Criteria;
import com.example.demo.Domain.Dto.NoticeDto;
import com.example.demo.Domain.Dto.PageDto;
import com.example.demo.Domain.Entity.Notice;
import com.example.demo.Domain.Service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/notice")
@Slf4j
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    public static String READ_NOTICE_DIR_PATH;


    //-------------------
    //-------------------

    @GetMapping("/list")
    public String list(Integer pageNo, String type, String keyword, Model model, HttpServletResponse response, HttpSession session){
        log.info("GET /notice/list no,type,keyword : " + pageNo+"|"+type+"|"+keyword);
        //----------------
        //PageDto  Start
        //----------------
        Criteria criteria = null;
        if(pageNo==null) {
            //최초 /notice/list 접근
            criteria = new Criteria();  //pageno=1 , amount=10
            pageNo=1;
        }
        else {
            criteria = new Criteria(pageNo,10); //페이지이동 요청 했을때
        }
        //----------------
        //Search
        //----------------
        if(type!=null)
            criteria.setType(type);
        if(keyword!=null)
            criteria.setKeyword(keyword);

        //서비스 실행
        Map<String,Object> map = noticeService.GetNoticeList(criteria);

        PageDto pageDto = (PageDto) map.get("pageDto");
        List<Notice> list = (List<Notice>) map.get("list");


        //Entity -> Dto
        List<NoticeDto>  noticeList =  list.stream().map(notice -> NoticeDto.Of(notice)).collect(Collectors.toList());
        System.out.println(noticeList);

        //View 전달
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("pageDto",pageDto);

        //--------------------------------
        //COUNT UP COOKIES
        //--------------------------------
        Cookie init = new Cookie("isRead","false");
        response.addCookie(init);

        String role = (String)session.getAttribute("role");
        if (role == null) {
            System.out.println("Role is null");
            return "notice/list";
        }
        if(role.equals("ROLE_USER")){
            model.addAttribute("role", "USER");
        } else if(role.equals("ROLE_MEMBER")){
            model.addAttribute("role", "MEMBER");
        }
        System.out.println(role);


        return "notice/list";

    }

    //-------------------
    // POST
    //-------------------

    @GetMapping("/post")
    public void post_get(){
        log.info("GET /notice/post");
    }

    @PostMapping("/post")
    public String post_post(@Valid NoticeDto dto, BindingResult bindingResult, Model model) throws IOException {
        log.info("POST /notice/post dto : " + dto);

        //유효성 검사
        if(bindingResult.hasFieldErrors()) {
            for( FieldError error  : bindingResult.getFieldErrors()) {
                log.info(error.getField()+ " : " + error.getDefaultMessage());
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            return "notice/post";
        }
        //서비스 실행
        boolean isadded = noticeService.addNotice(dto);

        return "redirect:/notice/list";

    }

    //-------------------
    //-------------------
    @GetMapping("/read")
    public String read(Long no, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        log.info("GET /notice/read no : " + no );

        //서비스 실행
        Notice notice =  noticeService.getNoticeOne(no);

        NoticeDto dto = new NoticeDto();
        dto.setNo(notice.getNo());
        dto.setCategory(notice.getCategory());
        dto.setTitle(notice.getTitle());
        dto.setContent(notice.getContent());
        dto.setRegdate(notice.getRegdate());

        System.out.println("FILENAMES : " + notice.getFilename());   // "[a.txt,b.txt,c.txt]"
        System.out.println("FILESIZES : " + notice.getFilesize());

        String filenames[] = null;
        String filesizes[] = null;
        if(notice.getFilename()!=null){
            //첫문자열에 [ 제거
            filenames = notice.getFilename().split(",");     // [a.txt , b.txt , c.txt]
            filenames[0] = filenames[0].substring(1, filenames[0].length());
            //마지막 문자열에 ] 제거
            int lastIdx = filenames.length-1;
            System.out.println("filenames[lastIdx] : " + filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]")));
            filenames[lastIdx] = filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]"));


            model.addAttribute("filenames", filenames);
        }
        if(notice.getFilesize()!=null){
            //첫문자열에 [ 제거
            filesizes = notice.getFilesize().split(",");
            filesizes[0] = filesizes[0].substring(1, filesizes[0].length());
            //마지막 문자열에 ] 제거
            int lastIdx = filesizes.length-1;
            System.out.println("filesizes[lastIdx] : " + filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]")));
            filesizes[lastIdx] = filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]"));

            model.addAttribute("filesizes", filesizes);
        }
        if(notice.getDirpath()!=null)
            READ_NOTICE_DIR_PATH = notice.getDirpath();

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
                        noticeService.countUp(notice);
                        cookie.setValue("true");

                        response.addCookie(cookie);
                    }
                }
            }
        }
        model.addAttribute("noticeDto",dto);

        String role = (String)session.getAttribute("role");
        if (role == null) {
            System.out.println("Role is null");
            return "notice/read";
        }
        if(role.equals("ROLE_USER")){
            model.addAttribute("role", "USER");
        } else if(role.equals("ROLE_MEMBER")){
            model.addAttribute("role", "MEMBER");
        }
        System.out.println(role);


        return "notice/read";
    }

    //-------------------
    //-------------------
    @GetMapping("/update")
    public void update(Long no,Model model){

        log.info("GET /notice/update");

        //서비스 실행
        Notice notice =  noticeService.getNoticeOne(no);

        NoticeDto dto = new NoticeDto();
        dto.setNo(notice.getNo());
        dto.setCategory(notice.getCategory());
        dto.setTitle(notice.getTitle());
        dto.setContent(notice.getContent());
        dto.setRegdate(notice.getRegdate());
        dto.setCount(notice.getCount());
        log.info("GET /notice/update dto : " + dto);

        System.out.println("FILENAMES : " + notice.getFilename());   // "[a.txt,b.txt,c.txt]"
        System.out.println("FILESIZES : " + notice.getFilesize());

        String filenames[] = null;
        String filesizes[] = null;
        if(notice.getFilename()!=null){
            //첫문자열에 [ 제거
            filenames = notice.getFilename().split(",");     // [a.txt , b.txt , c.txt]
            filenames[0] = filenames[0].substring(1, filenames[0].length());
            //마지막 문자열에 ] 제거
            int lastIdx = filenames.length-1;
            System.out.println("filenames[lastIdx] : " + filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]")));
            filenames[lastIdx] = filenames[lastIdx].substring(0,filenames[lastIdx].lastIndexOf("]"));

            model.addAttribute("filenames", filenames);
        }
        if(notice.getFilesize()!=null){
            //첫문자열에 [ 제거
            filesizes = notice.getFilesize().split(",");
            filesizes[0] = filesizes[0].substring(1, filesizes[0].length());
            //마지막 문자열에 ] 제거
            int lastIdx = filesizes.length-1;
            System.out.println("filesizes[lastIdx] : " + filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]")));
            filesizes[lastIdx] = filesizes[lastIdx].substring(0,filesizes[lastIdx].lastIndexOf("]"));

            model.addAttribute("filesizes", filesizes);
        }

        if(notice.getDirpath()!=null)
            READ_NOTICE_DIR_PATH = notice.getDirpath();

        model.addAttribute("noticeDto",dto);

    }

    @PostMapping("/update")
    public String update_post(@Valid NoticeDto dto, BindingResult bindingResult,Model model) throws IOException {
        System.out.println("POST /notice/update dto : " + dto);
        //유효성 검사
        if(bindingResult.hasFieldErrors()) {
            for( FieldError error  : bindingResult.getFieldErrors()) {
                log.info(error.getField()+ " : " + error.getDefaultMessage());
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            return "redirect:/notice/update?no="+dto.getNo();
        }
        //서비스 실행
        boolean isUpdate = noticeService.updateNotice(dto);

        if(isUpdate)
            return "redirect:/notice/read?no="+dto.getNo();

        return "redirect:/notice/update?no="+dto.getNo();

    }

}