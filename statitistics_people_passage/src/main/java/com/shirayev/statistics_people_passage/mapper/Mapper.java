package com.shirayev.statistics_people_passage.mapper;

import java.util.List;

public interface Mapper {

    <FROM, TO>List<TO> getListObject(List<FROM> fromList, Class<TO> clazz);

    <FROM, TO> TO getObject(FROM from, Class<TO> clazz);
}
