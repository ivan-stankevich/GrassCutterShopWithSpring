package com.ivan.GrassCutterShopWithSpring.service;

import com.ivan.GrassCutterShopWithSpring.model.User;
import com.ivan.GrassCutterShopWithSpring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public boolean existsById(long id){
        return userRepository.existsById(id);
    }

    public void delete(User user){
        userRepository.delete(user);
    }


}
