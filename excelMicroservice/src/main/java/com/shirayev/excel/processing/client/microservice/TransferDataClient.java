package com.shirayev.excel.processing.client.microservice;

import java.net.URI;

public interface TransferDataClient<T> {

    T transferData(URI uri, Object body, Class<T> clazz);

}
