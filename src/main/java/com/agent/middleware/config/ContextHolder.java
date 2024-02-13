package com.agent.middleware.config;

import com.bbl.util.model.DeviceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextHolder {

    @Bean
    DeviceInfo getInstance(){
        return DeviceInfo.getInstance();
    }
}
