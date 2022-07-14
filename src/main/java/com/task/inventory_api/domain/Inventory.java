package com.task.inventory_api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.inventory_api.common.ErrorCode;
import com.task.inventory_api.exception.ApiCustomException;
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


    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "goods_srl")
    private Goods goods;

    public void setStockIncrease(Long stock) {
        this.stock += stock;
    }

    public void setStockDeduction(Long stock) {
        if(this.stock - stock < 0){
            throw new ApiCustomException(ErrorCode.INVENTORY_CANNOT_BE_MINUS);
        } else {
            this.stock -= stock;
        }

    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Builder
    public Inventory(Long stock, Goods goods){
        this.stock = stock;
        this.goods =  goods;
    }
}
