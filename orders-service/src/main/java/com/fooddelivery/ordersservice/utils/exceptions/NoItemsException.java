package com.fooddelivery.ordersservice.utils.exceptions;

public class NoItemsException extends RuntimeException {
    public NoItemsException() {

    }
    public NoItemsException(String message) {
        super(message);
    }
    public NoItemsException(Throwable cause) {
        super (cause);
    }
    public NoItemsException(String message, Throwable cause) {super(message, cause);}

}
