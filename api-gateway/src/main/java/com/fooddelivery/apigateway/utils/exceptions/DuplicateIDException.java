package com.fooddelivery.apigateway.utils.exceptions;

public class DuplicateIDException extends RuntimeException{
    public DuplicateIDException(){

    }
    public DuplicateIDException(String message) {
        super(message);
    }
    public DuplicateIDException(Throwable cause) {
        super (cause);
    }
    public DuplicateIDException(String message, Throwable cause) {super(message, cause);}



}
