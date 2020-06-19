package com.example.gamedemo.server.impl;

import com.example.gamedemo.entity.UserEntity;
import com.example.gamedemo.mapper.UserMapper;
import com.example.gamedemo.repo.redis.UserRedis;
import com.example.gamedemo.repo.userrepo.UserRepo;
import com.example.gamedemo.server.UserService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepo userRepo;
    private final UserRedis userRedis;
    private final UserMapper userMapper ;
    private final LoadingCache<String,List<UserEntity>> usersCache;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserRedis userRedis, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userRedis = userRedis;
        this.userMapper = userMapper;
        this.usersCache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<String, List<UserEntity>>() {
                    @Override
                    public List<UserEntity> load(String key) throws Exception {
                        return UserServiceImpl.this.findAllUserFromDb();
                    }
                });
    }

    private List<UserEntity> findAllUserFromDb() {

        return this.userRepo.findAll();
    }


    @Override
    public List<UserEntity> getAll() throws ExecutionException {
        usersCache.put("all",userRepo.findAll());
        return this.usersCache.get("all");
    }

    @Override
    public Optional<UserEntity> getUser(Long userId) throws IOException {

        Optional<UserEntity> userEntity = userRedis.getUser(userId+"");
        if (!userEntity.isPresent()){
            userEntity =userRepo.findById(userId);
            if (userEntity.isPresent()){
                userRedis.addUser(userId+"",userEntity.get());
            }
        }
        return userRepo.findById(userId);
    }

    @Override
    public boolean findUserByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public void addUser(UserEntity userEntity) {
        userRedis.delete(userEntity.getId());
        userRepo.save(userEntity);
    }

    @Override
    public void delete(Long userId) {
        this.userRedis.delete(userId);
        this.userRepo.deleteById(userId);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }


}
