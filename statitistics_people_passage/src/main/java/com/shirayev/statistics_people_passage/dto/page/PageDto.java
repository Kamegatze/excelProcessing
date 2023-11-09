package com.shirayev.statistics_people_passage.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageDto<T> {

    private List<T> content;

    private Integer currentPage;

    private Integer countPage;

    private Integer countElementsInPage;

    private Long countElements;

}
