package com.college.faculty.config;

import com.college.faculty.exception.DuplicateFacultyException;
import com.college.faculty.exception.FacultyNotFoundException;
import com.college.faculty.exception.FacultyValidationException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import java.util.Properties;

@Configuration
public class SoapExceptionConfig {

    @Bean
    public SoapFaultMappingExceptionResolver soapExceptionResolver() {

        SoapFaultMappingExceptionResolver resolver =
                new SoapFaultMappingExceptionResolver();

        Properties mappings = new Properties();

        // Client errors
        mappings.setProperty(
                FacultyValidationException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                DuplicateFacultyException.class.getName(),
                "CLIENT"
        );

        mappings.setProperty(
                FacultyNotFoundException.class.getName(),
                "CLIENT"
        );

        resolver.setExceptionMappings(mappings);

        // Default = SERVER
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