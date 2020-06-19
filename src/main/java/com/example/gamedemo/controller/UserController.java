package com.example.gamedemo.controller;


import com.example.gamedemo.dto.UserDto;
import com.example.gamedemo.dto.Views;
import com.example.gamedemo.entity.UserEntity;
import com.example.gamedemo.exception.ConflictException;
import com.example.gamedemo.exeluser.User;
import com.example.gamedemo.mapper.UserMapper;
import com.example.gamedemo.server.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("game/users")
public class UserController {


    private static final String EXCEL_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String PATH = "/home/arsen/Desktop/test.xlsx";

    private final UserService userService;
    private final UserMapper userMapper;
    private final User user;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserController(UserService userService, UserMapper userMapper, User user) {
        this.userService = userService;
        this.userMapper = userMapper;

        this.user = user;
    }

    @GetMapping
    public List<UserDto> getAllUsers() throws ExecutionException {


        List<UserEntity> allUsers = userService.getAll();
        return userMapper.toDtoList(allUsers);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws IOException {
        Optional<UserEntity> userEntity = this.userService.getUser(userId);
        if (!userEntity.isPresent()) {
            log.warn("Users with id = {} not found.", userId);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDto(userEntity.get()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus addUser(@Valid @RequestBody UserDto userDto) {

        userDto.setActive(true);
        userDto.setLastVisit(LocalDateTime.now());
        boolean isExist = userService.findUserByEmail(userDto.getEmail());
        if (isExist) {
            throw new ConflictException("User with this email already exists.");
        }
        UserEntity userEntity = userMapper.toEntity(userDto);
        userService.addUser(userEntity);


        return HttpStatus.CREATED;
    }


    @PutMapping("{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) throws IOException {
        UserEntity userEntity = userMapper.toEntity(userDto);
        Optional<UserEntity> userEntityForBd = userService.getUser(userId);
        if (userEntityForBd.isPresent()) {
            userEntity.setId(userId);
            userService.addUser(userEntity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userEntity.setId(userId);
        userService.addUser(userEntity);
        return ResponseEntity.ok(userMapper.toDto(userEntity));

    }

    @DeleteMapping("{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {
        this.userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @JsonView(value = Views.Username.class)
    @GetMapping("/_search")
    public ResponseEntity<UserDto> search(@RequestParam("username") String username) {

        UserEntity userEntity = userService.getUserByUsername(username);
        if (userEntity != null) {
            return ResponseEntity.ok(userMapper.toDto(userEntity));
        }
        log.error("user no found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/excel/_download", produces = EXCEL_MIME_TYPE)
    public ResponseEntity<Resource> downloadUserExcelFile() throws ExecutionException {

        List<UserDto> userDtoList = userMapper.toDtoList(userService.getAll());
        try {
            InputStream in = user.createExcel(userDtoList);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "all_users.xlsx" + "")
                    .body(new InputStreamResource(in));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
