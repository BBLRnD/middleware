package com.agent.middleware.service;

import com.agent.middleware.socket.payloads.SocketPayload;

public interface SocketService {

   String socketPayloadObject(SocketPayload socketRequestPayload);

}
