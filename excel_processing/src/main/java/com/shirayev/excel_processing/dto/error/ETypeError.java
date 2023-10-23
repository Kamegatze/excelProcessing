package com.shirayev.excel_processing.dto.error;

public enum ETypeError {
    IO_EXCEPTION(1),
    TITLE_EXCEPTION(2),
    EMPTY_VALUE_RECORD_EXCEPTION(3);

    ETypeError(Integer code) {
    }
}
