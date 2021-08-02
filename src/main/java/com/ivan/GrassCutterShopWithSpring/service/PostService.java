package com.ivan.GrassCutterShopWithSpring.service;

import com.ivan.GrassCutterShopWithSpring.model.Post;
import com.ivan.GrassCutterShopWithSpring.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Iterable<Post> findAll(){
        return postRepository.findAll();
    }

    public void savePost(Post post){
        postRepository.save(post);
    }

    public boolean existsById(long id){
        return postRepository.existsById(id);
    }

    public Optional <Post> findById(long id){
        return postRepository.findById(id);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }
}
