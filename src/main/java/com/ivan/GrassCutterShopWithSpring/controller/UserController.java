package com.ivan.GrassCutterShopWithSpring.controller;

import com.ivan.GrassCutterShopWithSpring.model.Post;
import com.ivan.GrassCutterShopWithSpring.model.Role;
import com.ivan.GrassCutterShopWithSpring.model.Status;
import com.ivan.GrassCutterShopWithSpring.model.User;
import com.ivan.GrassCutterShopWithSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @GetMapping("/user")
    public String userListPage(Model model){
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user/add")
    public String addUserPage(Model model){
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        roles.add(Role.USER);
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.ACTIVE);
        statuses.add(Status.BANNED);
        model.addAttribute("status", statuses);
        model.addAttribute("role", roles);
        return "user-add";
    }

    @PostMapping("/user/add")
    public String addUserPost(@RequestParam String email, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String password,
                              @RequestParam Role role, @RequestParam Status status, Model model){
        String newPass = passwordEncoder.encode(password);
        User newUser = new User(email, firstName, lastName, newPass, role, status);
        userService.saveUser(newUser);
        return "redirect:/";
    }

    @GetMapping("/user/{id}")
    public String userDetails(@PathVariable(value = "id") long id, Model model) {
        if(!userService.existsById(id)){
            return "redirect:/";
        }
        Optional<User> user = userService.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("title", "User Details");
        model.addAttribute("user" , res);
        return "user-details";
    }

    @GetMapping("/user/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if(!userService.existsById(id)){
            return "redirect:/";
        }
        Optional<User> user = userService.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        roles.add(Role.USER);
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.ACTIVE);
        statuses.add(Status.BANNED);
        model.addAttribute("status", statuses);
        model.addAttribute("role", roles);
        model.addAttribute("title", "User Edit");
        model.addAttribute("user" , res);
        return "user-edit";
    }

    @PostMapping("/user/{id}/edit")
    public String blogUpdate(@PathVariable(value = "id") long id, @RequestParam String email, @RequestParam String firstName,
                             @RequestParam String lastName, @RequestParam String password,
                             @RequestParam Role role, @RequestParam Status status, Model model){
        User user = userService.findById(id).orElseThrow();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setStatus(status);
        userService.saveUser(user);
        return "redirect:/user";
    }

    @PostMapping("/user/{id}/remove")
    public String blogRemove(@PathVariable(value = "id") long id, Model model) {
        User user = userService.findById(id).orElseThrow();
        userService.delete(user);
        return "redirect:/user";
    }

    @GetMapping("/user/registration")
    public String registrationForm(){
        return "user-registration";
    }

    @PostMapping("/user/registration")
    public String registration(@RequestParam String email, @RequestParam String firstName,
                               @RequestParam String lastName, @RequestParam String password){
        String newPass = passwordEncoder.encode(password);
        User newUser = new User (email,firstName,lastName,newPass, Role.USER, Status.ACTIVE);
        userService.saveUser(newUser);
        return "redirect:/";
    }

}
