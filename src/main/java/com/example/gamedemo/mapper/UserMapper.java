package com.example.gamedemo.mapper;

import com.example.gamedemo.dto.UserDto;
import com.example.gamedemo.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
@org.mapstruct.Mapper(componentModel = "spring")
public interface UserMapper extends Mapper<UserEntity, UserDto> {
}
