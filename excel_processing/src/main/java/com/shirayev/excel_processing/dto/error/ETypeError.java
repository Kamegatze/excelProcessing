package com.shirayev.excel_processing.dto.error;

public enum ETypeError {
    IO_EXCEPTION(1),
    TITLE_EXCEPTION(2),
    EMPTY_VALUE_RECORD_EXCEPTION(3);

    private final Integer code;

    ETypeError(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
