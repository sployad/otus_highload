package ru.otus.socialnetwork.service;

import ru.otus.socialnetwork.dto.UserRegRequestDto;
import ru.otus.socialnetwork.dto.UserRegResponseDto;
import ru.otus.socialnetwork.exception.BadRequestException;
import ru.otus.socialnetwork.security.CustomUserDetails;

public interface AuthService {
    public UserRegResponseDto register(UserRegRequestDto userDto) throws BadRequestException;
    public CustomUserDetails currentUser();
}
