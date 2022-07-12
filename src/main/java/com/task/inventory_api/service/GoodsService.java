package com.task.inventory_api.service;

import com.task.inventory_api.common.ErrorCode;
import com.task.inventory_api.domain.Goods;
import com.task.inventory_api.domain.Inventory;
import com.task.inventory_api.dto.CreateGoodsDto;
import com.task.inventory_api.dto.SearchGoodsDto;
import com.task.inventory_api.dto.UpdateInventoryDto;
import com.task.inventory_api.exception.ApiCustomException;
import com.task.inventory_api.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GoodsService {
    private final GoodsRepository goodsRepository;

    public Long createGoods(CreateGoodsDto createGoodsDto) {
        validateDuplicateGoodsNm(createGoodsDto.getGoodsNm(), createGoodsDto.getOptionNm());

        Inventory saveInventory = Inventory.builder().stock(0L).build();

        Goods saveGoods = Goods.builder()
                .goodsNm(createGoodsDto.getGoodsNm())
                .optionNm(createGoodsDto.getOptionNm())
                .inventory(saveInventory)
                .build();

        return goodsRepository.save(saveGoods).getGoodsSrl();
    }

    private void validateDuplicateGoodsNm(String goodsNm, String optionNm) {
        if(goodsRepository.existsByGoodsNmAndOptionNm(goodsNm, optionNm)) {
            log.info("validateDuplicateGoodsNm =====>");
            throw new ApiCustomException(ErrorCode.DUPLICATE_GOODS_NAME);
        }
    }


    @Transactional
    public Long updateGoodsInventory(Long goodsSrl, UpdateInventoryDto updateInventoryDto) {

        Goods findGoods = goodsRepository.findById(goodsSrl).orElseThrow(() -> new ApiCustomException(ErrorCode.NOT_FOUND_GOODS));

        findGoods.chageGoodsStock(updateInventoryDto.getStock(),updateInventoryDto.getStockType());

        return findGoods.getGoodsSrl();
    }
}
