package com.test.evaluation.exception.handler;

import com.test.evaluation.dto.ResponseStatus;
import com.test.evaluation.exception.GeneralEvaluationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalEvaluationExceptionHandler {
    private static final String DEFAULT_EXCEPTION_CAUSE_FORMAT = "Exception : {} , Cause : {}";

    @ExceptionHandler(value = GeneralEvaluationException.class)
    public ResponseEntity<Object> generalSegmentException(GeneralEvaluationException e) {
        log.error(DEFAULT_EXCEPTION_CAUSE_FORMAT, e.getClass(), e.getMessage(), e);

        ResponseStatus responseStatus = new ResponseStatus(e.getHttpStatusCode(), e.getMessage());
        return new ResponseEntity<>(responseStatus, e.getHttpStatus());
    }
}
