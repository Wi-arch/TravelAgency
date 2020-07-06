package by.education.travel.controller.handler;

import by.education.travel.exception.InvalidSpecificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidSpecificationException.class)
    public final ResponseEntity<String> handleException(InvalidSpecificationException exception, WebRequest request) {
        setTextHeader();
        return new ResponseEntity<>("Custom exception;)", BAD_REQUEST);
    }

    private void setTextHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
    }
}
