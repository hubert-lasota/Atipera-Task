package com.example.atiperatask;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        return new ResponseEntity<>(problemDetail, status);
    }

}
