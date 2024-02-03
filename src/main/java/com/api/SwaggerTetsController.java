package com.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerTetsController {

    @GetMapping("/test")
    public String testAPI() {
        return "Hello, Swagger!";
    }
}
