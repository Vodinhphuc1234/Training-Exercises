/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exercise01;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 *
 * @author vodinhphuc
 */
public class Cache {
    private static Cache instance;
    private HashMap<Integer,User> users;
    private static LocalDateTime start;
    
    public static Cache getInstance(){
        
        if (instance==null) 
            instance=new Cache();
        else{
                long duration = Duration.between(start, LocalDateTime.now()).toHours();
                if (duration > Constant.EXPIRED_TIME_CACHE)
                    instance=new Cache();
            }
        return instance;
    }
    
    private Cache (){
        this.users = new HashMap<>();
        start=LocalDateTime.now();
    }
    
    public User getUser(int id)
    {
        User user = users.get(id);
        return user;
    }
    
    public boolean checkUser(int id)
    {
        boolean existed=users.keySet().contains(id);
        return existed;
    }
    
    public void addUser(User user)
    {
        users.put(user.getId(), user);
    }
    
     public void removeUser(int id)
     {
        users.remove(id);
    }
     
    public void updateUser(User user)
    {
        users.remove(user.getId());
        users.put(user.getId(), user);
    }
}
