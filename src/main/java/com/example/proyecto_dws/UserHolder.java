package com.example.proyecto_dws;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserHolder {
    private Map<Long,User> users= new ConcurrentHashMap<>();
    private AtomicLong lastID=new AtomicLong();

    public User addUser (User user){
        long id=lastID.incrementAndGet();
        user.setId(id);
        users.put(id,user);
        return user;
    }
    public Collection<User> getUsers(){
        return users.values();
    }
    public User getUser(long id){
        return users.get(id);
    }

}
