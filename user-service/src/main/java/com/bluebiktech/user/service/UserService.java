package com.bluebiktech.user.service;

import com.bluebiktech.apiservice.*;
import com.bluebiktech.user.Util;
import com.bluebiktech.user.dto.User;
import com.bluebiktech.user.exception.UserException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @GrpcClient("api-service")
    private ApiServiceGrpc.ApiServiceBlockingStub blockingStub;

    public List<User> getAllUsers()
    {
        UserGetAllRequest request = UserGetAllRequest.newBuilder().build();
        UserGetAllResponse response = blockingStub.getAllUsers(request);
        return response.getUsersList().stream().map(userDto -> new User(userDto.getUserName(), userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPhone(),userDto.getAge()))
                .collect(Collectors.toList());
    }


    public User getUser(String userName) {

        UserGetRequest request = UserGetRequest.newBuilder().setUserName(userName).build();
        UserGetResponse userResponse = blockingStub.getUser(request);
        if(userResponse != null && !userResponse.getUser().getUserName().equals("")) {
            userDTO userDto = userResponse.getUser();
            return new User(userDto.getUserName(), userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPhone(), userDto.getAge());
        }
        else
            throw new UserException("User with userName :" + userName + " does not exists.");
        }



    public User createUser(User user)
    {
        if(isUserExists(user.getUserName()))
            throw new UserException("User with userName : " + user.getUserName() + " : already exists");
        UserCreateRequest userCreateRequest = UserCreateRequest.newBuilder().setUser(Util.convertUserToUserDTO(user)).build();
        UserCreateResponse response = blockingStub.createUser(userCreateRequest);
        return Util.convertUserDTOToUser(response.getUser());
    }

    public List<User> bulkCreateUser(List<User> users)
    {
        UserBulkCreateRequest userCreateRequest = UserBulkCreateRequest.newBuilder().addAllUser(Util.convertUserListToUserDTOList(users)).build();
        UserBulkCreateResponse response = blockingStub.bulkCreateUsers(userCreateRequest);
        return Util.convertUserDTOListToUserList(response.getUserList());
    }

    public User updateUser(User user)
    {
        if(isUserExists(user.getUserName())) {
            UserUpdateRequest userUpdateRequest = UserUpdateRequest.newBuilder().setUser(Util.convertUserToUserDTO(user)).build();
            UserUpdateResponse response = blockingStub.updateUser(userUpdateRequest);
            return Util.convertUserDTOToUser(response.getUser());
        }
        else
            throw new UserException("Cannot update as user does not exists");
    }

    public List<User> bulkUpdateUser(List<User> users)
    {
        UserBulkUpdateRequest userUpdateRequest = UserBulkUpdateRequest.newBuilder().addAllUser(Util.convertUserListToUserDTOList(users)).build();
        UserBulkUpdateResponse response = blockingStub.bulkUpdateUsers(userUpdateRequest);
        return Util.convertUserDTOListToUserList(response.getUserList());
    }

    public String deleteUser(String userName)
    {
        UserDeleteRequest userdeleteRequest = UserDeleteRequest.newBuilder().setUserName(userName).build();
        UserDeleteResponse response = blockingStub.deleteUser(userdeleteRequest);
        return response.getResponseMessage();
    }



    public List<String> bulkDeleteUser(List<String> users)
    {
        UserBulkDeleteRequest userDeleteRequest = UserBulkDeleteRequest.newBuilder().addAllUserName(users).build();
        UserBulkDeleteResponse response = blockingStub.bulkDeleteUsers(userDeleteRequest);
        return response.getResponseMessageList();
    }
    public List<User> searchUsers(String searchQuery)
    {
        UserSearchRequest searchRequest = UserSearchRequest.newBuilder().setSearchQuery(searchQuery).build();
        UserSearchResponse searchResponse = blockingStub.searchUsers(searchRequest);
        return Util.convertUserDTOListToUserList(searchResponse.getUsersList());
    }

    private boolean isUserExists(String userName)
    {
        boolean isExist = false;
        try {
            User user = getUser(userName);
            if(user != null)
                isExist = true;
        }catch(UserException ex)
        {
            isExist = false;
        }
        return isExist;
    }
}
