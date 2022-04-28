package com.example.proyectodws;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    //List<User> findDistinctPeopleByUserNameOrLastName(String username, String LastName);

    @Query(value = "select * from user where user.user_username=?1", nativeQuery = true)
    List<User> userByUsername(@Param("username") String username);

    @Query(value = "select * from user where user.user_lastname=?1", nativeQuery = true)
    List<User> userByLastname(@Param("lastName") String lastName);


}
