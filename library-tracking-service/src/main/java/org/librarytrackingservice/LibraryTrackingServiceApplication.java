package org.librarytrackingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LibraryTrackingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryTrackingServiceApplication.class, args);
    }

}
