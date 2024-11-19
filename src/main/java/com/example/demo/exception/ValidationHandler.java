package com.example.demo.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice  //đánh dấu đây là class handle exception cho spring boot hiểu
public class ValidationHandler {
    //định nghĩa cho nó chạy mỗi khi nó gặp exception nào đó

    //MethodArgumentNotValidException là lỗi khi user nhập sai
    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)  //response bad request, phải nhập lại input đúng valid
    public ResponseEntity handleValidation(MethodArgumentNotValidException ex){
            String mess = "";

            //cứ mỗi thuộc tính lỗi => xử lý
        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            //fieldError là những field bị lỗi
            mess += fieldError.getField() + ": " + fieldError.getDefaultMessage() + ", ";
        }
        return new ResponseEntity(mess, HttpStatus.BAD_REQUEST); //bad request, phải nhập lại input đúng valid
    }

    @ExceptionHandler(DuplicateEntity.class)
    public ResponseEntity handleValidation(Exception ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity notFound(Exception ex) {
//        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFound(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticateException.class)
    public ResponseEntity authenticateException(Exception ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
