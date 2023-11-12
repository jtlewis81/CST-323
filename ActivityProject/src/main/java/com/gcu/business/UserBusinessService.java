package com.gcu.business;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.gcu.data.UserDataAccessService;
import com.gcu.data.entity.UserEntity;

@Component
@Service
public class UserBusinessService implements UserBusinessServiceInterface, UserDetailsService
{
	Logger logger = LoggerFactory.getLogger(UserBusinessService.class);

	@Autowired
	private UserDataAccessService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = userService.getUserByUsername(username);
		
		if(user != null)
		{
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			
			authorities.add(new SimpleGrantedAuthority("USER"));
			
			return new User(user.getUsername(), user.getPassword(), authorities);
		}
		else
		{
			throw new UsernameNotFoundException("username not found");
		}
	}

	@Override
	public List<UserEntity> getAllUsers()
	{
		return userService.getAllUsers();
	}

	@Override
	public UserEntity getUserByUsername(String username)
	{
		return userService.getUserByUsername(username);
	}
	
	@Override
	public UserEntity getUserById(int id)
	{
		return userService.getUserById(id);
	}

	@Override
	public boolean addUser(UserEntity userEntity)
	{
    	logger.info("[LOGGER] : Adding User : {}", userEntity.getUsername());
    	
		return userService.add(userEntity);
	}

	@Override
	public boolean updateUser(UserEntity userEntity, UserEntity updatedUser)
	{
    	logger.info("[LOGGER] : Updating User : {}", userEntity.getUsername());
    	
		return userService.update(userEntity, updatedUser);
	}

	@Override
	public boolean deleteUser(UserEntity userEntity)
	{
    	logger.info("[LOGGER] : Deleting User : {}", userEntity.getUsername());
    	
		return userService.delete(userEntity);
	}
}
