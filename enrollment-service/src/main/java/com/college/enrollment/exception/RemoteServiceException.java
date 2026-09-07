package com.college.enrollment.exception;

import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

@SoapFault(faultCode = FaultCode.SERVER)
public class RemoteServiceException
        extends RuntimeException {

    public RemoteServiceException(String message) {
        super(message);
    }

    public RemoteServiceException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}
