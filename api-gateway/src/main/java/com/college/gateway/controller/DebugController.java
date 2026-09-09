package com.college.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;

@RestController
public class DebugController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/debug/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> response = new LinkedHashMap<>();

        // Environment info
        Map<String, String> env = new LinkedHashMap<>();
        env.put("PORT", System.getenv("PORT"));
        env.put("EUREKA_URL", System.getenv("EUREKA_URL"));
        env.put("RENDER", System.getenv("RENDER"));
        env.put("RENDER_SERVICE_NAME", System.getenv("RENDER_SERVICE_NAME"));
        env.put("RENDER_INSTANCE_ID", System.getenv("RENDER_INSTANCE_ID"));
        response.put("environment", env);

        // Discovery info
        List<String> services = discoveryClient.getServices();
        response.put("services", services);

        Map<String, Object> serviceDetails = new LinkedHashMap<>();
        for (String svc : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(svc);
            List<Map<String, Object>> instList = new ArrayList<>();
            for (ServiceInstance inst : instances) {
                Map<String, Object> instMap = new LinkedHashMap<>();
                instMap.put("instanceId", inst.getInstanceId());
                instMap.put("host", inst.getHost());
                instMap.put("port", inst.getPort());
                instMap.put("uri", inst.getUri().toString());
                instMap.put("isSecure", inst.isSecure());
                instMap.put("metadata", inst.getMetadata());

                // Test DNS resolution
                try {
                    InetAddress[] addrs = InetAddress.getAllByName(inst.getHost());
                    List<String> ips = new ArrayList<>();
                    for (InetAddress a : addrs) {
                        ips.add(a.getHostAddress());
                    }
                    instMap.put("dnsResolution", "OK: " + ips);
                } catch (Exception e) {
                    instMap.put("dnsResolution", "FAILED: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                }

                // Test HTTP reachability
                try {
                    URL testUrl = new URL(inst.getUri().toString() + "/actuator/health");
                    HttpURLConnection conn = (HttpURLConnection) testUrl.openConnection();
                    conn.setConnectTimeout(4000);
                    conn.setReadTimeout(4000);
                    int code = conn.getResponseCode();
                    instMap.put("httpPing", "HTTP " + code);
                } catch (Exception e) {
                    instMap.put("httpPing", "FAILED: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                }

                instList.add(instMap);
            }
            serviceDetails.put(svc, instList);
        }
        response.put("instances", serviceDetails);

        return response;
    }
}
