package com.example.gamedemo.server;


import com.example.gamedemo.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public interface UserService {
    List<UserEntity> getAll() throws ExecutionException;


    Optional<UserEntity> getUser(Long userId) throws IOException;

    boolean findUserByEmail(String email);


    void addUser(UserEntity userEntity);

    void delete(Long userId);


    UserEntity getUserByUsername(String email);
}
