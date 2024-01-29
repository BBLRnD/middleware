package com.agent.middleware.dto.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.buf.StringUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MrhBlockDetails {

    private String listName;
    private Integer numberOfRecs;
    private String[] headerInfo;
    private String message;
    private Integer curPageNum;
    private Integer maxPageNum;
    private List<String[]> dataBlock;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");
        if (listName != null) {
            s.append("[listName=").append(listName).append("]").append("[numberOfRecs=").append(numberOfRecs).append("]")
                    .append("[headerInfo=").append(StringUtils.join(List.of(headerInfo), '|')).append("]")
                    .append("[message=").append(message).append("]").append("[curPageNum=").append(curPageNum).append("]")
                    .append("[maxPageNum=").append(maxPageNum).append("]").append("[dataBlocks=");
            for (int i = 0; i < numberOfRecs; i++) {
                s.append(StringUtils.join(List.of(dataBlock.get(i)), '|'));
                if (i < numberOfRecs - 1)
                    s.append("~");
            }
            s.append("]");
        }
        return s.toString();
    }
}
