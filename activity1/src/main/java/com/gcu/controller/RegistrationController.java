package com.gcu.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.gcu.business.UserBusinessService;
import com.gcu.data.entity.UserEntity;

@Controller
@RequestMapping("/registration")
public class RegistrationController 
{
	 @Autowired
	 private UserBusinessService userService;
    
	/**
	 * Display Registration page
	 * 
	 * @param model
	 * @return
	 */
    @GetMapping("/")
    public String displayRegistration(Model model) 
    {     
        model.addAttribute("pageName", "New User Registration");
        model.addAttribute("userEntity", new UserEntity());
        
        return "registration";
    }
    
    /**
     * performs a submission of new user
     * 
     * @return
     */
    @PostMapping("/submitRegistration")
    public ModelAndView submitRegistration(@Valid UserEntity userEntity, BindingResult bindingResult, Model model) 
    {      
        ModelAndView mv = new ModelAndView(); 
        
        // Check if Username already exists.
        boolean existingUserError = false;
        if (userService.getUserByUsername(userEntity.getUsername()) != null)
        {
        	existingUserError = true;
        }
        
        // if registration submitted fields or 'username already exists' error occurs
        if (bindingResult.hasErrors() || existingUserError) 
        {
        	// notify user username already exists 
        	if (existingUserError)
        		mv.addObject("existingUserError", "Username already exists!"); 
        	
            mv.addObject("pageName", "New User Registration");
            mv.setViewName("registration");   
            return mv;
        }

        // Add new User to list of valid login credentials. 
        try
        {
        	userService.addUser(userEntity);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
        mv.addObject("pageName", "Login");
        mv.setViewName("redirect:/");
        return mv;
    }
}
