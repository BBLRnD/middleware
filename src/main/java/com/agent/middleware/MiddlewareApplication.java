package com.agent.middleware;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiddlewareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddlewareApplication.class, args);

        //String sJava="\u0985\u09AA\u09BE\u09B0\u09C7\u09B6\u09A8";
        //System.out.println("StringEscapeUtils.unescapeJava(sJava):\n" + StringEscapeUtils.unescapeJava(sJava));
    }

}
