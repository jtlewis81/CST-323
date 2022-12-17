package com.gcu.controller;

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
@RequestMapping("/registration")
public class RegistrationController 
{
	@Autowired
	private UserBusinessService userService;
    
    @GetMapping("/")
    public String displayRegistration(Model model) 
    {     
        model.addAttribute("pageName", "New User Registration");
        model.addAttribute("userEntity", new UserEntity());
        
        return "registration";
    }
    
    @PostMapping("/submitRegistration")
    public String submitRegistration(@Valid UserEntity userEntity, BindingResult bindingResult, Model model) 
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
        	
        	model.addAttribute("pageName", "New User Registration"); 
            return "registration";
        }

        try
        {
        	userService.addUser(userEntity);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
        model.addAttribute("pageName", "Login");
        return "redirect:/";
    }
}
