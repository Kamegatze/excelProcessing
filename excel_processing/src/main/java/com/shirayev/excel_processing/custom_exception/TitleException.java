package com.shirayev.excel_processing.custom_exception;

import java.io.IOException;

public class TitleException extends IOException {
    public TitleException(String message) {
        super(message);
    }
}
