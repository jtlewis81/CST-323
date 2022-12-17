package com.gcu.api;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gcu.business.UserBusinessService;
import com.gcu.data.entity.UserEntity;

@RestController
@RequestMapping("/api/user")
public class UserRestService
{
	@Autowired
	UserBusinessService service;
	
	@GetMapping(path="/get")
	public ResponseEntity<?> getUser(@RequestParam String username, Principal principal)
	{
		try
		{
			UserEntity user = service.getUserByUsername(username);
			
			if (user == null)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else if (username.equals(principal.getName()))
			{
				return new ResponseEntity<>(user, HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/add")
	public ResponseEntity<?> addUser(@RequestParam String username, @RequestParam String password, @RequestParam String email)
	{
		try
		{
			UserEntity user = new UserEntity(username, password, email);
			
			if (service.getUserByUsername(username) == null)
			{
				if((username != "" || username != null) &&
						(password != "" || password != null) &&
						(email != "" || email != null) &&
						service.addUser(user))
				{
					return new ResponseEntity<>(HttpStatus.OK);
				}
				else
				{
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	@GetMapping("/update")
	public ResponseEntity<?> update(@RequestParam String authName, @RequestParam String username, @RequestParam String password, @RequestParam String email, Principal principal)
	{
		try
		{
			UserEntity user = service.getUserByUsername(principal.getName());
			
			if (user == null)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else if (authName.equals(principal.getName()))
			{
				UserEntity updatedUser = new UserEntity(user.getId(), username, password, email);
				if(service.updateUser(user, updatedUser))
				{
					return new ResponseEntity<>(HttpStatus.OK);
				}
				else
				{
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(path="/delete")
	public ResponseEntity<?> deleteUser(@RequestParam String username, Principal principal)
	{
		try
		{
			UserEntity user = service.getUserByUsername(username);
			
			if (user == null)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else if (username.equals(principal.getName()))
			{
				if(service.deleteUser(user))
				{
					return new ResponseEntity<>(HttpStatus.OK);
				}
				else
				{
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
