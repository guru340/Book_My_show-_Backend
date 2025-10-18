package com.cfs.BookMyShow.exception;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFound ex, WebRequest request){
        ErrorMessage errorMessage=new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), "NOT FOUND",ex.getMessage()
                ,request.getDescription(false));

        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<?> SeatUnavailableException(ResourceNotFound ex, WebRequest request){
        ErrorMessage errorMessage=new ErrorMessage(new Date(), HttpStatus.BAD_REQUEST.value(), "BAD REQUEST",ex.getMessage()
                ,request.getDescription(false));

        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(ResourceNotFound ex, WebRequest request){
        ErrorMessage errorMessage=new ErrorMessage(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "NOT FOUND",ex.getMessage()
                ,request.getDescription(false));

        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
