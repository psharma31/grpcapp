package com.bluebiktech.user;

import com.bluebiktech.apiservice.ApiServiceGrpc;
import com.bluebiktech.user.service.UserService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ApiServiceApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        /*Server server = ServerBuilder.forPort(6565)
                .addService(new UserService()).build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();*/
        SpringApplication.run(ApiServiceApplication.class,args);
    }

}
