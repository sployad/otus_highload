package ru.otus.socialnetwork;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SpringBootApplication
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class OtusApplication {
    public static void main(String[] args) {
        SpringApplication.run(OtusApplication.class, args);
    }
}
