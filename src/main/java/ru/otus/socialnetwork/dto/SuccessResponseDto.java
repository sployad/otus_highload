package ru.otus.socialnetwork.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SuccessResponseDto implements Serializable {
    private String message = "successful";
}
