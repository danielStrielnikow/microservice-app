package pl.danielstrielnikow.jobmicroservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobMicroservicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobMicroservicesApplication.class, args);
    }

}
