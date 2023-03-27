package com.bluebiktech.user.service;



import com.bluebiktech.apiservice.*;
import com.bluebiktech.user.Util;
import com.bluebiktech.user.entity.User;
import com.bluebiktech.user.repository.UserRepository;
import com.bluebiktech.user.search.SearchCriteria;
import com.bluebiktech.user.search.UserSpecification;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@GrpcService
public class UserService extends ApiServiceGrpc.ApiServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void getAllUsers(UserGetAllRequest request, StreamObserver<UserGetAllResponse> responseObserver) {
        UserGetAllResponse.Builder userResponseBuilder = UserGetAllResponse.newBuilder();
        List<User> userList = userRepository.findAll();
        List<userDTO> userDTOList = Util.convertUserListToUserDTOList(userList);
        userResponseBuilder.addAllUsers(userDTOList);
        responseObserver.onNext(userResponseBuilder.build());
        responseObserver.onCompleted();
    }


    @Override
    public void getUser(UserGetRequest request, StreamObserver<UserGetResponse> responseObserver) {
        UserGetResponse.Builder userResponseBuilder = UserGetResponse.newBuilder();
        User user = this.userRepository.findByUserName(request.getUserName());
        if(user != null)
        {
            userDTO userDto = Util.convertUserToUserDTOWithoutId(user);
            userResponseBuilder.setUser(userDto);
        }
        responseObserver.onNext(userResponseBuilder.build());
        responseObserver.onCompleted();
    }



    @Override
    public void createUser(UserCreateRequest request, StreamObserver<UserCreateResponse> responseObserver) {
        userDTO userDto = request.getUser();
        User user = Util.convertUserDTOToUser(userDto);
        User savedUser = this.userRepository.save(user);
        responseObserver.onNext(UserCreateResponse.newBuilder().setUser(Util.convertUserToUserDTO(savedUser)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void bulkCreateUsers(UserBulkCreateRequest request, StreamObserver<UserBulkCreateResponse> responseObserver) {
        List<userDTO> userDTOList = request.getUserList();
        List<User> userList = this.userRepository.saveAll(Util.convertUserDTOListToUserList(userDTOList));
        responseObserver.onNext(UserBulkCreateResponse.newBuilder().addAllUser(Util.convertUserListToUserDTOList(userList)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UserUpdateRequest request, StreamObserver<UserUpdateResponse> responseObserver) {
        userDTO userDto = request.getUser();
        this.userRepository.updateUser(userDto.getFirstName(),userDto.getLastName(),userDto.getEmail(),userDto.getAge(),userDto.getPhone(), userDto.getUserName());
        responseObserver.onNext(UserUpdateResponse.newBuilder().setUser(userDto).build());
        responseObserver.onCompleted();
    }

    @Override
    public void bulkUpdateUsers(UserBulkUpdateRequest request, StreamObserver<UserBulkUpdateResponse> responseObserver) {
        List<userDTO> userDTOList = request.getUserList();
        for(userDTO userDto : userDTOList)
        {
            this.userRepository.updateUser(userDto.getFirstName(),userDto.getLastName(),userDto.getEmail(),userDto.getAge(),userDto.getPhone(), userDto.getUserName());
        }
        responseObserver.onNext(UserBulkUpdateResponse.newBuilder().addAllUser(userDTOList).build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void deleteUser(UserDeleteRequest request, StreamObserver<UserDeleteResponse> responseObserver) {
        this.userRepository.deleteByUserName(request.getUserName());
        responseObserver.onNext(UserDeleteResponse.newBuilder().setResponseMessage("Deleted Successfully").build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void bulkDeleteUsers(UserBulkDeleteRequest request, StreamObserver<UserBulkDeleteResponse> responseObserver) {
        this.userRepository.deleteAllByUserName(request.getUserNameList());
        responseObserver.onNext(UserBulkDeleteResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void searchUsers(UserSearchRequest request, StreamObserver<UserSearchResponse> responseObserver) {
        String searchQuery = request.getSearchQuery();
        List<User> userList = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+)");
        Matcher matcher = pattern.matcher(searchQuery);
        Specification specification = null;
        if(matcher.find())
        {
        specification = Specification.where(new UserSpecification(new SearchCriteria(matcher.group(1),matcher.group(2),matcher.group(3))));
        }

        if(specification != null)
        {
            userList  = this.userRepository.findAll(specification);

        }
        responseObserver.onNext(UserSearchResponse.newBuilder().addAllUsers(Util.convertUserListToUserDTOList(userList)).build());
        responseObserver.onCompleted();
    }
}
