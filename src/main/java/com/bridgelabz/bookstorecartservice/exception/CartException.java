package com.bridgelabz.bookstorecartservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CartException extends RuntimeException{
    private int statusCode;
    private String statusMessage;

    public CartException(int i, String book_not_added_) {
    }
}
