package com.cu.sci.lambdaserver.course;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Configuration

public class CourseConfig {
    @Bean
    CommandLineRunner commandLineRunner(CourseRepository courseRepository){
        return args -> {
            Course c=
                    Course.builder()
                    .code("M494")
                    .department("Mathematics")
                    .name("Number theory 2")
                    .build();
            Course c1=
                    Course.builder()
                            .code("M493")
                            .department("Mathematics")
                            .name("Number theory 1")
                            .build();
            courseRepository.saveAll(List.of(c,c1));
        };
    }
}
