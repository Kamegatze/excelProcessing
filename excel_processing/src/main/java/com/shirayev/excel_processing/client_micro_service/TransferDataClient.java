package com.shirayev.excel_processing.client_micro_service;

import java.net.URI;

public interface TransferDataClient<T> {

    T transferData(URI uri, Object body, Class<T> clazz);

}
