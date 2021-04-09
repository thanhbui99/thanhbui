package com.java.web.moviefinal2.controllers.admin;

import com.java.web.moviefinal2.dto.UserRegister;
import com.java.web.moviefinal2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class StaffController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/admin/manager-staff")
    public String getAllStaff(Model model, Principal principal){

        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        List<UserRegister> allStaff = iUserService.getAllStaff();
         model.addAttribute("listAll",allStaff);
        return "admin/staffManager";
    }

    @GetMapping("/admin/add-staff")
    public String addStaff(Model model, Principal principal){
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        UserRegister userRegister = new UserRegister();
        List<UserRegister> allStaff = iUserService.getAllStaff();
        model.addAttribute("userRegister",userRegister);
        model.addAttribute("listAll",allStaff);
        return "admin/RegisterStaff";
    }

    @GetMapping("/admin/delete-staff/{id}")
    public String deleteStaff(@PathVariable Long id){
        iUserService.deleteStaff(id);
        return "redirect:/admin/manager-staff";
    }

    @GetMapping("/admin/edit-staff/{id}")
    public String editStaff(@PathVariable Long id,Model model){

        model.addAttribute("userRegister",iUserService.getOneById(id));
        return "admin/RegisterStaff";
    }
}
