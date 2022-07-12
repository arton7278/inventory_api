package com.task.inventory_api.dto;

import goods_crud.goods_api.common.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {
    private int status;
    private String code;
    private String message;
    private String etcMessage;
    public ErrorResponseDto(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ErrorResponseDto(ErrorCode errorCode, String etcMessage) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.etcMessage = etcMessage;
    }
}
