package com.bluebiktech.user.repository;


import com.bluebiktech.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    
    User findByUserName(String userName);

    void deleteByUserName(String userName);

    @Modifying
    @Query("delete from User where userName in :userNameList")
    void deleteAllByUserName(@Param("userNameList") List<String> userNameList);

    @Modifying
    @Transactional
    @Query("update User u set u.firstName = :firstName , u.lastName = :lastName , u.email = :email , u.age = :age, u.phone = :phone, u.userName = :userName where u.userName = :userName")
    void updateUser(@Param("firstName")String firstName, @Param("lastName")String lastName, @Param("email")String email, @Param("age")int age, @Param("phone")String phone, @Param("userName")String userName);
}
