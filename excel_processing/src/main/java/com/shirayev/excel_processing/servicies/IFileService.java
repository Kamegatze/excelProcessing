package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.FileDto;
import com.shirayev.excel_processing.dto.FileNesting;
import com.shirayev.excel_processing.dto.page.PageDto;
import com.shirayev.excel_processing.dto.page.PageRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    FileDto saveFile(MultipartFile multipartFile) throws IOException;

    FileDto getFileById(Long id);

    PageDto<FileDto> getFiles(PageRequestDto pageRequestDto);

    PageDto<FileNesting> getFilesNesting(PageRequestDto pageRequestDto);

}
