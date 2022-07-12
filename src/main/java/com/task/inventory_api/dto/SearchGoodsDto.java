package com.task.inventory_api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
public class SearchGoodsDto {
	@ApiModelProperty(value = "상품명")
	private String goodsNm;
	@ApiModelProperty(value = "옵션명")
	private String optionNm;
}
