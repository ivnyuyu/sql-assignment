package com.ivan.yuyuk.sqlassignment.model;

import java.io.Serializable;

public enum ResponseStatus implements Serializable {
    SUCCESS("Поздравляю! Задание решено верно.", true),
    EMPTY("", null),
    FORBIDDEN_KEY_USE_ERROR("Ошибка, использованы недопустимые ключевые слова 'INSERT', 'UPDATE', 'DELETE'.", false),
    SYNTAX_ERROR("Ошибка синтаксиса запроса.", false),
    NO_MATCH_RESULT_ERROR("Результат вашего запроса несовпадает с ожидаемым.", false);

    private final String message;
    private final Boolean isSuccess;

    ResponseStatus(String message, Boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public Boolean isSuccess() {
        return isSuccess;
    }

    public boolean isExecuteQuery() {
        return this == SUCCESS || this == NO_MATCH_RESULT_ERROR;
    }
}
