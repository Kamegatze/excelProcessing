package com.shirayev.excel.processing.custom.exception;

import java.io.IOException;

public class EmptyValueInRecordException extends IOException {
    public EmptyValueInRecordException(String message) {
        super(message);
    }
}
