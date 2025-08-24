package com.fbn.case_study.exception;

import com.fbn.case_study.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }
        for (ObjectError objectError : bindingResult.getGlobalErrors()) {
            errorMessages.add(objectError.getDefaultMessage());
        }
        String errorMessage = String.join(" | ", errorMessages);
        var response = ApiResponse.builder()
                .referenceId(UUID.randomUUID().toString())
                .requestTime(LocalDateTime.now())
                .requestType("Outbound")
                .message(errorMessage)
                .status(false)
                .error("Validation failed")
                .build();

        return new ResponseEntity<>(response, BAD_REQUEST);
    }
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<?> exceptionHandler(Exception exception){
        var response = ApiResponse.builder()
                .referenceId(UUID.randomUUID().toString())
                .requestTime(LocalDateTime.now())
                .requestType("Outbound")
                .message("Unable to process request at this time, please try again later. "+exception.getMessage())
                .status(false)
                .error("Validation failed")
                .build();
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        var response = ApiResponse.builder()
                .referenceId(UUID.randomUUID().toString())
                .requestTime(LocalDateTime.now())
                .requestType("Outbound")
                .message("Unable to process request at this time, please try again later. "+ex.getMessage())
                .status(false)
                .error("Operation not successful")
                .build();
        return new ResponseEntity<>(response, BAD_REQUEST);
    }
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<?> handleBindException(BindException ex) {
        var response = ApiResponse.builder()
                .referenceId(UUID.randomUUID().toString())
                .requestTime(LocalDateTime.now())
                .requestType("Outbound")
                .message("Unable to process request at this time, please try again later. "+ex.getMessage())
                .status(false)
                .error("Invalid input")
                .build();
        return new ResponseEntity<>(response, BAD_REQUEST);
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        var response = ApiResponse.builder()
                .referenceId(UUID.randomUUID().toString())
                .requestTime(LocalDateTime.now())
                .requestType("Outbound")
                .message("Unable to process request at this time, please try again later. "+ex.getMessage())
                .status(false)
                .error("Operation not successful")
                .build();
        return new ResponseEntity<>(response, BAD_REQUEST);
    }
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {

        var response = ApiResponse.builder()
                .referenceId(UUID.randomUUID().toString())
                .requestTime(LocalDateTime.now())
                .requestType("Outbound")
                .message(ex.getMessage())
                .status(false)
                .error("Operation not successful")
                .build();
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKeyException(DuplicateKeyException ex) {
        var response = ApiResponse.builder()
                .referenceId(UUID.randomUUID().toString())
                .requestTime(LocalDateTime.now())
                .requestType("Outbound")
                .message("Unable to process request at this time, please try again later. "+ex.getMessage())
                .status(false) .error("Operation not successful") .build();
        return new ResponseEntity<>(response, BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String userFriendlyMessage = "Duplicate record found.";

        ex.getMostSpecificCause();
        if (ex.getMostSpecificCause().getMessage() != null) {
            String dbMessage = ex.getMostSpecificCause().getMessage();

            if (dbMessage.contains("bvn")) {
                userFriendlyMessage = "A customer with this BVN already exists.";
            } else if (dbMessage.contains("nin")) {
                userFriendlyMessage = "A customer with this NIN already exists.";
            } else if (dbMessage.contains("account_number")) {
                userFriendlyMessage = "This account number is already registered.";
            } else if (dbMessage.contains("email")) {
                userFriendlyMessage = "This email address is already registered.";
            } else if (dbMessage.contains("phone")) {
                userFriendlyMessage = "This phone number is already registered.";
            }
        }

        var response = ApiResponse.builder()
                .referenceId(UUID.randomUUID().toString())
                .requestTime(LocalDateTime.now())
                .requestType("Outbound")
                .message(userFriendlyMessage)
                .status(false)
                .error("Operation not successful")
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
