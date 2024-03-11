package com.yanfaisn.restapi;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController

public class HelloWorld {

    @GetMapping("hello")
    public String sayHello() {
        return "Hello Yanuar";
    }

    @PostMapping("say-hello")
    public String postMethodName(@RequestBody String requestBody) {

        return requestBody;
    }

}
