package com.college.attendance_service.config;

import com.college.attendance_service.exception.AttendanceDatabaseException;
import com.college.attendance_service.exception.AttendanceNotFoundException;
import com.college.attendance_service.exception.AttendanceValidationException;
import com.college.attendance_service.exception.DuplicateAttendanceException;
import com.college.attendance_service.exception.RemoteServiceException;
import com.college.attendance_service.exception.StudentServiceException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import java.util.Properties;

@Configuration
public class SoapExceptionConfig {

    @Bean
    public SoapFaultMappingExceptionResolver
    soapExceptionResolver() {

        SoapFaultMappingExceptionResolver resolver =
                new SoapFaultMappingExceptionResolver();

        Properties mappings =
                new Properties();

        mappings.setProperty(
                AttendanceValidationException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                AttendanceNotFoundException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                DuplicateAttendanceException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                StudentServiceException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                RemoteServiceException.class.getName(),
                "SERVER"
        );

        mappings.setProperty(
                AttendanceDatabaseException.class.getName(),
                "SERVER"
        );

        resolver.setExceptionMappings(
                mappings
        );

        SoapFaultDefinition defaultFault =
                new SoapFaultDefinition();

        defaultFault.setFaultCode(
                SoapFaultDefinition.SERVER
        );

        resolver.setDefaultFault(
                defaultFault
        );

        resolver.setOrder(1);

        return resolver;
    }
}