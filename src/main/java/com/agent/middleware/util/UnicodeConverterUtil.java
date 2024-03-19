package com.agent.middleware.util;

public class UnicodeConverterUtil {
    public static String convertTextToUni(String banglaString) {

        StringBuilder unicodeStringBuilder = new StringBuilder();

        for (int i = 0; i < banglaString.length(); i++) {
            char ch = banglaString.charAt(i);
            if (ch == ' ') {
                unicodeStringBuilder.append(" ");
            }
            else {
                String unicodeHex = String.format("%04x", (int) ch);
                unicodeStringBuilder.append("\\u").append(unicodeHex);
            }
        }

        return unicodeStringBuilder.toString();
    }

    public static String convertUniToText(String unicodeString) {

        StringBuilder textStringBuilder = new StringBuilder();

        int i = 0;

        while (i < unicodeString.length()) {

            if (unicodeString.startsWith("\\u", i) && i + 6 <= unicodeString.length()) {
                int unicodeValue = Integer.parseInt(unicodeString.substring(i + 2, i + 6), 16);
                textStringBuilder.append((char) unicodeValue);
                i += 6;
            }
            else {
                textStringBuilder.append(unicodeString.charAt(i));
                i++;
            }
        }

        return textStringBuilder.toString();
    }
}
