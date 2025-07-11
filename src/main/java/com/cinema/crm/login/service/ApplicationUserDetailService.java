package com.cinema.crm.login.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cinema.crm.databases.pvrinoxcrm.entities.Users;
import com.cinema.crm.databases.pvrinoxcrm.repositories.UserRepository;
import com.cinema.crm.login.model.MyUserDetails;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ApplicationUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users= userRepository.findByEmail(username);
		if(Objects.isNull(users)) {
			log.debug("user did not founded with name : {}",username);

			throw new UsernameNotFoundException(username + " USER DID NOT FOUNDED!");
		}
		
		return new MyUserDetails(users);
	}

}
