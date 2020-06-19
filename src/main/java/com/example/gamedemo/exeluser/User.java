package com.example.gamedemo.exeluser;

import com.example.gamedemo.dto.UserDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface User {
    InputStream createExcel(List<UserDto> userDtoList) throws IOException;

}
