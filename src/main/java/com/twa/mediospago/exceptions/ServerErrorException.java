package com.twa.mediospago.exceptions;

public class ServerErrorException extends RuntimeException{
    private int statusCode;

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
