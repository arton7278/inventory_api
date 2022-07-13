package com.task.inventory_api.exception;

import com.task.inventory_api.common.ErrorCode;
import com.task.inventory_api.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * POST Reqeust Dto 유효성 검사 Exception Custom 처리
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.REQUIRED_VALUE_NOT_ENTERED);
        StringBuilder builder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String code = fieldError.getCode();
            String field = fieldError.getField();

            etcMessage(builder, fieldError);

        }
        errorResponseDto.setEtcMessage(builder.toString());

        return new ResponseEntity<ErrorResponseDto>(errorResponseDto, HttpStatus.valueOf(errorResponseDto.getStatus()));
    }

    private void etcMessage(StringBuilder builder, FieldError fieldError) {
        builder.append("[");
        builder.append(fieldError.getField());
        builder.append("](은)는 ");
        builder.append(fieldError.getDefaultMessage());
        builder.append(" 입력된 값: [");
        builder.append(fieldError.getRejectedValue());
        builder.append("]");
        builder.append(System.lineSeparator());
    }


    @ExceptionHandler(ApiCustomException.class)
    public ResponseEntity<ErrorResponseDto> handleApiCustomException(ApiCustomException exception) {
        ErrorResponseDto errorResponseDto =  new ErrorResponseDto(exception.getErrorCode());
        log.info("ApiCustomException : {} " , exception.getMessage());
        return new ResponseEntity<ErrorResponseDto>(errorResponseDto, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

}
