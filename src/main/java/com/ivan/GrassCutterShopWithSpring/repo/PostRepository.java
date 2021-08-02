package com.ivan.GrassCutterShopWithSpring.repo;

import com.ivan.GrassCutterShopWithSpring.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PostRepository extends JpaRepository<Post, Long> {

}
