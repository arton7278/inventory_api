package com.task.inventory_api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class SearchGoodsDto extends  BaseSearchDto{
	@ApiModelProperty(value = "상품명")
	private String goodsNm;
	@ApiModelProperty(value = "옵션명")
	private String optionNm;
}
