package com.bluebiktech.user.controller;

import com.bluebiktech.user.dto.User;
import com.bluebiktech.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers()
    {
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/userName/{userName}")
    public ResponseEntity<User>  getUser(@PathVariable("userName") String userName)
    {
        return new ResponseEntity(userService.getUser(userName),HttpStatus.OK);
    }



    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid  @RequestBody User user)
    {
        return new ResponseEntity(userService.createUser(user),HttpStatus.CREATED);
    }

    @PostMapping("/user/bulk")
    public ResponseEntity<List<User>> bulkCreate(@Valid @RequestBody List<User> userList)
    {
        return new ResponseEntity(userService.bulkCreateUser(userList),HttpStatus.CREATED);
    }

    @PutMapping("/user")
    public ResponseEntity<User>  updateUser(@Valid @RequestBody User user)
    {
        return new ResponseEntity(userService.updateUser(user),HttpStatus.OK);
    }

    @PutMapping("/user/bulk")
    public ResponseEntity<List<User>> bulkUpdate(@Valid @RequestBody List<User> userList)
    {
        return new ResponseEntity(userService.bulkUpdateUser(userList),HttpStatus.OK);
    }

    @DeleteMapping("/user/userName/{userName}")
    public ResponseEntity<String> deleteUser(@PathVariable("userName") String userName)
    {
        return new ResponseEntity(userService.deleteUser(userName),HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/user/bulk")
    public ResponseEntity<String> bulkDelete(@RequestBody List<String> userNameList)
    {
        return new ResponseEntity(userService.bulkDeleteUser(userNameList),HttpStatus.NO_CONTENT);
    }


    @GetMapping("/user/search")
    public ResponseEntity<List<User>>  search(@RequestParam("search") String searchQuery)
    {
        return new ResponseEntity(userService.searchUsers(searchQuery),HttpStatus.OK);
    }





    @GetMapping("/hello")
    public String sayHello()
    {
        return "hello there";
    }

}
