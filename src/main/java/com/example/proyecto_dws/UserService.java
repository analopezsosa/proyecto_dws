package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GradeRepository gradeRepository;



    public boolean newUser(User user){//add a user

        if (!userRepository.existsById(user.getId())){
            userRepository.save(user);
            return true;

        }else{
            return false;
        }

    }

    public boolean updateUser(User user){
        if (userRepository.existsById(user.getId())){

            userRepository.save(user);
            return true;
        }else{

            return false;
        }

    }

    public boolean removeUser(Long id){
        userRepository.deleteById(id);
        return true;
    }



}
