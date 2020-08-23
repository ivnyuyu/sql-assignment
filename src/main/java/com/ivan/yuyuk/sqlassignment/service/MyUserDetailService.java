package com.ivan.yuyuk.sqlassignment.service;

import com.ivan.yuyuk.sqlassignment.entity.MyUserDetails;
import com.ivan.yuyuk.sqlassignment.entity.User;
import com.ivan.yuyuk.sqlassignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        return user.map(MyUserDetails::new).get();
    }

    private  MyUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            return (MyUserDetails) principal;
        }
        return null;
    }

    public User getUser() {
        MyUserDetails userFromSession = getCurrentUser();
        Optional<User> user = userRepository.findByUserName(userFromSession.getUsername());
        return user.get();
    }
}
