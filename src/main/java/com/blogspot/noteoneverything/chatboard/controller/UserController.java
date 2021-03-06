package com.blogspot.noteoneverything.chatboard.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import org.springframework.validation.BindingResult;

import com.blogspot.noteoneverything.chatboard.dao.UserRepository;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.service.UserService;
import com.blogspot.noteoneverything.chatboard.service.SecurityService;
import com.blogspot.noteoneverything.chatboard.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if(error != null){
            model.addAttribute("message", "Your username or password is invalid.");
            model.addAttribute("alertClass", "text-danger");
        }else if (logout != null){
            model.addAttribute("message", "You have been logged out successfully.");
            model.addAttribute("alertClass", "text-success");
        }
        model.addAttribute("user", new User());
        return "users/login";
    }

    @GetMapping(value = "/create_user")
    public String createUser(Model model){
        model.addAttribute("user", new User());
        return "users/create_user";
    }

    @PostMapping(value = "/create_user")
    public String createUser(
            @Valid @ModelAttribute("user") User user,
            RedirectAttributes redirectAttributes,
            BindingResult bindingResult,
            Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "users/create_user";
        }
        if(userService.createUser(user)){  
            redirectAttributes.addFlashAttribute("message", "Success. User created. You can login with the user name now.");
            redirectAttributes.addFlashAttribute("alertClass", "text-success");
        }else{
            redirectAttributes.addFlashAttribute("message", "Sorry user could not be created. Please contact the administarator.");
            redirectAttributes.addFlashAttribute("alertClass", "text-danger");
        }
        return "redirect:/login";
    }
}