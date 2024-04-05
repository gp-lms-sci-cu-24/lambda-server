package com.cu.sci.lambdaserver.auth.config;

import com.cu.sci.lambdaserver.auth.web.ClientInfoHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AppConfiguration implements WebMvcConfigurer {

    private final ClientInfoHandlerInterceptor clientInfoHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clientInfoHandlerInterceptor);
    }


}
