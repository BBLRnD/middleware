package com.agent.middleware.dto.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Getter
@Setter
@NoArgsConstructor
public class MrhBlock{
    private Integer numberOfMrh;
    private List<MrhBlockDetails> mrhBlocks;


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

    public MrhBlock mrhBlock(String mrhBlockStr){
        Long timestamp = new Date().getTime();
        String mrhDetails = mrhBlockStr.split("mrhDetails=", 2)[1].trim();
        String[] mrhDetailList = mrhDetails.trim().split("(?=~\\[)");
        List<MrhBlockDetails> mrhBlockDetailList = new ArrayList<>();
        for (String mrhDetail : mrhDetailList) {
            MrhBlockDetails mrhBlockDetails = new MrhBlockDetails();
            mrhBlockDetails.setListName(Objects.requireNonNull(extractValue(mrhDetail, "listName")).replaceAll("\\]\\[\\~", ""));
            mrhBlockDetails.setNumberOfRecs(Integer.parseInt(Objects.requireNonNull(extractValue(mrhDetail, "numberOfRecs")).replaceAll("]\\[", "")));
            mrhBlockDetails.setMessage(Objects.requireNonNull(extractValue(mrhBlockStr, "message")).replaceAll("\\]\\[", ""));
            mrhBlockDetails.setCurPageNum(Integer.parseInt(Objects.requireNonNull(extractValue(mrhDetail, "curPageNum")).replaceAll("\\]\\[", "")));
            mrhBlockDetails.setMaxPageNum(Integer.parseInt(Objects.requireNonNull(extractValue(mrhDetail, "maxPageNum")).replaceAll("\\]\\[", "")));

            String headerInfos = Objects.requireNonNull(extractValue(mrhDetail, "headerInfo")).replace("\\]\\[", "");
            mrhBlockDetails.setHeaderInfo(headerInfos.split("\\|"));

            String dataBlocks = Objects.requireNonNull(extractValue(mrhDetail, "dataBlocks")).replace("\\]\\[", "");
            String[] dataBlockList = dataBlocks.split("\\~");
            List<String[]> finalBocks = new ArrayList<>();
            for (int i = 0; i < dataBlockList.length; i++) {
                String[] dataBlockData = dataBlockList[i].split("\\|");
                finalBocks.add(dataBlockData);
            }
            mrhBlockDetails.setDataBlock(finalBocks);
            mrhBlockDetailList.add(mrhBlockDetails);
        }

        int numberOfMrh = Integer.parseInt(extractValue(mrhBlockStr, "numOfMrh"));

        MrhBlock mrhBlock = new MrhBlock();
        mrhBlock.setNumberOfMrh(numberOfMrh);
        mrhBlock.setMrhBlocks(mrhBlockDetailList);
        Long timestamp1 = new Date().getTime();
        System.out.println("first time" + timestamp + " Last Time :" + timestamp1);
        System.out.println("Difference : " + (timestamp1 - timestamp));
        return mrhBlock;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("mrhDataBlock=[numOfMrh="+numberOfMrh+"]");
        s.append("[mrhDetails="+(mrhBlocks.toString().replaceFirst("\\[","")).
                replaceFirst("\\,","~"));
        return s.toString();

    }
}
