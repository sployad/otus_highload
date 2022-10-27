package ru.otus.socialnetwork.service;

import ru.otus.socialnetwork.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findByFirstNameOrLastName(String firstName, String lastName);
}
