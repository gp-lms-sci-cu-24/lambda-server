package com.cu.sci.lambdaserver.storage.cloudinary.config;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    private final CloudinaryConfigProperties cloudinaryConfigProperties;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> configuration = cloudinaryConfigProperties.toMap();

        return new Cloudinary(configuration);
    }

}
