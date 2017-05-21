package com.forum.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@PropertySource({"file:${user.dir}/config/application.properties", "file:${user.dir}/config/web.properties"})
@ComponentScan(basePackages = { "com.szw.srm.controller" })
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private Environment env;

	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		try{
			String clientRoot = env.getProperty("staticResourcesPath")
					.endsWith("/") ? env.getProperty("staticResourcesPath")
					: env.getProperty("staticResourcesPath").concat("/");
			System.out.println("clientRoot: " + clientRoot);
			registry.addResourceHandler("/"+clientRoot+"**").addResourceLocations(
					"file:" + clientRoot);
			
			registry.addResourceHandler("/plugins/**").addResourceLocations("classpath:/static/plugins/");
			registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");			
			registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
			registry.addResourceHandler("/build/**").addResourceLocations("classpath:/static/build/");
//			registry.addResourceHandler("/images/**").addResourceLocations("file:" + env.getProperty("imagesStaticResourcesPath"));
			registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
			registry.addResourceHandler("/views/**").addResourceLocations("classpath:/templates/views/");
			
		}catch(Exception e){
			
			System.out.println("staticResourcesPath is not in web.properties");
		}
	}
}