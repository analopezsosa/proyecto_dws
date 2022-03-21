package com.example.proyecto_dws;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserHolder {
    private Map<String,User> usersM= new ConcurrentHashMap<>();
    private AtomicLong lastID=new AtomicLong();

    public void addUser (String username, User user){
        usersM.put(username, user);
    }

    public Collection<User> getUsers(){
        return usersM.values();
    }
    public User getUser(String username){
        return usersM.get(username);
    }


}
