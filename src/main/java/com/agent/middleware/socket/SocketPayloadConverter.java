package com.agent.middleware.socket;

import com.agent.middleware.socket.payloads.*;

import java.util.*;


public class SocketPayloadConverter {

    private RequestSocketStringObject getRequestStringObject(String socketResponse) {

        RequestSocketStringObject requestSocketStringObject = new RequestSocketStringObject();
        //todo:: breaking into pieces
        return requestSocketStringObject;
    }

    //data:: [serviceName=MoveMoneyService|versionInfo=v01|funcCode=L]
    private CallingInfo getCallingInfoObject(String callingInfoString) {
        CallingInfo callingInfo = new CallingInfo();
        String[] particles = callingInfoString.replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .split("\\|");

        for (String particle : particles) {
            String[] keyValue = particle.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                if ("serviceName".equals(key)) {
                    callingInfo.setServiceName(value);
                } else if ("versionInfo".equals(key)) {
                    callingInfo.setVersionInfo(value);
                } else if ("funcCode".equals(key)) {
                    callingInfo.setFuncCode(value);
                }

            }
        }
        return callingInfo;
    }

    // data:: [criteria1=criteriaValue1|criteria2=criteriaValue2|actionCode=<Submit,Cancel>|submitWithExceptionOverride=Y]
    private CallingParam getCallingParamObject(String callingParamString) {
        CallingParam callingParam = new CallingParam();
        String[] keyValuePairs = callingParamString
                .replaceAll("\\[", "") // Remove leading '['
                .replaceAll("\\]", "") // Remove trailing ']'
                .split("\\|");

        HashMap<String, String> paramMap = new HashMap<>();

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                if ("actionCode".equals(key)) {
                    callingParam.setActionCode(value);
                } else if ("submitWithExceptionOverride".equals(key)) {
                    callingParam.setSubmitWithExceptionOverride("Y".equalsIgnoreCase(value));
                } else {
                    paramMap.put(key, value);
                }
            }
        }

        callingParam.setHashMap(paramMap);

        return callingParam;
    }

    // data:: [ipAddress= 127.0.0.1|processorId=coreI7|macAddress=secTok|browser=Mozilla]
    private DeviceInfo getDeviceInfoObject(String deviceInfoString) {
        DeviceInfo deviceInfo = new DeviceInfo();
        String[] particles = deviceInfoString.replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .split("\\|");

        for (String particle : particles) {
            String[] keyValue = particle.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                if ("ipAddress".equals(key)) {
                    deviceInfo.setIpAddress(value);
                } else if ("processorId".equals(key)) {
                    deviceInfo.setProcessorId(value);
                } else if ("macAddress".equals(key)) {
                    deviceInfo.setMacAddress(value);
                } else if ("browser".equals(key)) {
                    deviceInfo.setBrowser(value);
                }

            }
        }

        return deviceInfo;
    }

    // data:: [headerInfo=abc|ppp|pppp][numOfRecs=53][messageToDisplay=][[1|6|9]~[2|3|0]pipeSeperatedColumn tilDaSeperatedRow]
    private ExceptionBlock getExceptionBlock(String exceptionBlockString) {
        String[] parts = exceptionBlockString.split("\\[|\\]");

        List<String> headerInfo = new ArrayList<>();
        String numOfRecs = "0";
        String messageToDisplay = "";
        List<List<String>> records = new ArrayList<>();

        for (String part : parts) {
            String[] keyValue = part.split("=");

            if (keyValue.length > 1) {
                String key = keyValue[0];
                String value = keyValue[1];

                switch (key) {
                    case "headerInfo":
                        headerInfo = Arrays.asList(value.split("\\|"));
                        break;
                    case "numOfRecs":
                        if (!value.isEmpty()) {
                            numOfRecs = value;
                        }
                        break;
                    case "messageToDisplay":
                        messageToDisplay = value;
                        break;
                    case "records":
                        String[] recordStrings = value.split("~");
                        for (String recordString : recordStrings) {
                            records.add(Arrays.asList(recordString.split("\\|")));
                        }
                        break;
                }
            }
        }

        ExceptionBlock exceptionBlock = new ExceptionBlock();
        exceptionBlock.setHeaderInfo(headerInfo);
        exceptionBlock.setNumOfRecs(numOfRecs);
        exceptionBlock.setMessageToDisplay(messageToDisplay);
        exceptionBlock.setRecords(records);

        return exceptionBlock;
    }

    public GenBlock getGenBlock(String genBlockString) {
        GenBlock genBlock = new GenBlock();
        // genBlockString has the format "genDataBlock=[data1=<>|data2=<>]"
        if (genBlockString.startsWith("[genDataBlock=[")) {
            String genDataBlockString = genBlockString.substring("[genDataBlock=[".length(), genBlockString.length() - 2);
            String[] keyValuePairs = genDataBlockString.split("\\|");

            Map<String, String> genDataMap = new HashMap<>();

            System.out.println(genDataMap);
            for (String pair : keyValuePairs) {
                String[] parts = pair.split("=");
                if (parts.length == 2) {
                    genDataMap.put(parts[0], parts[1]);
                }
            }
//            genBlock.setDataMap(genDataMap);
        }
        // Return the GenBlock object
        return genBlock;
    }

    private ListBlock getListBlock(String listBlockString) {
        ListBlock listBlock = new ListBlock();
        return listBlock;
    }


    //[numOfMrh=3]
    //[mrhBlock1=[headerInfo= name | age | address][numOfRecs=][messageToDisplay=][nafiz | 33 | dhaka ~ polash | 27 | khulna]]
    //[mrhBlock2=[headerInfo=pipeSeperatedColumnNames][numOfRecs=][messageToDisplay=][pipeSeperatedColumn tilDaSeperatedRow]]
    //[mrhBlock3=[headerInfo=pipeSeperatedColumnNames][numOfRecs=][messageToDisplay=][pipeSeperatedColumn tilDaSeperatedRow]]
    //]
    private MrhBlock getMrhBlockObject(String gridMrhBlockString) {
        MrhBlock mrhBlock = new MrhBlock();

        return mrhBlock;
    }

    private SecurityInfo getSecurityInfoObject(String securityString) {
        SecurityInfo securityInfo = new SecurityInfo();

        String[] keyValuePairs = securityString.split("\\[|\\||\\]");

        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "userId":
                        securityInfo.setUserId(value);
                        break;
                    case "sessionId":
                        securityInfo.setSessionId(value);
                        break;
                    case "securityToken":
                        securityInfo.setSecurityToken(value);
                        break;
                    case "saltValue":
                        securityInfo.setSaltValue(value);
                        break;
                }
            }
        }
        return securityInfo;
    }


    private StatusBlock getStatusBlock(String statusBlockString) {
        StatusBlock statusBlock = new StatusBlock();

        String[] keyValuePairs = statusBlockString.split("\\[|\\||\\]");


        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "responseCode":
                        statusBlock.setResponseCode(value);
                        break;
                    case "responseMessage":
                        statusBlock.setResponseMessage(value);
                        break;
                }
            }
        }
        return statusBlock;
    }


    /////////////////////////

    private String getCallingInfoString(CallingInfo callingInfo) {
        StringBuilder result = new StringBuilder();

        result.append("[serviceName=").append(callingInfo.getServiceName()).append("|");
        result.append("versionInfo=").append(callingInfo.getVersionInfo()).append("|");
        result.append("funcCode=").append(callingInfo.getFuncCode()).append("]");

        return result.toString();
    }

    private String getSecurityInfo(SecurityInfo securityInfo) {
        return "";
    }


    private String getDeviceInfo(DeviceInfo deviceInfo) {
        StringBuilder result = new StringBuilder();

        result.append("[ipAddress=").append(deviceInfo.getIpAddress()).append("|");
        result.append("processorId=").append(deviceInfo.getProcessorId()).append("|");
        result.append("macAddress=").append(deviceInfo.getMacAddress()).append("|");
        result.append("browser=").append(deviceInfo.getBrowser()).append("]");

        return result.toString();
    }


    private String getCallingParam(CallingParam callingParam) {
        if (callingParam == null) {
            return "[]";
        }

        StringBuilder result = new StringBuilder("[ ");

        HashMap<String, String> paramMap = callingParam.getHashMap();
        if (paramMap != null && !paramMap.isEmpty()) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                result.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("|");
            }
        }

        if (callingParam.getActionCode() != null) {
            result.append("[actionCode=")
                    .append(callingParam.getActionCode())
                    .append("|");
        }

        if (callingParam.getSubmitWithExceptionOverride() != null) {
            result.append("submitWithExceptionOverride=")
                    .append(callingParam.getSubmitWithExceptionOverride() ? "Y" : "N")
                    .append("|");
        }
        result.append("]");
        return result.toString();
    }


    private String getGenBlock(GenBlock genBlock) {

        return genBlock.genBlockToString();
    }

    private String getMrhBlock(MrhBlock mrhBlock) {
        return "";
    }

    private String getListBlock(ListBlock listBlock) {
        return "";
    }

    private String getStatusBlock(StatusBlock statusBlock) {
        return "";
    }

    private String getExceptionBlock(ExceptionBlock exceptionBlock) {
        return "";
    }

}
