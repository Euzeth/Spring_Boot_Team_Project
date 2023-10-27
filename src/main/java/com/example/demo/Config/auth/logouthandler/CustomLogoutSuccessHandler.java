package com.example.demo.Config.auth.logouthandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.Config.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.thymeleaf.util.StringUtils;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

private final String kakaoClientId = "34a860be2821a13fa1656cd331d2488d";
	private final String LOGOUT_REDIRECT_URI = "http://3.38.87.177:8080/member/login";

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		System.out.println("CustomLogoutSuccessHandler's onLogoutSuccess!");


		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		String provider = principalDetails.getMember().getProvider();
		if(provider!=null && StringUtils.contains(provider,"kakao"))
		{
			System.out.println("GET /th/kakao/logoutWithKakao");
			//URL
			String url = "https://kauth.kakao.com/oauth/logout?client_id="+kakaoClientId+"&logout_redirect_uri="+LOGOUT_REDIRECT_URI;
			response.sendRedirect(url);
			return ;
		}
		else if(provider!=null && StringUtils.contains(provider,"google"))
		{
			String url = "https://accounts.google.com/Logout";
			response.sendRedirect(url);
			return;

		}
		else if(provider!=null && StringUtils.contains(provider,"naver"))
		{
			String url = "http://nid.naver.com/nidlogin.logout";
			response.sendRedirect(url);
			return ;
		}



		response.sendRedirect("/member/login");
	}

}
