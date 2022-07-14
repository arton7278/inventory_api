package com.task.inventory_api.service;

import com.task.inventory_api.common.ErrorCode;
import com.task.inventory_api.domain.Goods;
import com.task.inventory_api.domain.Inventory;
import com.task.inventory_api.dto.CreateGoodsDto;
import com.task.inventory_api.dto.FindGoodsDto;
import com.task.inventory_api.dto.SearchGoodsDto;
import com.task.inventory_api.dto.UpdateInventoryDto;
import com.task.inventory_api.exception.ApiCustomException;
import com.task.inventory_api.repository.GoodsRepository;
import com.task.inventory_api.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GoodsService {
    private final GoodsRepository goodsRepository;

    private final InventoryRepository inventoryRepository;

    private final RedissonClient redissonClient;

    @Transactional
    public Long createGoods(CreateGoodsDto createGoodsDto) {
        validateDuplicateGoodsNm(createGoodsDto.getGoodsNm(), createGoodsDto.getOptionNm());

        Goods saveGoods = Goods.builder()
                .goodsNm(createGoodsDto.getGoodsNm())
                .optionNm(createGoodsDto.getOptionNm())
                .build();

        Goods resultGoods = goodsRepository.save(saveGoods);

        Inventory saveInventory = Inventory.builder().stock(createGoodsDto.getStock()).goods(resultGoods).build();

        inventoryRepository.save(saveInventory);

        return resultGoods.getGoodsSrl();
    }

    private void validateDuplicateGoodsNm(String goodsNm, String optionNm) {
        if(goodsRepository.existsByGoodsNmAndOptionNm(goodsNm, optionNm)) {
            log.info("validateDuplicateGoodsNm =====>");
            throw new ApiCustomException(ErrorCode.DUPLICATE_GOODS_NAME);
        }
    }


    @Transactional
    public Long updateGoodsInventory(Long goodsSrl, UpdateInventoryDto updateInventoryDto) {
        RLock lock =  redissonClient.getLock(String.valueOf(goodsSrl));
        Goods findGoods = null;

        try {
            boolean isLocked = lock.tryLock(2, 3, TimeUnit.SECONDS);
            if(isLocked){
                findGoods = goodsRepository.findById(goodsSrl).orElseThrow(() -> new ApiCustomException(ErrorCode.NOT_FOUND_GOODS));
                findGoods.chageGoodsStock(updateInventoryDto.getStock(),updateInventoryDto.getStockType());
            }

        } catch (ApiCustomException acu) {
            throw new ApiCustomException(acu.getErrorCode());
        } catch (Exception e) {
            log.error("{} ", e);
            e.printStackTrace();
        } finally {
            // 락 해제
            lock.unlock();
        }

        return findGoods.getGoodsSrl();
    }

    public Page<FindGoodsDto> findGoodsAll(SearchGoodsDto searchGoodsDto) {

        Page<Goods> findGoodsList = null;

        searchGoodsDto.setDefaultParam();

        PageRequest pageRequest = PageRequest.of(searchGoodsDto.getOffset(), searchGoodsDto.getLimit(), Sort.by(Sort.Direction.valueOf(searchGoodsDto.getSortType()), searchGoodsDto.getSortField()));

        if(StringUtils.isNotBlank(searchGoodsDto.getGoodsNm()) && StringUtils.isNotBlank(searchGoodsDto.getOptionNm())){
            findGoodsList = goodsRepository.findByGoodsNmAndOptionNm(searchGoodsDto.getGoodsNm(), searchGoodsDto.getOptionNm(), pageRequest);
        } else{
            findGoodsList = goodsRepository.findByGoodsNm(searchGoodsDto.getGoodsNm(), pageRequest);
        }
        return findGoodsList.map(FindGoodsDto :: new);
    }
}
