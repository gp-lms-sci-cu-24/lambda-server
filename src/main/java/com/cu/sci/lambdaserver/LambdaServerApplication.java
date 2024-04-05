package com.cu.sci.lambdaserver;

import com.cu.sci.lambdaserver.auth.properties.SecurityConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
public class LambdaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LambdaServerApplication.class, args);
    }

}