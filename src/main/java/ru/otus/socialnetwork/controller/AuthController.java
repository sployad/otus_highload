package ru.otus.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import ru.otus.socialnetwork.dto.UserRegRequestDto;
import ru.otus.socialnetwork.dto.UserRegResponseDto;
import ru.otus.socialnetwork.exception.BadRequestException;
import ru.otus.socialnetwork.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "register")
    public UserRegResponseDto register(@RequestBody UserRegRequestDto usrDto) throws BadRequestException {
        return authService.register(usrDto);
    }
}
