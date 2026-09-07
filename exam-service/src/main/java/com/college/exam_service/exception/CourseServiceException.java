package com.college.exam_service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.SERVER)
public class CourseServiceException extends RuntimeException {

    public CourseServiceException(String message) {
        super(message);
    }

    public CourseServiceException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}
