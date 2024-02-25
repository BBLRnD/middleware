package com.agent.middleware.config;


import com.agent.middleware.dto.UserSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserSessionConfig {
    @Bean
    public UserSession userSession() {
        return new UserSession();
    }
}
