package com.gcu.controller;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.gcu.business.UserBusinessService;
import com.gcu.data.entity.UserEntity;
import com.gcu.model.UserModel;

@Component
@Controller
public class LoginController
{
	Logger logger = LoggerFactory.getLogger(LoginController.class);

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
    	logger.info("[LOGGER] : Logging In User : {}", principal.getName());
    	
    	UserEntity user = userService.getUserByUsername(principal.getName());
        model.addAttribute("pageName", "Home");
        model.addAttribute("username", principal.getName());
        model.addAttribute("userEntity", user);
        
        return "home";
    }
	
}
