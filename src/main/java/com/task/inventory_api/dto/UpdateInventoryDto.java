package com.task.inventory_api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@ToString
public class UpdateInventoryDto {

        @ApiModelProperty(value = "재고수량", required = true)
        @NotNull
        @PositiveOrZero
        @Min(0)
        private Long stock;

        @ApiModelProperty(value = "재고 증(Increase)감/차감(Deduction)", required = true)
        @NotBlank
        private String stockType;
}
