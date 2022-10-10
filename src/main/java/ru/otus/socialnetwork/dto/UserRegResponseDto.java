package ru.otus.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserRegResponseDto {
    private String message = "successful";
    private Long userId;

    public UserRegResponseDto(Long userId) {
        this.userId = userId;
    }
}
