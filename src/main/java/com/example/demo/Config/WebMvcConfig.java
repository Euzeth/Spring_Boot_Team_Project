package com.example.demo.Config;

import com.example.demo.Domain.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer{


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/* '/js/**'로 호출하는 자원은 '/static/js/' 폴더 아래에서 찾는다. */
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365);
		/* '/css/**'로 호출하는 자원은 '/static/css/' 폴더 아래에서 찾는다. */
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365);
		/* '/img/**'로 호출하는 자원은 '/static/img/' 폴더 아래에서 찾는다. */
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/").setCachePeriod(60 * 60 * 24 * 365);
		/* '/font/**'로 호출하는 자원은 '/static/font/' 폴더 아래에서 찾는다. */
		registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(60 * 60 * 24 * 365);

		registry.addResourceHandler("/audio/**").addResourceLocations("file:/music/").setCachePeriod(60 * 60 * 24 * 365);
	}

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**").allowedOrigins("http://localhost:8080").allowedMethods("GET");
		}
	
}
