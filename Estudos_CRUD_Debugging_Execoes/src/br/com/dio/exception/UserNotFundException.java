package br.com.dio.exception;

public class UserNotFundException extends RuntimeException{

    public UserNotFundException(String message) {
        super(message);
    }
}
