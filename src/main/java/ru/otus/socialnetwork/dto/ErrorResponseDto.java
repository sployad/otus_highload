package ru.otus.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorResponseDto implements Serializable {
    private String message;
}

