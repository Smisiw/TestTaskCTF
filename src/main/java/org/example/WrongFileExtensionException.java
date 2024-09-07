package org.example;

public class WrongFileExtensionException extends RuntimeException {
    public WrongFileExtensionException(String message) {
        super(message);
    }
}
