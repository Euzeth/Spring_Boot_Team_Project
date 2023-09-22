package com.example.demo.Config;

import com.example.demo.Config.auth.*;
import com.example.demo.Config.auth.exceptionhandler.CustomAccessDeniedHandler;
import com.example.demo.Config.auth.exceptionhandler.CustomAuthenticationEntryPoint;
import com.example.demo.Config.auth.loginhandler.CustomAuthenticationFailureHandler;
import com.example.demo.Config.auth.loginhandler.CustomLoginSuccessHandler;
import com.example.demo.Config.auth.logouthandler.CustomLogoutHandler;
import com.example.demo.Config.auth.logouthandler.CustomLogoutSuccessHandler;
import com.example.demo.Config.auth.logouthandler.OAuthLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{


	protected void configure(HttpSecurity http) throws Exception{

		http.csrf().disable();

		http
			.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**", "/font/**").permitAll()
				.antMatchers("/", "/index", "/member/**", "/song").permitAll()
//				.anyRequest().authenticated()
			.and()

			.formLogin()
			.loginPage("/member/login")
			.successHandler(new CustomLoginSuccessHandler())
			.failureHandler(new CustomAuthenticationFailureHandler())

			.and()
			.logout()
			.logoutUrl("/logout")
			.permitAll()
				//.addLogoutHandler(new CustomLogoutHandler())
				.addLogoutHandler(new OAuthLogoutHandler())
			.logoutSuccessHandler(new CustomLogoutSuccessHandler())

			.and()
			.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			.accessDeniedHandler(new CustomAccessDeniedHandler());

	}

	@Autowired
	private PrincipalDetailsService principalDetailsService;

	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(principalDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
