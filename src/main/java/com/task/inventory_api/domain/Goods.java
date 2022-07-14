package com.task.inventory_api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods extends BaseTimeEntity{
    //goods 시리얼
    @Id @GeneratedValue
    @Column(name = "goods_srl")
    private Long goodsSrl;
    //상품 이름
    @Column(nullable = false)
    private String goodsNm;
    //옵션명
    private String optionNm;

    @JsonIgnore
    @OneToOne(mappedBy="goods", fetch = LAZY)
    private Inventory inventory;


    @Builder
    public Goods (String goodsNm, String optionNm) {
        this.goodsNm = goodsNm;
        this.optionNm = optionNm;
    }

    public void chageGoodsStock(Long stock, String changeType) {

        if("increase".equals(changeType)){
            this.getInventory().setStockIncrease(stock);
        } else {
            //차감 : deduction
            this.getInventory().setStockDeduction(stock);
        }
    }
}
