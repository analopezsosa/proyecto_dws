package com.example.proyectodws;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    @Query(value = "select * from user where user.user=?1", nativeQuery = true)
    List<User> userByUsername(@Param("username") String username);

    @Query(value = "select * from user where user.last_name=?1", nativeQuery = true)
    List<User> userByLastname(@Param("lastName") String lastName);


}
