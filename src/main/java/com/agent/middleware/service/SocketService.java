package com.agent.middleware.service;

import com.bbl.util.model.SocketPayload;

public interface SocketService {

    SocketPayload socketRequest(SocketPayload socketPayload);

}
