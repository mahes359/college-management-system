package com.college.course.config;

import com.college.course.exception.CourseDatabaseException;
import com.college.course.exception.CourseNotFoundException;
import com.college.course.exception.CourseValidationException;
import com.college.course.exception.DuplicateCourseException;

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

        // Client errors
        mappings.setProperty(
                CourseValidationException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                CourseNotFoundException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                DuplicateCourseException.class.getName(),
                "CLIENT"
        );

        // Server/database error
        mappings.setProperty(
                CourseDatabaseException.class.getName(),
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