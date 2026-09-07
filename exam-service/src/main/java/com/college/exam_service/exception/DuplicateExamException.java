package com.college.exam_service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.CLIENT)
public class DuplicateExamException extends RuntimeException {

    public DuplicateExamException(String message) {
        super(message);
    }
}
