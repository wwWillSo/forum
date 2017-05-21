package com.szw.crm.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szw.crm.entity.User;
import com.szw.crm.repository.UserRepository;

@Service
public class DataInit {

    @Autowired 
    private UserRepository userRepository;

//    @PostConstruct
    public void dataInit(){
        User admin = new User();
        admin.setPassword("admin");
        admin.setUsername("admin");
        admin.setRole(User.ROLE.admin);
        userRepository.save(admin);

        User user = new User();
        user.setPassword("user");
        user.setUsername("user");
        user.setRole(User.ROLE.user);
        userRepository.save(user);
        
        User customer = new User() ;
        customer.setPassword("customer");
        customer.setUsername("username");
        customer.setRole(User.ROLE.customer);
        userRepository.save(customer) ;
    }

}