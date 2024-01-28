package com.agent.middleware.socket.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class MrhBlock implements BaseSocketObject{

    ///currently max 3;
    private Integer numberOfMrh;
    private List<MrhBlockDetails> mrhBlocks;

}
