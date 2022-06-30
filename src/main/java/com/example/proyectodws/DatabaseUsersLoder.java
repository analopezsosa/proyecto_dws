package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUsersLoder {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initDataBase(){
        List<String> listRoles = new ArrayList<>();
        listRoles.add("USER");
        listRoles.add("ADMIN");
        userRepository.save(new User("user",passwordEncoder.encode("pass"),"USER"));
        userRepository.save(new User("admin",passwordEncoder.encode("adminpass"),"admin",listRoles));

    }
}
