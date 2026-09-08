$services = @{
    "attendance-service" = "com.college.attendance_service"
    "course-service" = "com.college.course"
    "enrollment-service" = "com.college.enrollment"
    "exam-service" = "com.college.exam_service"
    "faculty-service" = "com.college.faculty"
    "student-service" = "com.college.student"
}

foreach ($svc in $services.Keys) {
    $pkg = $services[$svc]
    $dir = $pkg.Replace(".", "\")
    $controllerDir = "e:\CODING\CLOUD COMPUTING\WEBSERVICE\$svc\src\main\java\$dir\controller"
    
    if (-Not (Test-Path $controllerDir)) {
        New-Item -ItemType Directory -Force -Path $controllerDir | Out-Null
    }
    
    $code = @"
package $pkg.controller;

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
"@
    
    Set-Content -Path "$controllerDir\ProxyController.java" -Value $code
    Write-Host "Created ProxyController in $svc"
}
