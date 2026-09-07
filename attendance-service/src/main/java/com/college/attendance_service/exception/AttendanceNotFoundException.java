package com.college.attendance_service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.CLIENT)
public class AttendanceNotFoundException
        extends RuntimeException {

    public AttendanceNotFoundException(String message) {
        super(message);
    }
}
