package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> userByUsername(String username) {
        return userRepository.userByUsername(username);
    }
    public List<User> userByLastname(String lastName) {
        return userRepository.userByLastname(lastName);
    }



    public void addUser(User username){

       userRepository.save(username);

    }


    public User removeUser(String username){
        User usertodelete= this.getUser(username);
        if(usertodelete!=null){
            userRepository.delete(usertodelete);
        }
        return usertodelete;

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
    public User updateUser(String username,User user){
        user.setUser(username);
        return userRepository.save(user);
    }



}
