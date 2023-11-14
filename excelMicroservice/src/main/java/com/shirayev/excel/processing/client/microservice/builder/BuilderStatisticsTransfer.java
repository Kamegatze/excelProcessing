package com.shirayev.excel.processing.client.microservice.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuilderStatisticsTransfer<T> {

    private URI uri;

    private Class<T> clazz;
}
