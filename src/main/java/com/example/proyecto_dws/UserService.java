package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GradeRepository gradeRepository;



    public void newUser(String username, String password){
        User user= new User(username,password);
        userRepository.save(user);
    }
}
