package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;



    public void addUser(User username){//add a user

       userRepository.save(username);

    }


    public void removeUser(String username){
        User usertodelete= this.getUser(username);
        if(usertodelete!=null){
            userRepository.delete(usertodelete);
        }

    }
    public Collection<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(String username)   {


        Optional<User> present = userRepository.findById(username);

        if (present.isPresent()){
            return present.get();
        }else{
            return null;
        }

    }

}
