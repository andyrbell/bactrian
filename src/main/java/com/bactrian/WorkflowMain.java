package com.bactrian;

//import org.apache.camel.cdi.ImportResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = "com.bactrian")
@ImportResource("classpath:camel-context.xml")
public class WorkflowMain {
    public static void main(String[] args) {
        SpringApplication.run(WorkflowMain.class, args);
    }
}
