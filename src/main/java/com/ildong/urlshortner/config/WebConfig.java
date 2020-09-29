package com.ildong.urlshortner.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
