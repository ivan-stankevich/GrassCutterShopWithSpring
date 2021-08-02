package com.ivan.GrassCutterShopWithSpring.controller;

import com.ivan.GrassCutterShopWithSpring.model.GrassCutterBrand;
import com.ivan.GrassCutterShopWithSpring.model.Post;
import com.ivan.GrassCutterShopWithSpring.repo.UserRepository;
import com.ivan.GrassCutterShopWithSpring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {

    private final PostService postService;

    @Autowired
    public MainController(UserRepository userRepository, PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/main")
    public String mainPageShow(Model model) {
        Iterable <Post> posts = postService.findAll();
        model.addAttribute("title", "Main Page");
        model.addAttribute("posts", posts);
        return "main";
    }

    @RequestMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/about")
    @PreAuthorize("hasAuthority('developers:read')")
    public String aboutPage(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }

}
