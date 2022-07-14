package com.task.inventory_api.service;

import com.task.inventory_api.domain.Goods;
import com.task.inventory_api.dto.CreateGoodsDto;
import com.task.inventory_api.dto.UpdateInventoryDto;
import com.task.inventory_api.repository.GoodsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceTest {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsRepository goodsRepository;


    @Before
    public void initGoods() throws Exception{
        CreateGoodsDto createGoodsDto = new CreateGoodsDto();
        createGoodsDto.setGoodsNm("prd-a");
        createGoodsDto.setOptionNm("opt-aa");
        createGoodsDto.setStock(10L);

        goodsService.createGoods(createGoodsDto);

        createGoodsDto.setGoodsNm("prd-a");
        createGoodsDto.setOptionNm("opt-ab");
        createGoodsDto.setStock(0L);
        goodsService.createGoods(createGoodsDto);

        createGoodsDto.setGoodsNm("prd-b");
        createGoodsDto.setOptionNm("opt-ba");
        createGoodsDto.setStock(0L);
        goodsService.createGoods(createGoodsDto);

        createGoodsDto.setGoodsNm("prd-b");
        createGoodsDto.setOptionNm("opt-bb");
        createGoodsDto.setStock(0L);
        goodsService.createGoods(createGoodsDto);

        createGoodsDto.setGoodsNm("prd-b");
        createGoodsDto.setOptionNm("opt-bc");
        createGoodsDto.setStock(0L);
        goodsService.createGoods(createGoodsDto);

        createGoodsDto.setGoodsNm("prd-c");
        createGoodsDto.setOptionNm("opt-ca");
        createGoodsDto.setStock(0L);
        goodsService.createGoods(createGoodsDto);
    }


    /**
     * 재고 수정 락 테스트
     * @throws Exception
     */
    @Test
    public void updateIncreaseInventoryMultiThreadTest() throws Exception {
        AtomicInteger successCount = new AtomicInteger();
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        Goods findGoods = goodsRepository.findGoods("prd-a", "opt-aa");
        UpdateInventoryDto updateInventoryDto = new UpdateInventoryDto();
        updateInventoryDto.setStock(2L);
        updateInventoryDto.setStockType("increase");

        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                try {
                    goodsService.updateGoodsInventory(findGoods.getGoodsSrl(), updateInventoryDto);
                    successCount.getAndIncrement();
                    System.out.println("재고수정 성공");
                } catch (ObjectOptimisticLockingFailureException soe) {
                    System.out.println("충돌감지");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        System.out.println("성공카운트");
        System.out.println(successCount.get());

        assertEquals(successCount.get(), 5);
    }

    @Test
    public void updateDeductionInventoryMultiThreadTest() throws Exception {
        AtomicInteger successCount = new AtomicInteger();
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        Goods findGoods = goodsRepository.findGoods("prd-a", "opt-aa");
        UpdateInventoryDto updateInventoryDto = new UpdateInventoryDto();
        updateInventoryDto.setStock(2L);
        updateInventoryDto.setStockType("deduction");

        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                try {
                    goodsService.updateGoodsInventory(findGoods.getGoodsSrl(), updateInventoryDto);
                    successCount.getAndIncrement();
                    System.out.println("재고수정 성공");
                } catch (ObjectOptimisticLockingFailureException soe) {
                    System.out.println("충돌감지");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();



        System.out.println("성공카운트");
        System.out.println(successCount.get());

        assertEquals(successCount.get(), 5);
    }
}