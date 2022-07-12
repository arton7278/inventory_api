package com.task.inventory_api.repository;

import com.task.inventory_api.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long>{

    List<Goods> findByGoodsNm(String goodsNm);

    List<Goods>findByGoodsNmAndOptionNm(String goodNm, String optionNm);

    boolean existsByGoodsNmAndOptionNm(String goodNm, String optionNm);
}
