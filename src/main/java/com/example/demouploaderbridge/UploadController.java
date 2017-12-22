package com.example.demouploaderbridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.text.NumberFormat;

@RestController
public class UploadController {
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    private final RestTemplate restTemplate;

    public UploadController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/bridge")
    public String bridge(@RequestParam MultipartFile file) throws Exception {
        MultiValueMap<String, Object> multiPartBody = new LinkedMultiValueMap<>();
        multiPartBody.add("file", new InputStreamResource(file.getInputStream()) {
            // See https://jira.spring.io/browse/SPR-13571
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }

            @Override
            public long contentLength() throws IOException {
                return -1; // we do not want to generally read the whole stream into memory ...
            }
        });
        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity
                .post(URI.create("http://localhost:9090/upload"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multiPartBody);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getBody();
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();
        log.info("originalFilename={},contentType={},size={}", originalFilename, contentType, NumberFormat.getIntegerInstance().format(size));
        return "OK";
    }
}
