package com.swiggyapp.swiggyartefact;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.swiggyapp.swiggyartefact.exceptionhandler.OrderException;

@ControllerAdvice
public class SwiggyAppExceptionHandler {
   @ExceptionHandler(value = OrderException.class)
   public ResponseEntity<Object> exception(OrderException exception) {
      return new ResponseEntity<>("No Valid Record found", HttpStatus.BAD_REQUEST);
   }
}
