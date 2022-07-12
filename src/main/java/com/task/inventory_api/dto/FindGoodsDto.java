package com.task.inventory_api.dto;

import com.task.inventory_api.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FindGoodsDto {
	//상품번호
	private Long goodsSrl;
	//상품 이름
	private String goodsNm;
	//상품 설명
	private String optionName;
	//재고수량
	private Long stock;

	public FindGoodsDto(Goods goods){
		this.goodsSrl = goods.getGoodsSrl();
		this.goodsNm = goods.getGoodsNm();
		this.optionName = goods.getOptionNm();
		this.stock = goods.getInventory().getStock();
	}


}
