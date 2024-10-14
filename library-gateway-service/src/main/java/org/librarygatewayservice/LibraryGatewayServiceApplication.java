package org.librarygatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LibraryGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryGatewayServiceApplication.class, args);
    }

}
