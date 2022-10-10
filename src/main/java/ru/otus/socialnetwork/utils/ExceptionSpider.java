package ru.otus.socialnetwork.utils;

import ru.otus.socialnetwork.dto.ErrorResponseDto;
import ru.otus.socialnetwork.exception.AuthorizationException;
import ru.otus.socialnetwork.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ExceptionSpider {
    @ExceptionHandler(value = {BadRequestException.class, HttpClientErrorException.BadRequest.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleBadRequestException(BadRequestException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(value = {AuthorizationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleAuthorizationException(AuthorizationException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }
}
