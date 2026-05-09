package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.user.UserRepository;
import com.example.user.grpc.GetUserRequest;
import com.example.user.grpc.GetUserResponse;
import com.example.user.grpc.UserQueryServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserQueryServiceGrpc.UserQueryServiceImplBase {

    private final UserRepository userRepository;

    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        long usrNo = request.getUsrNo();
        log.debug("gRPC GetUser 요청 수신: usrNo={}", usrNo);

        UserEntity user = userRepository.findByUsrNo(usrNo).orElse(null);
        if (user == null) {
            log.warn("사용자를 찾을 수 없음: usrNo={}", usrNo);
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("user not found: usrNo=" + usrNo)
                    .asRuntimeException());
            return;
        }

        GetUserResponse response = GetUserResponse.newBuilder()
                .setUsrNo(user.getUsrNo())
                .setUsrId(nullSafe(user.getUsrId()))
                .setUsrNm(nullSafe(user.getUsrNm()))
                .setUsrEmail(nullSafe(user.getUsrEmail()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private static String nullSafe(String value) {
        return value == null ? "" : value;
    }
}
