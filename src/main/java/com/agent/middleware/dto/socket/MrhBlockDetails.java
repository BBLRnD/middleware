package com.agent.middleware.dto.socket;

import org.apache.tomcat.util.buf.StringUtils;

import java.util.List;


public class MrhBlockDetails {

    private String listName;
    private Integer numberOfRecs;
    private String[] headerInfo;
    private String message;
    private Integer curPageNum;
    private Integer maxPageNum;
    private List<String[]> dataBlock;

    public MrhBlockDetails() {
    }

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

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Integer getNumberOfRecs() {
        return numberOfRecs;
    }

    public void setNumberOfRecs(Integer numberOfRecs) {
        this.numberOfRecs = numberOfRecs;
    }

    public String[] getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(String[] headerInfo) {
        this.headerInfo = headerInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCurPageNum() {
        return curPageNum;
    }

    public void setCurPageNum(Integer curPageNum) {
        this.curPageNum = curPageNum;
    }

    public Integer getMaxPageNum() {
        return maxPageNum;
    }

    public void setMaxPageNum(Integer maxPageNum) {
        this.maxPageNum = maxPageNum;
    }

    public List<String[]> getDataBlock() {
        return dataBlock;
    }

    public void setDataBlock(List<String[]> dataBlock) {
        this.dataBlock = dataBlock;
    }
}
