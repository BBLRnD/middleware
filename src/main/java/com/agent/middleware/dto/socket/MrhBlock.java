package com.agent.middleware.dto.socket;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class MrhBlock implements BaseSocketObject {

    ///currently max 3;
    private Integer numberOfMrh;
    private List<MrhBlockDetails> mrhBlock1;
    private List<MrhBlockDetails> mrhBlock2;
    private List<MrhBlockDetails> mrhBlock3;

}
