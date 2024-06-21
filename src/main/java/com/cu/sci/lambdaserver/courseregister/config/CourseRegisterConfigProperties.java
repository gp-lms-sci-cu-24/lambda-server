package com.cu.sci.lambdaserver.courseregister.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.course.register.ttl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegisterConfigProperties {
    private int seconds = 40;
}

