package com.test.evaluation.exception;

import com.test.evaluation.constant.HttpStatusString;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneralEvaluationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private String httpStatusCode = HttpStatusString.INTERNAL_SERVER_ERROR;

    public GeneralEvaluationException(String message) {
        super(message);
    }

    public GeneralEvaluationException(HttpStatus httpStatus, String httpStatusCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
    }
}
