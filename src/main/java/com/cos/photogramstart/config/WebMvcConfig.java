package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {  // web 설정 파일이 됨 (implements WebMvcConfigurer)
	
	@Value("${file.path}")
	private String uploadFolder;  //  C:/workspace/springbootwork/upload/
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			
			registry
				.addResourceHandler("/upload/**") // jsp 페이지에서 /upload/** 이런주소 패턴 나오면
				.addResourceLocations("file:///" + uploadFolder) // file:///C:/workspace/springbootwork/upload/ 얘로 바꿔줌
				.setCachePeriod(60*10*6) // 1 시간
				.resourceChain(true)
				.addResolver(new PathResourceResolver()); // 등록됨
		}

}
