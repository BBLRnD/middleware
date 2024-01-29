package com.agent.middleware.dto.socket;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.buf.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class ListBlock{
    private Integer numberOfRecs;
    private String[] headerInfo;
    private String message;
    private Integer curPageNum;
    private Integer maxPageNum;
    private List<String[]> dataBlock;

    public ListBlock(String listBlockStr, SocketPayload socketPayload){
        socketPayload.setListBlock(listBlock(listBlockStr));
    }

    private String extractValue(String input, String key) {
        String patternString = "\\[" + key + "=(.*?)\\]";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null; // Handle the case where the key is not found
        }
    }
    private ListBlock listBlock(String listBlockString){
        ListBlock listBlock = new ListBlock();
        listBlock.setNumberOfRecs(Integer.parseInt(Objects.requireNonNull(extractValue(listBlockString, "numberOfRecs")).replaceAll("]\\[","")));
        String headerInfos = Objects.requireNonNull(extractValue(listBlockString, "headerInfo")).replace("\\]\\[","");
        listBlock.setHeaderInfo(headerInfos.split("\\|"));
        listBlock.setMessage(Objects.requireNonNull(extractValue(listBlockString, "message")).replaceAll("\\]\\[",""));
        listBlock.setCurPageNum(Integer.parseInt(Objects.requireNonNull(extractValue(listBlockString, "curPageNum")).replaceAll("\\]\\[","")));
        listBlock.setMaxPageNum(Integer.parseInt(Objects.requireNonNull(extractValue(listBlockString, "maxPageNum")).replaceAll("\\]\\[","")));

        String dataBlocks = Objects.requireNonNull(extractValue(listBlockString, "dataBlocks")).replace("\\]\\[","");
        String[] dataBlockList = dataBlocks.split("\\~");
        List<String[]> finalBlocks = new ArrayList<>();
        for(int i=0; i< dataBlockList.length; i++){
            String[] dataBlockData = dataBlockList[i].split("\\|");
            finalBlocks.add(dataBlockData);
        }
        listBlock.setDataBlock(finalBlocks);
        return listBlock;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[listBlock=[numberOfRecs=");
        s.append(numberOfRecs).append("]")
        .append("[headerInfo=").append(StringUtils.join(List.of(headerInfo), '|')).append("]")
        .append("[message=").append(message).append("]").append("[curPageNum=").append(curPageNum).append("]")
        .append("[maxPageNum=").append(maxPageNum).append("]").append("[dataBlocks=");
        for(int i=0;i<numberOfRecs;i++){
            s.append(StringUtils.join(List.of(dataBlock.get(i)), '|'));
            if(i < numberOfRecs-1)
                s.append("~");
        }
        s.append("]]");
        return s.toString();
    }
}
