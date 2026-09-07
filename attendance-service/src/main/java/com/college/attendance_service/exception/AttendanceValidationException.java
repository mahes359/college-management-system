package com.college.attendance_service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.CLIENT)
public class AttendanceValidationException
        extends RuntimeException {

    public AttendanceValidationException(String message) {
        super(message);
    }
}
