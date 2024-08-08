package com.danielfreitassc.backend.configuration;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.danielfreitassc.backend.exceptions.errorResponse.ErrorResponse;



@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "Ocorreu um erro. Sentimos muito pelo inconveniente. Por favor, tente novamente.";
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "Erro verifique se essa ação é permitida";
        
        if (ex.getCause() instanceof SQLException) {
            SQLException sqlException = (SQLException) ex.getCause();
            String sqlState = sqlException.getSQLState();
            
            if (sqlState != null && sqlState.startsWith("22001")) {
                errorMessage = "O tamanho dos dados inseridos excede o limite permitido.";
            }
        }
        
        ErrorResponse errorResponse = new ErrorResponse("CONFLICT", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        String errorMessage = "Usuário não encontrado"; // Mensagem genérica para exemplo

        ErrorResponse errorResponse = new ErrorResponse("UNAUTHORIZED", errorMessage);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusCode().toString(), ex.getReason());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        String errorMessage = "Ocorreu um erro inesperado. Sentimos muito pelo inconveniente. Por favor, tente novamente.";
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String errorMessage = "Recurso não encontrado. A URL solicitada pode estar incorreta.";
        ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    } 
}
