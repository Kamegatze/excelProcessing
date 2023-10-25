package com.shirayev.excel_processing.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {

    private List<T> content;

    private Integer currentPage;

    private Integer countPage;

    private Integer countElementsInPage;

    private Long countElements;
}
