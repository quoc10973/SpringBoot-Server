package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");  // Thư mục chứa template
        templateResolver.setSuffix(".html");       // Đuôi file template
        templateResolver.setTemplateMode("HTML5"); // Chế độ template (HTML5)
        templateResolver.setCharacterEncoding("UTF-8"); // Mã hóa ký tự
        templateResolver.setCacheable(false);      // Không cache template trong quá trình phát triển
        return templateResolver;
    }

    @Bean
    public TemplateEngine templateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver()); // Sử dụng resolver ở trên
        return templateEngine;
    }
}
