package com.example.demouploaderbridge;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoUploaderBridgeApplication {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false); // Use "Chunked transfer encoding"
        return builder
                .requestFactory(requestFactory)
                .build();
    }

    @Bean
    public RequestDumperFilter dumperFilter() {
        return new RequestDumperFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoUploaderBridgeApplication.class, args);
    }
}
