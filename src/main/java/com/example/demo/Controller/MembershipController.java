package com.example.demo.Controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MembershipController {
	private final String ADMIN_KEY = "ae39e2b27d5011a69f526b76f277ed62";

	@GetMapping("/membership")
	public void membership() {
		log.info("GET /membership");
	}
	
	@GetMapping("/membership/request1")
	public @ResponseBody membershipResponse pay1() {
		//url
		String url = "https://kapi.kakao.com/v1/payment/ready";
		
		//header
		HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK "+ ADMIN_KEY);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		
		//parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TCSUBSCRIP");
        params.add("partner_order_id", "subscription_order_id_1");
        params.add("partner_user_id", "subscription_user_id_1");
        params.add("item_name","WATERMELON 스트리밍 정기결제");
        params.add("quantity","1");
        params.add("total_amount", "6900");
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8080/app/membership/success");
        params.add("fail_url", "http://localhost:8080/app/membership/fail");
        params.add("cancel_url", "http://localhost:8080/app/membership/cancel");
		
        //HEADER + PARAMETER
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);


        //REQUEST
        RestTemplate rt = new RestTemplate();
        membershipResponse response = rt.postForObject(url, entity, membershipResponse.class);
        System.out.println(response);
		
		return response;
	}
	
	@GetMapping("/membership/request2")
	public @ResponseBody membershipResponse pay2() {
		//url
		String url = "https://kapi.kakao.com/v1/payment/ready";
		
		//header
		HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK "+ ADMIN_KEY);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		
		//parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TCSUBSCRIP");
        params.add("partner_order_id", "subscription_order_id_1");
        params.add("partner_user_id", "subscription_user_id_1");
        params.add("item_name","WATERMELON 스트리밍+오프라인 정기결제");
        params.add("quantity","1");
        params.add("total_amount", "9900");
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8080/app/membership/success");
        params.add("fail_url", "http://localhost:8080/app/membership/fail");
        params.add("cancel_url", "http://localhost:8080/app/membership/cancel");
		
        //HEADER + PARAMETER
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);


        //REQUEST
        RestTemplate rt = new RestTemplate();
        membershipResponse response = rt.postForObject(url, entity, membershipResponse.class);
        System.out.println(response);
		
		return response;
	}
	
	
		@GetMapping("/membership/success")
	 	public @ResponseBody String success() {
	        log.info("GET /membership/success");
	        return "Subscribe Success!";
	    }
	    @GetMapping("/membership/cancel")
	    public @ResponseBody String cancel() {
	        log.info("GET /membership/cancel");
	        return "Subscribe Cancel..";
	    }
	    @GetMapping("/membership/fail")
	    public @ResponseBody String fail() {
	        log.info("GET /membership/fail");
	        return "Subscribe Fail..";
	    }
	
}

@Data
class membershipResponse {
    private String tid;
    private boolean tms_result;
    private String next_redirect_app_url;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;
    private String android_app_scheme;
    private String ios_app_scheme;
    private String created_at;

}
