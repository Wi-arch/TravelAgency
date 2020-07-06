package by.education.travel.exception;

public class InvalidSpecificationException extends AbstractException {

    public InvalidSpecificationException(String description, String... args) {
        super(description, args);
    }
}
