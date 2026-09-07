package com.college.exam_service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.CLIENT)
public class ExamValidationException extends RuntimeException {

    public ExamValidationException(String message) {
        super(message);
    }
}
