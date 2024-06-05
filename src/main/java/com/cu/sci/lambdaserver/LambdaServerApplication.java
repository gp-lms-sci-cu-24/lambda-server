package com.cu.sci.lambdaserver;

import com.cu.sci.lambdaserver.auth.properties.SecurityConfigurationProperties;
import com.cu.sci.lambdaserver.storage.cloudinary.config.CloudinaryConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableConfigurationProperties({SecurityConfigurationProperties.class, CloudinaryConfigProperties.class})
@EnableMethodSecurity
public class LambdaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LambdaServerApplication.class, args);
    }

}