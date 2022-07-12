package com.task.inventory_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ApiReponseDto<T> {

    private int status;
    private T data;

    @Builder
    public ApiReponseDto(int status, T data) {
        this.status = status;
        this.data = data;
    }
}
