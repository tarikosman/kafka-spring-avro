package com.example.spring.kafka.demo.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeAttribute;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Example exception handler.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorProperties errorProperties;

    @Value
    @JsonInclude(Include.NON_NULL)
    public static class ExceptionResponse {
        private final ZonedDateTime timestamp = ZonedDateTime.now();
        private String type;
        private String message;
        private String trace;
    }

    private boolean isIncluded(IncludeAttribute attribute, WebRequest request, String paramName) {
        return errorProperties != null && (attribute == IncludeAttribute.ALWAYS ||
                (attribute == IncludeAttribute.ON_PARAM && "true".equals(request.getParameter("trace"))));
    }

    private String printStackTrace(Exception e, WebRequest request) {
        if (isIncluded(errorProperties.getIncludeStacktrace(), request, "trace")) {
            var s = new StringWriter();
            try (var out = new PrintWriter(s)) {
                e.printStackTrace(out);
            }
            return s.toString();
        }
        return null;
    }

    private ResponseEntity<Object> handleException(Exception e, HttpStatus status, WebRequest request) {
        var className = errorProperties.isIncludeException() ? e.getClass().getName() : null;
        var message = isIncluded(errorProperties.getIncludeMessage(), request, "message") ? e.getMessage() : null;
        var error = new ExceptionResponse(className, message, printStackTrace(e, request));
        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Object> handleAccountNotFoundException(EntityNotFoundException e, WebRequest request) {
        return handleException(e, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
