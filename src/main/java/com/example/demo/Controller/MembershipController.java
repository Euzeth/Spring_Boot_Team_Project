package com.example.demo.Controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.demo.Domain.Dto.MembershipDto;
import com.example.demo.Domain.Entity.Membership;
import com.example.demo.Domain.Service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MembershipController {

    @Autowired
    MembershipService membershipService;

    private final String ADMIN_KEY = "ae39e2b27d5011a69f526b76f277ed62";

    private String MembershipRequest(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role.equals("ROLE_USER")) {
            System.out.println("user's Membership");
            return "redirect:/membershipU";
        } else if (role.equals("ROLE_MEMBER")) {
            System.out.println("member's Membership");
            return "redirect:/membershipM";
        }
        return "indexlog";
    }
  @GetMapping("/membership")
    public String membership(HttpSession session, Authentication authentication){
        System.out.println("Authentication : " + authentication);
      return MembershipRequest(session);
  }

  @PostMapping("/membership")
    public String membership_post(HttpSession session){
        return MembershipRequest(session);
  }

    @GetMapping("/membershipU")
    public void membership_U() {
        System.out.println("GET /membershipU");
    }

    @GetMapping("/membershipM")
    public void membership_M(Model model) {
        System.out.println("GET /membershipM");

/*        log.info("GET /memberhsipM");
        List<MembershipDto> list = membershipService.getMembershipList();

        List<MembershipDto> membershipDtoList = list.stream().collect(Collectors.toList());

        model.addAttribute("selectedAllList", membershipDtoList);

        System.out.println(membershipDtoList);*/
    }

    // 유저 멤버십페이지에서 WM_1 결제
    @GetMapping("/membership/request1")
    public @ResponseBody MembershipResponse pay1() {
        // url
        String url = "https://kapi.kakao.com/v1/payment/ready";

        // header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        // parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TCSUBSCRIP");
        params.add("partner_order_id", "subscription_order_id_1");
        params.add("partner_user_id", "subscription_user_id_1");
        params.add("item_name", "WATERMELON 스트리밍 정기결제");
        params.add("quantity", "1");
        params.add("total_amount", "6900");
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8080/membership/success1");
        params.add("fail_url", "http://localhost:8080/membership/fail");
        params.add("cancel_url", "http://localhost:8080/membership/cancel");

        // HEADER + PARAMETER
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        // REQUEST
        RestTemplate rt = new RestTemplate();
        MembershipResponse response = rt.postForObject(url, entity, MembershipResponse.class);
        System.out.println(response);

        return response;
    }

    // 유저 멤버십페이지에서 WM_2 결제
    @GetMapping("/membership/request2")
    public @ResponseBody MembershipResponse pay2() {
        // url
        String url = "https://kapi.kakao.com/v1/payment/ready";

        // header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        // parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TCSUBSCRIP");
        params.add("partner_order_id", "subscription_order_id_1");
        params.add("partner_user_id", "subscription_user_id_1");
        params.add("item_name", "WATERMELON 스트리밍+오프라인 정기결제");
        params.add("quantity", "1");
        params.add("total_amount", "9900");
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8080/membership/success2");
        params.add("fail_url", "http://localhost:8080/membership/fail");
        params.add("cancel_url", "http://localhost:8080/membership/cancel");

        // HEADER + PARAMETER
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        // REQUEST
        RestTemplate rt = new RestTemplate();
        MembershipResponse response = rt.postForObject(url, entity, MembershipResponse.class);
        System.out.println(response);

        return response;
    }

    /// 유저 멤버십페이지에서 WM_1 결제 성공시 매핑
    @GetMapping("/membership/success1")
    public @ResponseBody String success1(MembershipDto dto, Authentication authentication, HttpServletRequest request) throws IOException {
        log.info("GET /membership/success1");

        // 멤버십DB에 정보 ADD
        membershipService.addMembership(dto, authentication, request);
        return "Subscribe Success!";
    }

    // 유저 멤버십페이지에서 WM_2 결제 성공시 매핑
    @GetMapping("/membership/success2")
    public @ResponseBody String success2(MembershipDto dto, Authentication authentication, HttpServletRequest request) throws IOException {
        log.info("GET /membership/success2");
        // 멤버십DB에 정보 ADD
        membershipService.addMembership(dto, authentication, request);
        return "Subscribe Success!";
    }

    // 유저 멤버십페이지에서 결제 취소 매핑
    @GetMapping("/membership/cancel")
    public @ResponseBody String cancel() {
        log.info("GET /membership/cancel");
        return "Subscribe Cancel..";
    }

    // 유저 멤버십페이지에서 결제 실패 매핑
    @GetMapping("/membership/fail")
    public @ResponseBody String fail() {
        log.info("GET /membership/fail");
        return "Subscribe Fail..";
    }

    @GetMapping("/membership/selectAll")
    public String membership_selectAll(Model model) {
        log.info("GET /memberhsipM");
        List<MembershipDto> list = membershipService.getMembershipList();

        List<MembershipDto> membershipDtoList = list.stream().collect(Collectors.toList());

        model.addAttribute("selectedAllList", membershipDtoList);

        System.out.println(membershipDtoList);

        return "membershipM";
    }

    // membership 개별 유저 조회 관련 매핑(ID)
    @GetMapping("/membership/selectId")
    public String membership_selectId(@RequestParam String username, Model model) {
        log.info("GET /membership_M select ID");
        Membership selectedId = membershipService.getMembershipOne(username);
        model.addAttribute("selectedId", selectedId);
        System.out.println(selectedId);

        return "membershipM";
    }

    // membership 개별 유저 조회 관련 매핑(CODE)
    @GetMapping("/membership/selectCode")
    public String membership_selectCode(@RequestParam String membershipcode, Model model) {
        log.info("GET /membership_M select CODE");
        List<MembershipDto> list = membershipService.getMembershipCode(membershipcode);

        List<MembershipDto> selectedCodeList = list.stream().collect(Collectors.toList());

        model.addAttribute("selectedCodeList", selectedCodeList);

        System.out.println(selectedCodeList);

        return "membershipM";
    }

    // membership 개별 유저 조회 관련 매핑(종료일자)

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        System.out.println("MembershipController's dataBinder.. " + dataBinder);
        // String("2022-01-01") -> LocalDate로 변환 Editor
        dataBinder.registerCustomEditor(LocalDate.class, "endDate", new MembershipDtoEditor());

    }

    @GetMapping("/membership/selectDate")
    public String membership_selectDate(@Valid @ModelAttribute MembershipDto dto, BindingResult bindingResult, Model model) {
        log.info("GET /membership_M select EndDate");
        if (bindingResult.hasFieldErrors()) {

            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            System.out.println("ERROR 발생!!");
            return "membershipM";
        }
        List<MembershipDto> list = membershipService.getMembershipDate(dto.getEnddate());
        List<MembershipDto> selectedDateList = list.stream().collect(Collectors.toList());

        model.addAttribute("selectedDateList", selectedDateList);

        System.out.println(selectedDateList);

        return "membershipM";
    }

    // membership 회원 삭제 관련 매핑
    @GetMapping("/membership/delete")
    public void membership_delete(@RequestParam String username) {
        log.info("GET /membership_M delete");
    }

    @PostMapping("/membership/delete")
    public String membership_delete_post(@RequestParam String username) {
        log.info("POST /membership_M delete");
        membershipService.removeMembership(username);
        return "redirect:/membershipM";
    }



}

@Data
class MembershipResponse {
    private String tid;
    private boolean tms_result;
    private String next_redirect_app_url;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;
    private String android_app_scheme;
    private String ios_app_scheme;
    private String created_at;
}

class MembershipDtoEditor extends PropertyEditorSupport {
    // String->Object
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        System.out.println("MemberDtoEditor's setAsText : " + text);
        setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}