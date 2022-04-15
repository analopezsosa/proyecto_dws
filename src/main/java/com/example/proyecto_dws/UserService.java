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


    /* LO QUE HABIA EN EL HOLDER


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

     */

}
