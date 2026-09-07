package com.college.attendance_service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.SERVER)
public class AttendanceDatabaseException
        extends RuntimeException {

    public AttendanceDatabaseException(String message) {
        super(message);
    }

    public AttendanceDatabaseException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}
