package com.example.demo.service;

import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void getUser(UserRequest request,
                        StreamObserver<UserResponse> responseObserver) {

        UserResponse response = UserResponse.newBuilder()
                .setUserId(request.getUserId())
                .setName("Soobin")
                .setEmail("soobin@test.com")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
