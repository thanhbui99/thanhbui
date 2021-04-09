package com.java.web.moviefinal2.controllers.admin;

import com.java.web.moviefinal2.converter.UserConverter;
import com.java.web.moviefinal2.dto.UserEdit;
import com.java.web.moviefinal2.dto.UserRegister;
import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.repository.UserRepository;
import com.java.web.moviefinal2.service.IUserService;
import com.java.web.moviefinal2.utils.EncrytedPasswordUtils;
import com.java.web.moviefinal2.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class isLogin {

    public static final Logger log = LoggerFactory.getLogger(isLogin.class);

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    private IUserService iUserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        return "admin/adminLogin";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        List<UserRegister> list = new ArrayList<>();
        AppUser user = userRepository.findByUserName(principal.getName());
        UserRegister userRegister = userConverter.convertToDTOno(user);
        list.add(userRegister);
        // Sau khi user login thanh cong se co principal
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        UserEdit userEdit = new UserEdit();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userEdit", userEdit);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("list", list);

        return "web/userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String PageNot4(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "web/404Page";
    }


    @PostMapping("/register-handle")
    public String registerUser(@ModelAttribute UserRegister userRegister, Model model) {
        if (iUserService.getByUserName(userRegister.getUserName()) && iUserService.getByEmail(userRegister.getEmail())) {
            model.addAttribute("message1", iUserService.getByUserName(userRegister.getUserName()));
            model.addAttribute("message2", iUserService.getByEmail(userRegister.getEmail()));
            return "register";
        }
        if (iUserService.getByUserName(userRegister.getUserName())) {
            model.addAttribute("message1", iUserService.getByUserName(userRegister.getUserName()));
            return "register";
        }
        if (iUserService.getByUserName(userRegister.getEmail())) {
            model.addAttribute("message2", iUserService.getByEmail(userRegister.getEmail()));
            return "register";
        }
        userRegister.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(userRegister.getPassword()));
        iUserService.save(userRegister);
        return "admin/adminLogin";
    }

    @PostMapping("/register-handle-staff")
    public String registerStaff(@Valid @ModelAttribute UserRegister userRegister, Principal principal, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "redirect:/admin/add-staff";
            }

            log.info(">> userRegister : {}", userRegister.toString());
            userRegister.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(userRegister.getPassword()));
            if (principal != null && principal.getName().equals("admin")) ;
            {
                iUserService.saveStaff(userRegister);
                return "redirect:/admin/manager-staff";
            }
        } catch (Exception e) {
            return "redirect:/404";
        }

    }

}

