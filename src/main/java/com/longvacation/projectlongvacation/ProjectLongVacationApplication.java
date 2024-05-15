package com.longvacation.projectlongvacation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProjectLongVacationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectLongVacationApplication.class, args);
    }

    @RestController
    public static class SwaggerTetsController {

        @GetMapping("/test")
        public String testAPI() throws Exception {

            RestTemplate restTemplate = new RestTemplate();
            String url = "https://search.shopping.naver.com/catalog/15557524473?cat_id=50000326&frm=NVSCPRO"; // 요청하고자 하는 URL

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            //System.out.println(response.getBody()); // 응답 본문 출력

            return "Hello, Swagger!";
        }
    }
}
