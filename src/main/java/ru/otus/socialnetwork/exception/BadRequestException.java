package ru.otus.socialnetwork.exception;

public class BadRequestException extends Exception {
    @Override
    public String getMessage() {
        return message;
    }

    String message = "Неверный запрос";
}
