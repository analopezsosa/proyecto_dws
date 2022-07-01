package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseUsersLoder {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initDataBase(){
        List<String> listRoles = new ArrayList<>();
        List<String> listUser = new ArrayList<>();
        listUser.add("USER");
        listRoles.add("USER");
        listRoles.add("ADMIN");
        /*
        userRepository.save(new User("user",passwordEncoder.encode("pass"),"userlastname",listUser));
        userRepository.save(new User("admin",passwordEncoder.encode("adminpass"),"admin",listRoles));
        */
        User user = new User("admin",passwordEncoder.encode("adminpass"),"adminlastname",listRoles);
        userService.addUser(user);
    }
}
