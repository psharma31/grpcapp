package com.bluebiktech.user;

import com.bluebiktech.apiservice.userDTO;
import com.bluebiktech.user.dto.User;

import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static User convertUserDTOToUser(userDTO userDto)
    {
       return  new User(userDto.getUserName(),userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),userDto.getPhone(),userDto.getAge());
    }

    public static List<User>  convertUserDTOListToUserList(List<userDTO> userDTOList)
    {
        return userDTOList.stream().map(userDto -> new User (userDto.getUserName(),userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),userDto.getPhone(),userDto.getAge()))
                .collect(Collectors.toList());
    }

    public static List<userDTO>  convertUserListToUserDTOList(List<User> userList)
    {
        return userList.stream().map(user -> userDTO.newBuilder().setUserName(user.getUserName())
                        .setFirstName(user.getFirstName()).setLastName(user.getLastName()).setEmail(user.getEmail())
                        .setPhone(user.getPhone()).setAge(user.getAge()).build())
                .collect(Collectors.toList());
    }

    public static userDTO convertUserToUserDTO(User user) {

        return userDTO.newBuilder().setUserName(user.getUserName())
                .setFirstName(user.getFirstName()).setLastName(user.getLastName()).setEmail(user.getEmail()).setPhone(user.getPhone()).setAge(user.getAge()).build();
    }
}
