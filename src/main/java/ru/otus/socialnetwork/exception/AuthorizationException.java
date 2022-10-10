package ru.otus.socialnetwork.exception;

public class AuthorizationException extends Exception {
    @Override
    public String getMessage() {
        return "Невереный логин или пароль";
    }
}
