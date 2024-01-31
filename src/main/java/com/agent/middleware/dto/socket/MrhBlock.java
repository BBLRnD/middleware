package com.agent.middleware.dto.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MrhBlock {
    private Integer numberOfMrh;
    private List<MrhBlockDetails> mrhBlocks;

    public MrhBlock(String mrhBlockStr, SocketPayload socketPayload) {
        socketPayload.setMrhBlock(mrhBlock(mrhBlockStr));
    }

    public MrhBlock() {
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

    private MrhBlock mrhBlock(String mrhBlockStr) {
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
        return mrhBlock;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (mrhBlocks != null) {
            s.append("[mrhBlock=[numOfMrh=").append(numberOfMrh).append("]");
            //s.append("[mrhDetails=").append(mrhBlocks.toString().replaceFirst("\\[", "")).replaceFirst("\\,", "~")));
            s.append("[mrhDetails=").append(mrhBlocks.toString().replaceFirst("\\[", "")
                    .replaceAll("\\,", "~"));
        }
        return s.toString();
    }

    public Integer getNumberOfMrh() {
        return numberOfMrh;
    }

    public void setNumberOfMrh(Integer numberOfMrh) {
        this.numberOfMrh = numberOfMrh;
    }

    public List<MrhBlockDetails> getMrhBlocks() {
        return mrhBlocks;
    }

    public void setMrhBlocks(List<MrhBlockDetails> mrhBlocks) {
        this.mrhBlocks = mrhBlocks;
    }
}
