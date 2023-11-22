package com.shirayev.excel.processing.mapper.clazz;

import com.shirayev.excel.processing.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MapperClazz implements Mapper {

    private final ModelMapper model;

    @Override
    public <FROM, TO> List<TO> getListObject(List<FROM> fromList, Class<TO> clazz) {
        return fromList.stream().map(item -> model.map(item, clazz)).toList();
    }

    @Override
    public <FROM, TO> TO getObject(FROM from, Class<TO> clazz) {
        return model.map(from, clazz);
    }
}
