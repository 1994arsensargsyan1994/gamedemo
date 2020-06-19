package com.example.gamedemo.repo.redis;


import com.example.gamedemo.dto.UserDto;
import com.example.gamedemo.entity.UserEntity;
import com.example.gamedemo.repo.userrepo.UserRepo;
import com.example.gamedemo.util.JsonUtil;
import com.example.gamedemo.util.Validator;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;

@Repository
public class UserRedis {


    private final HashOperations hashOperations;

    private final RedisTemplate redisTemplate;
    private final UserRepo userRepo;

    @Autowired
    public UserRedis(RedisTemplate redisTemplate, UserRepo userRepo) {
        this.redisTemplate = redisTemplate;
        this.userRepo = userRepo;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void addUser(String key, UserEntity user) {
       String jsonUserValue = JsonUtil.serialise(user);
             hashOperations.put("USER", key, jsonUserValue);
    }

    public Optional<UserEntity> getUser(String key) throws IOException {

            String userInfoJson = (String) hashOperations.get("USER",key);
            if (!Validator.isEmpty(userInfoJson)) {
                return Optional.of(JsonUtil.deserialize(userInfoJson, new TypeReference<UserEntity>() {
                }));
            }

        return Optional.empty();
    }

    public void delete(Long userId) {
        hashOperations.delete("USER",userId+"");
    }


//

//    public void foo() {
//
//        RedisConnectionFactory connectionFactory = redisConfig.connectionFactory();
//        RedisConnection connection = connectionFactory.getConnection();
//    }

}
