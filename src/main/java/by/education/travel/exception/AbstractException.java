package by.education.travel.exception;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException {

    private final String[] args;

    protected AbstractException(String message, String... args) {
        super(message);
        this.args = args;
    }
}