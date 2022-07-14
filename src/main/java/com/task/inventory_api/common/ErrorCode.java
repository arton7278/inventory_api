package com.task.inventory_api.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    //검색
    NOT_FOUND_GOODS(500,"SRH0001", "상품이 존재하지 않습니다."),

    REQUIRED_VALUE_NOT_ENTERED(400,"VAL0001", "필수값 미 입력 입니다."),
    INVENTORY_CANNOT_BE_MINUS(400,"VAL0002", "재고는 0이하로 수정이 불가 합니다"),
    //유효성 체크
    DUPLICATE_GOODS_NAME(400,"DUP0001", "중복된 상품이 존재 합니다."),

    UNKNOWN_ERROR(500,"SYS0001", "일시적인 오류가 발생하였습니다.");

    private final int status;

    private final String code;
    private final String message;

    public int getStatus() { return status; }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

}
