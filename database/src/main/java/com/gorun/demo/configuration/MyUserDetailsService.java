package com.gorun.demo.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.gorun.demo.dao.ClubRepo;
import com.gorun.demo.model.Club;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	ClubRepo clubRepo;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		Club club = clubRepo.findByName(name).get(0);
		System.out.println("Authentication for: " + club);
		if (club!=null) {
			return new User(club.getName(), club.getPassword(),
                    new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("User not found with name: " + name);
		}
		
	}

}
