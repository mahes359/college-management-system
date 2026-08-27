package com.college.enrollment.config;

import com.college.enrollment.exception.CourseServiceException;
import com.college.enrollment.exception.DuplicateEnrollmentException;
import com.college.enrollment.exception.EnrollmentDatabaseException;
import com.college.enrollment.exception.EnrollmentNotFoundException;
import com.college.enrollment.exception.EnrollmentValidationException;
import com.college.enrollment.exception.RemoteServiceException;
import com.college.enrollment.exception.StudentServiceException;

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

        Properties mappings = new Properties();

        mappings.setProperty(
                EnrollmentValidationException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                EnrollmentNotFoundException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                DuplicateEnrollmentException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                StudentServiceException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                CourseServiceException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                RemoteServiceException.class.getName(),
                "SERVER"
        );

        mappings.setProperty(
                EnrollmentDatabaseException.class.getName(),
                "SERVER"
        );

        resolver.setExceptionMappings(mappings);

        SoapFaultDefinition defaultFault =
                new SoapFaultDefinition();

        defaultFault.setFaultCode(
                SoapFaultDefinition.SERVER
        );

        resolver.setDefaultFault(defaultFault);

        resolver.setOrder(1);

        return resolver;
    }
}