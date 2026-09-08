package com.college.attendance_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    @Autowired
    private DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/{serviceName}/ws")
    public String proxySoap(@PathVariable String serviceName, @RequestBody String soapEnvelope) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName.toUpperCase());
        if (instances == null || instances.isEmpty()) {
            instances = discoveryClient.getInstances(serviceName.toLowerCase());
        }
        if (instances == null || instances.isEmpty()) {
            throw new RuntimeException("Service not available in Eureka: " + serviceName);
        }
        
        ServiceInstance instance = instances.get(0);
        String targetUrl = instance.getUri().toString() + "/ws";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        HttpEntity<String> request = new HttpEntity<>(soapEnvelope, headers);

        return restTemplate.postForObject(targetUrl, request, String.class);
    }
}
