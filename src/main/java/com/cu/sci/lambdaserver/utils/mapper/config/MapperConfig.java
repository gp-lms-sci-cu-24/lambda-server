package com.cu.sci.lambdaserver.utils.mapper.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
//        modelMapper.getConfiguration().setSkipNullEnabled(false);
        return modelMapper;
    }
}
