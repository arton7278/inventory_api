package com.task.inventory_api.repository;

import com.task.inventory_api.domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long>{

    Page<Goods> findByGoodsNm(String goodsNm, Pageable pageable);

    Page<Goods>findByGoodsNmAndOptionNm(String goodNm, String optionNm, Pageable pageable);

    boolean existsByGoodsNmAndOptionNm(String goodNm, String optionNm);

    @Query("select g from Goods g where g.goodsNm = :goodsNm and g.optionNm = :optionNm")
    Goods findGoods(String goodsNm, String optionNm);
}
