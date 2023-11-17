package com.shirayev.excel.processing.controllers;

import com.shirayev.excel.processing.dto.FileDto;
import com.shirayev.excel.processing.servicies.IFileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.io.FileInputStream;


@WebMvcTest(FileController.class)
public class FileControllerTest {

    @MockBean
    private IFileService fileService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void handlerWriteFileInDatabase_IsCreated_WriteDataInDatabase() throws Exception {

        FileInputStream fileInputStream = new FileInputStream("./src/test/resources/excel.xls");

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "excel.xls",
                MediaType.TEXT_PLAIN_VALUE,
                fileInputStream
        );

        FileDto fileDto = FileDto.builder()
                .id(1L)
                .name("excel.xls")
                .build();

        Mockito.when(fileService.saveFile(multipartFile)).thenReturn(fileDto);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/file//write_file").file(multipartFile))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(fileDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(fileDto.getName()));
        Mockito.verify(fileService, Mockito.times(1)).saveFile(multipartFile);
    }
}