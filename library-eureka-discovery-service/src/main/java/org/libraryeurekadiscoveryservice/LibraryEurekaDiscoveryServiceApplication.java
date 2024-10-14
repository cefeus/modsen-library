package org.libraryeurekadiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LibraryEurekaDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryEurekaDiscoveryServiceApplication.class, args);
    }

}
