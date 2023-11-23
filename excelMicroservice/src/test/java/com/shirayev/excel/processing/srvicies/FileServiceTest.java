package com.shirayev.excel.processing.srvicies;

import com.shirayev.excel.processing.client.statistics.StatisticsClient;
import com.shirayev.excel.processing.dto.FileDto;
import com.shirayev.excel.processing.dto.PeoplePassageDto;
import com.shirayev.excel.processing.dto.SheetsDto;
import com.shirayev.excel.processing.entities.File;
import com.shirayev.excel.processing.entities.Sheets;
import com.shirayev.excel.processing.mapper.clazz.MapperClazz;
import com.shirayev.excel.processing.parser.Parser;
import com.shirayev.excel.processing.repositories.FileRepository;
import com.shirayev.excel.processing.repositories.PeoplePassageRepository;
import com.shirayev.excel.processing.repositories.SheetsRepository;
import com.shirayev.excel.processing.servicies.implementation.FileServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class FileServiceTest {
    @Mock
    private FileRepository fileRepository;
    @Mock
    private SheetsRepository sheetsRepository;
    @Mock
    private PeoplePassageRepository peoplePassageRepository;
    @Mock
    private Parser<List<SheetsDto>> excelParser;
    @Mock
    private StatisticsClient statisticsClient;
    @Mock
    private MapperClazz mapperClazzMock;
    @InjectMocks
    private FileServiceImp fileServiceImp;
    private MapperClazz mapperClazz;
    private List<SheetsDto> sheetsDtoList;

    @BeforeEach
    void setup() {
            mapperClazz = new MapperClazz(new ModelMapper());

            List<PeoplePassageDto> peoplePassageDtoListTestData = List.of(
                    PeoplePassageDto.builder()
                            .id(1L)
                            .last_name("Гончарова")
                            .first_name("Евгения")
                            .patronymic("Платоновна")
                            .age(20)
                            .action("Вышел")
                            .timeAction(Time.valueOf("14:15:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(2L)
                            .last_name("Казакова")
                            .first_name("Медина")
                            .patronymic("Семёновна")
                            .age(30)
                            .action("Вошел")
                            .timeAction(Time.valueOf("16:15:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(3L)
                            .last_name("Анисимов")
                            .first_name("Иван")
                            .patronymic("Андреевич")
                            .age(22)
                            .action("Вышел")
                            .timeAction(Time.valueOf("09:15:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(4L)
                            .last_name("Давыдова")
                            .first_name("Антонина")
                            .patronymic("Никитична")
                            .age(27)
                            .action("Вошел")
                            .timeAction(Time.valueOf("12:15:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(5L)
                            .last_name("Григорьев")
                            .first_name("Кирилл")
                            .patronymic("Ильич")
                            .age(40)
                            .action("Вышел")
                            .timeAction(Time.valueOf("17:45:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(6L)
                            .last_name("Андреевна")
                            .first_name("София")
                            .patronymic("Артемовна")
                            .age(57)
                            .action("Вошел")
                            .timeAction(Time.valueOf("16:27:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(7L)
                            .last_name("Новикова")
                            .first_name("Ева")
                            .patronymic("Владимировна")
                            .age(53)
                            .action("Вышел")
                            .timeAction(Time.valueOf("16:05:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(8L)
                            .last_name("Суворов")
                            .first_name("Максим")
                            .patronymic("Григорьевич")
                            .age(19)
                            .action("Вошел")
                            .timeAction(Time.valueOf("14:39:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(9L)
                            .last_name("Николаева")
                            .first_name("Серафима")
                            .patronymic("Георгиевна")
                            .age(21)
                            .action("Вышел")
                            .timeAction(Time.valueOf("18:08:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(10L)
                            .last_name("Васильева")
                            .first_name("Алёна")
                            .patronymic("Артёмовна")
                            .age(65)
                            .action("Вошел")
                            .timeAction(Time.valueOf("11:55:00"))
                            .build()
            );

            List<PeoplePassageDto> peoplePassageDtoListOther = List.of(
                    PeoplePassageDto.builder()
                            .id(11L)
                            .last_name("Медведев")
                            .first_name("Андрей")
                            .patronymic("Дмитриевич")
                            .age(20)
                            .action("Вышел")
                            .timeAction(Time.valueOf("11:50:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(12L)
                            .last_name("Федоров")
                            .first_name("Максим")
                            .patronymic("Эрикович")
                            .age(31)
                            .action("Вошел")
                            .timeAction(Time.valueOf("14:25:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(13L)
                            .last_name("Русаков")
                            .first_name("Фёдор")
                            .patronymic("Максимович")
                            .age(39)
                            .action("Вышел")
                            .timeAction(Time.valueOf("08:10:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(14L)
                            .last_name("Пастухов")
                            .first_name("Давид")
                            .patronymic("Владиславович")
                            .age(42)
                            .action("Вошел")
                            .timeAction(Time.valueOf("10:25:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(15L)
                            .last_name("Сафонов")
                            .first_name("Егор")
                            .patronymic("Михайлович")
                            .age(54)
                            .action("Вышел")
                            .timeAction(Time.valueOf("08:29:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(16L)
                            .last_name("Исаева")
                            .first_name("Маргарита")
                            .patronymic("Владиславовна")
                            .age(63)
                            .action("Вошел")
                            .timeAction(Time.valueOf("18:13:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(17L)
                            .last_name("Лукьянова")
                            .first_name("София")
                            .patronymic("Данииловна")
                            .age(23)
                            .action("Вышел")
                            .timeAction(Time.valueOf("10:41:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(18L)
                            .last_name("Малышев")
                            .first_name("Кирилл")
                            .patronymic("Артёмович")
                            .age(28)
                            .action("Вошел")
                            .timeAction(Time.valueOf("09:43:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(19L)
                            .last_name("Зверева")
                            .first_name("Алиса")
                            .patronymic("Ивановна")
                            .age(17)
                            .action("Вышел")
                            .timeAction(Time.valueOf("15:31:00"))
                            .build(),
                    PeoplePassageDto.builder()
                            .id(20L)
                            .last_name("Калашников")
                            .first_name("Владимир")
                            .patronymic("Макарович")
                            .age(37)
                            .action("Вошел")
                            .timeAction(Time.valueOf("19:22:00"))
                            .build()
            );

            sheetsDtoList = List.of(
                    new SheetsDto(1L, "test-data", peoplePassageDtoListTestData),
                    new SheetsDto(2L, "other", peoplePassageDtoListOther)
            );
    }

    @Test
    void givenFile_whenWriteInDatabase_thenReturnFileDto() throws IOException {
        /*
        * given
        * */
        FileInputStream fileInputStream = new FileInputStream("./src/test/resources/excel_without_title.xls");

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "excel_without_title.xls",
                MediaType.TEXT_PLAIN_VALUE,
                fileInputStream
        );


        List<Sheets> sheets = mapperClazz.getListObject(sheetsDtoList, Sheets.class);

        FileDto fileDto = FileDto.builder()
                .id(1L)
                .name(multipartFile.getOriginalFilename())
                .build();

        File file = mapperClazz.getObject(fileDto, File.class);
        file.setSheets(sheets);

        sheets.forEach(item -> {
            item.setFile(file);
        });

        BDDMockito.given(excelParser.parse(ArgumentMatchers.any())).willReturn(sheetsDtoList);
        BDDMockito.given(mapperClazzMock.getListObject(sheetsDtoList, Sheets.class)).willReturn(sheets);
        BDDMockito.given(fileRepository.save(ArgumentMatchers.any())).willReturn(file);
        BDDMockito.given(sheetsRepository.saveAll(sheets)).willReturn(sheets);
        BDDMockito.given(peoplePassageRepository.saveAll(sheets.get(0).getPeoplePassages())).willReturn(sheets.get(0).getPeoplePassages());
        BDDMockito.given(peoplePassageRepository.saveAll(sheets.get(1).getPeoplePassages())).willReturn(sheets.get(1).getPeoplePassages());
        BDDMockito.given(mapperClazzMock.getObject(file, FileDto.class)).willReturn(fileDto);
        BDDMockito.given(fileRepository.findById(fileDto.getId())).willReturn(Optional.of(file));

        /*
        * when
        * */
        FileDto fileDtoReturn = fileServiceImp.saveFile(multipartFile);

        /*
        * then
        * */
        Assertions.assertNotNull(fileDto);
        Assertions.assertEquals(fileDtoReturn.getName(), multipartFile.getOriginalFilename());
    }

    @Test
    void givenFile_whenGetFileById_thenReturnFileDto() {
        /*
        * given
        * */

        FileDto fileDto = FileDto.builder()
                .id(1L)
                .name("excel_without_title.xls")
                .build();

        File file = mapperClazz.getObject(fileDto, File.class);
        file.setSheets(mapperClazz.getListObject(sheetsDtoList, Sheets.class));

        BDDMockito.given(fileRepository.findById(file.getId())).willReturn(Optional.of(file));
        BDDMockito.given(mapperClazzMock.getObject(file, FileDto.class)).willReturn(fileDto);

        /*
        * when
        * */

        FileDto fileDtoReturn = fileServiceImp.getFileById(fileDto.getId());

        /*
        * then
        * */
        Assertions.assertNotNull(fileDtoReturn);
        Assertions.assertEquals(fileDtoReturn.getName(), file.getName());
        Assertions.assertEquals(fileDtoReturn.getId(), file.getId());
    }

    @Test
    void givenFile_whenGetFileById_thenThrowsNoSuchException() {
        /*
        * given
        * */

        BDDMockito.given(fileRepository.findById(1L)).willReturn(Optional.empty());

        /*
        * when
        * */
        Throwable throwable = Assertions.assertThrows(NoSuchElementException.class, () -> {
            fileServiceImp.getFileById(1L);
        });

        /*
        * then
        * */

        Assertions.assertNotNull(throwable);
        Assertions.assertInstanceOf(NoSuchElementException.class, throwable);
        Assertions.assertEquals(throwable.getMessage(), "Файл с id: 1 не был найден");
    }
}
