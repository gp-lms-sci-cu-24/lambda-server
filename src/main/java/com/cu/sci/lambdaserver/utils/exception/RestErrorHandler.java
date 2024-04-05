package com.cu.sci.lambdaserver.utils.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Slf4j
public class RestErrorHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<RestErrorResponse> handleStatusException(ResponseStatusException ex, WebRequest request) {
//        log.error(ex.getReason(), ex);
        return RestErrorResponse.builder()
                .exception(ex)
                .path(request.getDescription(false).substring(4))
                .build().entity();
    }
}
