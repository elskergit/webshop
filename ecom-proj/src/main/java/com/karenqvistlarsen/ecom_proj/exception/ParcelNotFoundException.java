package com.karenqvistlarsen.ecom_proj.exception;

public class ParcelNotFoundException extends RuntimeException {
    public ParcelNotFoundException(String message) {
        super(message);
    }
}