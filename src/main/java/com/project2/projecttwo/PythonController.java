package com.project2.projecttwo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class PythonController {
    @GetMapping("/runPython")
    public String Python() {
       
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5000/runPython";

        HttpHeaders headers = new HttpHeaders();
        // headers.set("Authorization", "Bearer YOUR_TOKEN_HERE");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
        return response.getBody();
    }


}
