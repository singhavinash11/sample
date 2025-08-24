package org.singhav.sample.exception;

public class IllegalAccessOrInvocationTargetException extends RuntimeException {
    public IllegalAccessOrInvocationTargetException(String message, Exception ex) {
        super(message, ex);
    }
}
