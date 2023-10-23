package com.shirayev.excel_processing.custom_exception;

import java.io.IOException;

public class EmptyValueInRecordException extends IOException {
    public EmptyValueInRecordException(String message) {
        super(message);
    }
}
