package com.fooddelivery.clientservice.utils.exceptions;

public class DuplicateUserNameException extends RuntimeException{
    public DuplicateUserNameException(){

    }
    public DuplicateUserNameException(String message) {
        super(message);
    }
    public DuplicateUserNameException(Throwable cause) {
        super (cause);
    }
    public DuplicateUserNameException(String message, Throwable cause) {super(message, cause);}



}
