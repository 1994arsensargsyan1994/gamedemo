package com.example.gamedemo.mapper;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface Mapper<Entity, Dto> {



    Entity toEntity(Dto dto);

    Dto toDto(Entity entity);

    List<Entity> toEntityList(List<Dto> dtoList);

    List<Dto> toDtoList(List<Entity> entityList);
}
