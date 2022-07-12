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
public class Inventory {
    @Id
    @GeneratedValue
    @Column(name = "inventory_srl")
    private Long inventorySrl;

    //수량
    private Long stock;

    @JsonIgnore
    @OneToOne(mappedBy="goods", fetch = LAZY)
    private Goods goods;

    public void setStockIncrease(Long stock) {
        this.stock += stock;
    }

    public void setStockDeduction(Long stock) {
        this.stock -= stock;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Builder
    public Inventory(Long stock){
        this.stock = stock;
    }
}
