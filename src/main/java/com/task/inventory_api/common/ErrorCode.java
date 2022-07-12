package com.task.inventory_api.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    //검색
    NOT_FOUND_GOODS(500,"SRH0001", "상품이 존재하지 않습니다."),
    //유효성 체크
    NOT_EXIST_GOODS_NAME(400,"VAL0001", "상품명은 필수 입니다."),
    NOT_EXIST_GOODS_PRICE(400,"VAL0002", "상품가격은 필수 입니다."),
    NOT_EXIST_GOODS_COM_ID(400,"VAL0003", "상품의 업체정보는 필수 입니다."),
    NOT_EXIST_GOODS_GENDER_TYPE(400,"VAL0004", "상품의 성별은 필수 입니다."),
    NEGATIVE_INPUT_IS_NOT_ALLOWED(400,"VAL0005", "음수 입력은 허용되지 않습니다."),

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
