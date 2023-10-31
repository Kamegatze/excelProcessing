package com.shirayev.excel_processing.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {

    private final Sort sort = Sort.by("id").descending();

    private Integer pageNumber = 0;

    private Integer pageSize = 20;

    public static PageRequest getPageRequest(PageRequestDto pageRequestDto) {
        return PageRequest.of(pageRequestDto.getPageNumber(), pageRequestDto.getPageSize(), pageRequestDto.getSort());
    }
}
