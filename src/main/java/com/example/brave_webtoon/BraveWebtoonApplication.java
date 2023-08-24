package com.example.brave_webtoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(
        exclude = {
                org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
//                org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
//                org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
        }
)
public class BraveWebtoonApplication {
//    static {
//        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
//    }
    public static void main(String[] args) {
        SpringApplication.run(BraveWebtoonApplication.class, args);
    }
}
