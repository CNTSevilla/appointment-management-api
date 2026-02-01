package org.cnt.appointmentmanagementtest.common.error_handle.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.cnt.appointmentmanagementtest.common.error_handle.dto.ApiErrorResponse;
import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.ApiException;
import org.cnt.appointmentmanagementtest.common.error_handle.exceptions.errors.interfaces.IAPIError;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GenericControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(GenericControllerAdvice.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleAppException(ApiException ex, HttpServletRequest request) {
        IAPIError err = ex.getError();

        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                err.getHttpStatus(),
                ex.getMessage(),
                err.getCode(),
                err.getReason(),
                request.getRequestURI()
        );

        // Log interno (sin filtrar internals al cliente)
        log.warn("Handled ApiException: {}, \ntime={}, \nstatus={}, \nmessage={}, \nreason={}, \nexception={}",
                body.path(), body.timestamp(), body.status(), body.error(), body.reason(), ex);

        return ResponseEntity.status(err.getHttpStatus()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                "-849",
                "La petición contiene errores en los argumentos",
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                "-850",
                "La petición contiene valores no válidos",
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleBadJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                "-851",
                "El cuerpo de la petición no es un JSON válido",
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiErrorResponse> handleForbidden(Exception ex, HttpServletRequest request) {
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.FORBIDDEN,
                ex.getMessage(),
                "-852",
                "No tienes permisos para realizar esta operación",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(Exception ex, HttpServletRequest request) {
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                "-853",
                "No autenticado o credenciales inválidas",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> defaultErrorHandler(Exception ex, HttpServletRequest request) {
        // Log completo interno
        log.error("Unhandled exception at path={}", request.getRequestURI(), ex);

        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                "-899",
                "Error inesperado",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}