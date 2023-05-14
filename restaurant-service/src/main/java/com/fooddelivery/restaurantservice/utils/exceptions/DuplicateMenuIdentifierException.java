package com.fooddelivery.restaurantservice.utils.exceptions;

public class DuplicateMenuIdentifierException extends RuntimeException{
    public DuplicateMenuIdentifierException(){

    }
    public DuplicateMenuIdentifierException(String message) {
        super(message);
    }
    public DuplicateMenuIdentifierException(Throwable cause) {
        super (cause);
    }
    public DuplicateMenuIdentifierException(String message, Throwable cause) {super(message, cause);}



}
