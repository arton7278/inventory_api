package com.task.inventory_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@ToString
public class CreateGoodsDto {
        //사용자 ID
        @ApiModelProperty(value = "상품명", required = true)
        @NotBlank
        private String goodsNm;

        @ApiModelProperty(value = "옵션명", required = true)
        @NotBlank
        private String optionNm;

        @ApiModelProperty(value = "재고수량", required = true)
        @PositiveOrZero
        @Min(0)
        private Long stock;
}
