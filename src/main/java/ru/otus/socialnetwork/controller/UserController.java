package ru.otus.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.socialnetwork.dto.UserDto;
import ru.otus.socialnetwork.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;

    @GetMapping(params = {"firstName", "lastName"})
    public List<UserDto> getUsers(@RequestParam String firstName, @RequestParam String lastName){
        return userService.findByFirstNameOrLastName(firstName, lastName);
    }
}
