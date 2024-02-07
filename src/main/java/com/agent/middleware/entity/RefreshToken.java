package com.agent.middleware.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RefreshToken {
    private int id;
    private String token;
    private Instant expiryDate;
    private Long userId;
}
