package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;



    public boolean addUser(User user){//add a user

        if (!userRepository.existsById(user.getId())){
            //if the user isnt already
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
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
        return true;
    }
    public Collection<User> getUsers(){
        return userRepository.findAll();
    }



}
