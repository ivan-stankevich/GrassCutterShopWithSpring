package com.ivan.GrassCutterShopWithSpring.controller;

import com.ivan.GrassCutterShopWithSpring.model.Post;
import com.ivan.GrassCutterShopWithSpring.model.User;
import com.ivan.GrassCutterShopWithSpring.repo.UserRepository;
import com.ivan.GrassCutterShopWithSpring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class PostController {
    private final UserRepository userRepository;
    private final PostService postService;

    @Autowired
    public PostController(UserRepository userRepository, PostService postService) {
        this.userRepository = userRepository;
        this.postService = postService;
    }

    @GetMapping("/blog/add")
    @PreAuthorize("hasAuthority('developers:write')")
    public String blogAdd(Model model, Principal principal) {
        String name = principal.getName();
        Optional<User> user = userRepository.findByEmail(name);
        if(user.isPresent()) {
            User user2 = user.get();
            String email = user2.getEmail();
            model.addAttribute("user", email);
        }

        return "blog-add";
    }

    @PostMapping("/blog/add")
    @PreAuthorize("hasAuthority('developers:write')")
    public String blogAddPost(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post newPost = new Post (title,anons,full_text);
        postService.savePost(newPost);
        return "redirect:/";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if(!postService.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> post = postService.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("title", "Post Details");
        model.addAttribute("post" , res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    @PreAuthorize("hasAuthority('developers:write')")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if(!postService.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> post = postService.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("title", "Post Details");
        model.addAttribute("post" , res);
        return "blog-edit";
    }
    @PostMapping("/blog/{id}/edit")
    @PreAuthorize("hasAuthority('developers:write')")
    public String blogUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = postService.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postService.savePost(post);
        return "redirect:/";
    }

    @PostMapping("/blog/{id}/remove")
    @PreAuthorize("hasAuthority('developers:write')")
    public String blogRemove(@PathVariable(value = "id") long id, Model model) {
        Post post = postService.findById(id).orElseThrow();
        postService.delete(post);
        return "redirect:/";
    }
}
