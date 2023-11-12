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
    public String display(UserEntity userEntity, Model model, Principal principal) 
    {
        model.addAttribute("pageName", "Home");
        model.addAttribute("username", principal.getName());
        model.addAttribute("userEntity", userService );
        
        return "home";
    }
    
    @PostMapping("/updateUser")
    public String updateUser(@Valid UserEntity userEntity, BindingResult bindingResult, Model model, Principal principal) 
    {    	
        boolean existingUserError = false;
        UserEntity user = userService.getUserByUsername(principal.getName());
        
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
        	userService.updateUser(user, userEntity);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
        model.addAttribute("pageName", "Home");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userEntity", user);
        
        if(userEntity.getUsername() != null && userEntity.getUsername() != "")
        {
        	return "redirect:/logout";
        }
        	
        return "redirect:/home";
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
