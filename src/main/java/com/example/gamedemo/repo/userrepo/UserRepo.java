package com.example.gamedemo.repo.userrepo;

import com.example.gamedemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    UserEntity findByUsername(String username);


}
