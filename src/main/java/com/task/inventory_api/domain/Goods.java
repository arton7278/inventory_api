package com.task.inventory_api.domain;

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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "inventory_srl")
    private Inventory inventory;


    @Builder
    public Goods (String goodsNm, String optionNm, Inventory inventory) {
        this.goodsNm = goodsNm;
        this.optionNm = optionNm;
        this.inventory = inventory;
        inventory.setGoods(this);
    }

    public void chageGoodsStock(Long stock, String changeType) {
        if("increase".equals(changeType)){
            this.getInventory().setStockIncrease(stock);
        } else {
            this.getInventory().setStockDeduction(stock);
        }
    }
}
