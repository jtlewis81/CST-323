package com.gcu.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gcu.business.UserBusinessService;
import com.gcu.data.entity.UserEntity;

@Controller
@RequestMapping("/home")
public class HomeController 
{
    @Autowired
    private UserBusinessService userService;
    
    @GetMapping("/")
    public String display(Model model, Principal principal) 
    {
    	UserEntity user = userService.getUserByUsername(principal.getName());
        model.addAttribute("pageName", "Home");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userEntity", user);
        
        return "home";
    }
    
    @PostMapping("/updateUser")
    public String updateUser(@Valid UserEntity userEntity, BindingResult bindingResult, Model model, Principal principal) 
    {
        boolean existingUserError = false;
        if (userService.getUserByUsername(userEntity.getUsername()) != null)
        {
        	existingUserError = true;
        }
        
        if (bindingResult.hasErrors() || existingUserError) 
        {
        	if (existingUserError)
        	{
        		model.addAttribute("existingUserError", "Username already exists!"); 
        	}
      
        	model.addAttribute("pageName", "home");   
            return "redirect:/home";
        }

        try
        {
        	userService.updateUser(userService.getUserByUsername(principal.getName()), userEntity);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
        return "redirect:/logout";
    }
    
    @GetMapping("/deleteUser")
    public String deleteUser(Model model, Principal principal)
    {
    	try
        {
        	userService.deleteUser(userService.getUserByUsername(principal.getName()));
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
        return "redirect:/logout";
    }
    
}
