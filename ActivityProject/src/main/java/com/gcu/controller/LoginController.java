package com.gcu.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.gcu.business.UserBusinessService;
import com.gcu.data.entity.UserEntity;
import com.gcu.model.UserModel;

@Controller
public class LoginController
{	
	@Autowired
	private UserBusinessService userService;

	@GetMapping("/")
	public String displayLogin(Model model)	
	{
		model.addAttribute("pageName", "Welcome!");
		return "login";
	}

	@GetMapping("/registration")
	public String newUser(Model model)
	{
	    model.addAttribute("pageName", "New User Registration");
	    model.addAttribute("userEntity", new UserModel());
	    return "registration";
	} 

    @GetMapping("/home")
    public String display(Model model, Principal principal) 
    {   
    	UserEntity user = userService.getUserByUsername(principal.getName());
        model.addAttribute("pageName", "Home");
        model.addAttribute("username", principal.getName());
        model.addAttribute("userEntity", user);
        
        return "home";
    }
	
}
