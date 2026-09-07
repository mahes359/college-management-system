package com.college.course.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.CLIENT)
public class DuplicateCourseException extends RuntimeException {

    public DuplicateCourseException(String message) {
        super(message);
    }
}
