package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Here, load user from your database. For demo, using hardcoded user.
    	List<String> roles = new ArrayList<String>();
    	roles.add("ROLE_USER");
    	GrantedAuthority[] auths = {new SimpleGrantedAuthority("ROLE_USER")};
        if ("user".equals(username)) {
        	UserDetails usersDetail = new org.springframework.security.core.userdetails.User("user", "{noop}password", Arrays.asList(auths));
        	return usersDetail;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
