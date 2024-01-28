package com.agent.middleware.service;

import com.agent.middleware.dto.socket.SocketPayload;

public interface SocketService {

    SocketPayload socketRequest(SocketPayload socketPayload);

}
