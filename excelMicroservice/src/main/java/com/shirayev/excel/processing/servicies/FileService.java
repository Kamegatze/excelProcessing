package com.shirayev.excel.processing.servicies;

import com.shirayev.excel.processing.dto.FileDto;
import com.shirayev.excel.processing.dto.page.PageDto;
import com.shirayev.excel.processing.dto.page.PageRequestDto;
import com.shirayev.excel.processing.dto.FileNesting;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    FileDto saveFile(MultipartFile multipartFile) throws IOException;

    FileDto getFileById(Long id);

    PageDto<FileDto> getFiles(PageRequestDto pageRequestDto);

    PageDto<FileNesting> getFilesNesting(PageRequestDto pageRequestDto);

}
