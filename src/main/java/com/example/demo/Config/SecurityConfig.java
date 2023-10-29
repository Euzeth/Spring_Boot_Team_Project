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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PrincipalDetailsOAuth2Service principalDetailsOAuth2Service;

	@Autowired
	private PrincipalDetailsService principalDetailsService;

	protected void configure(HttpSecurity http) throws Exception{

		http.csrf().disable();

		http
			.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**", "/font/**", "/audio/**").permitAll()
				.antMatchers("/", "/index", "/member/login", "/member/mypage", "/member/join", "/song", "/search").permitAll()
				.antMatchers("/inmylist", "/Top100", "/membership", "/main").permitAll()
				.antMatchers("/play", "/nextplay", "/previousplay", "/pause", "/stop", "/time").permitAll()
				.antMatchers("/qna/**","/notice/**").permitAll()
				.antMatchers("/musicinfo").permitAll()
				.anyRequest().authenticated()
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
			.accessDeniedHandler(new CustomAccessDeniedHandler())

				//REMEMBER ME
				.and()
				.rememberMe()
				.rememberMeParameter("remember-me")
				.tokenValiditySeconds(60*60)
				.alwaysRemember(false)
				.tokenRepository(tokenRepository())
				.userDetailsService(principalDetailsOAuth2Service)

				.and()
				//OAUTH2
				.oauth2Login()
				.userInfoEndpoint()
				.userService(principalDetailsOAuth2Service);

	}



	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(principalDetailsOAuth2Service)
			.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		//repo.setCreateTableOnStartup(true);
		return repo;
	}

}
