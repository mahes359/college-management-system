package com.college.course.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.CLIENT)
public class CourseValidationException extends RuntimeException {

    public CourseValidationException(String message) {
        super(message);
    }
}
