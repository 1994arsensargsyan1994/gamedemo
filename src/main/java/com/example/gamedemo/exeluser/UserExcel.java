package com.example.gamedemo.exeluser;

import com.example.gamedemo.dto.UserDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
@Service
public class UserExcel implements  User {

    @Override
    public InputStream createExcel(List<UserDto> userDtoList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet  sheet = workbook.createSheet("users");
        this.createAndFillHeader(sheet);
        this.createAndFillBody(sheet, userDtoList);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        return new ByteArrayInputStream(bos.toByteArray());
    }

    private void createAndFillBody(Sheet sheet, List<UserDto> userDtoList) {
        int rowNum = 1;
        for (UserDto userDto : userDtoList) {
            int cell = 0;
            Row  row = sheet.createRow(rowNum++);
            row.createCell(cell++).setCellValue(userDto.getId());
            row.createCell(cell++).setCellValue(userDto.getUsername());
            row.createCell(cell++).setCellValue(userDto.getEmail());
            row.createCell(cell++).setCellValue(userDto.getRole().name());
            row.createCell(cell++).setCellValue(userDto.getGender().name());
            row.createCell(cell).setCellValue(userDto.getLastVisit().toString());
        }
    }

    private void createAndFillHeader(Sheet sheet) {

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("username");
        header.createCell(2).setCellValue("email");
        header.createCell(3).setCellValue("role");
        header.createCell(4).setCellValue("gender");
        header.createCell(5).setCellValue("data");
    }
}
