package com.college.attendance_service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.SERVER)
public class StudentServiceException
        extends RuntimeException {

    public StudentServiceException(String message) {
        super(message);
    }

    public StudentServiceException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}
