package com.gcu.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gcu.business.UserBusinessService;
import com.gcu.data.entity.UserEntity;

@Controller
@RequestMapping("/home")
public class HomeController 
{
	// VARIABLES 
    @Autowired
    private UserBusinessService userService;
    
    // display home page 
    @GetMapping("/")
    public String display(Model model, Principal principal) 
    {   
    	UserEntity user = userService.getUserByUsername(principal.getName());
        model.addAttribute("pageName", "Home");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userEntity", user);
        
        return "home";
    }
}
