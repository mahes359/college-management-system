package com.college.exam_service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.SERVER)
public class ExamDatabaseException extends RuntimeException {

    public ExamDatabaseException(String message) {
        super(message);
    }

    public ExamDatabaseException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}
