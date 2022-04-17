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



    public void newUser(String username, String password, String... roles){
        User user= new User(username,password,roles);
        userRepository.save(user);
    }

    public org.springframework.security.core.userdetails.User loadByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.loadByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> roles = new ArrayList<>();
        for(String role : user.getRoles()){
            roles.add(new SimpleGrantedAuthority("ROLE "+role ));
        }
        return new org.springframework.security.core.userdetails.User(user.getUser(),user.getPassword(),roles);
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
