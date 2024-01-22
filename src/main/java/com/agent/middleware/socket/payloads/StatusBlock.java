package com.agent.middleware.socket.payloads;

import lombok.Data;

@Data
public class StatusBlock {
    private String responseCode;
    private String responseMessage;
}
