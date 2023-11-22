package com.shirayev.excel.processing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shirayev.excel.processing.dto.FileDto;
import com.shirayev.excel.processing.dto.FileNesting;
import com.shirayev.excel.processing.dto.PeoplePassageDto;
import com.shirayev.excel.processing.dto.SheetsNesting;
import com.shirayev.excel.processing.dto.page.PageDto;
import com.shirayev.excel.processing.dto.page.PageRequestDto;
import com.shirayev.excel.processing.servicies.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.List;


@WebMvcTest(FileController.class)
public class FileControllerTest {

    @MockBean
    private FileService fileService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/file/write_file").file(multipartFile)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(fileDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(fileDto.getName()));
        Mockito.verify(fileService, Mockito.times(1)).saveFile(multipartFile);
    }

    @Test
    void handlerGetFileById_IsOk_ReturnFileDto() throws Exception {
        FileDto fileDto = FileDto.builder()
                .id(1L)
                .name("excel.xls")
                .build();

        Mockito.when(fileService.getFileById(fileDto.getId())).thenReturn(fileDto);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/file/%d", fileDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(fileDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(fileDto.getName()));
        Mockito.verify(fileService, Mockito.times(1)).getFileById(fileDto.getId());
    }

    @Test
    void handlerGetFiles_IsOk_ReturnPageFilesDto() throws Exception {

        /*
        * given
        * */

        List<FileDto> fileDtoList = List.of(
                new FileDto(1L, "other_name"),
                new FileDto(2L, "other_name"),
                new FileDto(3L, "other_name"),
                new FileDto(4L, "other_name"),
                new FileDto(5L, "other_name"),
                new FileDto(6L, "other_name"),
                new FileDto(7L, "other_name"),
                new FileDto(8L, "other_name"),
                new FileDto(9L, "other_name"),
                new FileDto(10L, "other_name"),
                new FileDto(11L, "other_name"),
                new FileDto(12L, "other_name"),
                new FileDto(13L, "other_name")
        );
        PageRequestDto pageRequestDto = new PageRequestDto();

        PageDto<FileDto> pageDto = PageDto.<FileDto>builder()
                .content(fileDtoList)
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .countPage(1)
                .countElements((long) fileDtoList.size())
                .build();

        /*
        * when
        * */

        Mockito.when(fileService.getFiles(pageRequestDto)).thenReturn(pageDto);

        /*
        * then
        * */

        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/file/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage").value(pageDto.getCurrentPage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countElementsInPage").value(pageDto.getCountElementsInPage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countPage").value(pageDto.getCountPage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countElements").value(pageDto.getCountElements()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andReturn();
        Mockito.verify(fileService, Mockito.times(1)).getFiles(pageRequestDto);
        String body = resultActions.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertEquals(body, objectMapper.writeValueAsString(pageDto));
    }

    @Test
    void handlerGetFilesNesting_IsOk_ReturnPageFileNesting() throws Exception {
        /*
        * given
        * */

        List<SheetsNesting> sheetsNestingList = List.of(
                SheetsNesting.builder()
                        .id(1L)
                        .title("main")
                        .peoplePassages(List.of(
                                PeoplePassageDto.builder()
                                        .id(1L)
                                        .action("Вошел")
                                        .age(20)
                                        .first_name("Алексей")
                                        .last_name("Ширяев")
                                        .patronymic("Павлович")
                                        .timeAction(Time.valueOf("16:00:00"))
                                        .build(),
                                PeoplePassageDto.builder()
                                        .id(2L)
                                        .action("Вышел")
                                        .age(20)
                                        .first_name("Виктор")
                                        .last_name("Кулаков")
                                        .patronymic("Андреевич")
                                        .timeAction(Time.valueOf("15:00:00"))
                                        .build()
                        ))
                        .build(),
                SheetsNesting.builder()
                        .id(2L)
                        .title("other")
                        .peoplePassages(List.of(
                                PeoplePassageDto.builder()
                                        .id(3L)
                                        .action("Вошел")
                                        .age(20)
                                        .first_name("Андрей")
                                        .last_name("Скрипка")
                                        .patronymic("Викторович")
                                        .timeAction(Time.valueOf("17:00:00"))
                                        .build(),
                                PeoplePassageDto.builder()
                                        .id(4L)
                                        .action("Вышел")
                                        .age(20)
                                        .first_name("Иван")
                                        .last_name("Романов")
                                        .patronymic("Валерьевич")
                                        .timeAction(Time.valueOf("17:30:00"))
                                        .build()
                        ))
                        .build()
        );

        List<FileNesting> fileNestingList = List.of(
            FileNesting.builder()
                    .id(1L)
                    .name("other_name")
                    .sheets(sheetsNestingList)
                    .build()
        );

        PageRequestDto pageRequestDto = new PageRequestDto();

        PageDto<FileNesting> pageDto = PageDto.<FileNesting>builder()
                .content(fileNestingList)
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .countPage(1)
                .countElements((long) fileNestingList.size())
                .build();

        /*
        * when
        * */

        Mockito.when(fileService.getFilesNesting(pageRequestDto)).thenReturn(pageDto);

        /*
        * then
        * */
        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/file/all/nesting")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage").value(pageDto.getCurrentPage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countElementsInPage").value(pageDto.getCountElementsInPage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countPage").value(pageDto.getCountPage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countElements").value(pageDto.getCountElements()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andReturn();
        Mockito.verify(fileService, Mockito.times(1)).getFilesNesting(pageRequestDto);
        String body = resultActions.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertEquals(body, objectMapper.writeValueAsString(pageDto));
    }
}