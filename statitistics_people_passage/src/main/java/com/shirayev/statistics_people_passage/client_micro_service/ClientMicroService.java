package com.shirayev.statistics_people_passage.client_micro_service;

import java.net.URI;

public interface ClientMicroService<T> {

    T getData(URI uri);

}
