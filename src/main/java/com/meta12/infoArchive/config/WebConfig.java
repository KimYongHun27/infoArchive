package com.meta12.infoArchive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 앞으로 저장할 권장 위치: 프로젝트루트/uploads
        Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads");
        String uploadUri = uploadPath.toUri().toString();

        // 현재 네 파일이 들어가 있는 위치: src/main/resources/static/uploads
        Path staticUploadPath = Paths.get(
                System.getProperty("user.dir"),
                "src", "main", "resources", "static", "uploads"
        );
        String staticUploadUri = staticUploadPath.toUri().toString();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadUri, staticUploadUri);
    }
}