package com.task.inventory_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.inventory_api.domain.Goods;
import com.task.inventory_api.dto.BaseSearchDto;
import com.task.inventory_api.dto.CreateGoodsDto;
import com.task.inventory_api.dto.SearchGoodsDto;
import com.task.inventory_api.dto.UpdateInventoryDto;
import com.task.inventory_api.repository.GoodsRepository;
import com.task.inventory_api.service.GoodsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GoodsApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static ObjectMapper objectMapper = new ObjectMapper();

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
     * 상품 생성
     * @throws Exception
     */
    @Test
    public void createGoods() throws  Exception{
        //given
        CreateGoodsDto createGoodsDto = new CreateGoodsDto();
        createGoodsDto.setGoodsNm("prd-d");
        createGoodsDto.setOptionNm("opt-da");
        createGoodsDto.setStock(10L);

        String param = objectMapper.writeValueAsString(createGoodsDto);

        //when //then
        this.mockMvc.perform(post("/api/v1/goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    /**
     * 상품 중보 체크
     * @throws Exception
     */
    @Test
    public void existsByGoods() throws  Exception{
        //given
        CreateGoodsDto createGoodsDto = new CreateGoodsDto();
        createGoodsDto.setGoodsNm("prd-c");
        createGoodsDto.setOptionNm("opt-ca");
        createGoodsDto.setStock(0L);

        String param = objectMapper.writeValueAsString(createGoodsDto);

        //when //then
        this.mockMvc.perform(post("/api/v1/goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    /**
     * 재고 수정 증감
     * @throws Exception
     */
    @Test
    public void updateGoodsInventoryIncrease() throws Exception{

        Goods findGoods = goodsRepository.findGoods("prd-a", "opt-aa");
        UpdateInventoryDto updateInventoryDto = new UpdateInventoryDto();
        updateInventoryDto.setStock(10L);
        updateInventoryDto.setStockType("increase");

        String param = objectMapper.writeValueAsString(updateInventoryDto);
        //when //then
        this.mockMvc.perform(put("/api/v1/goods/"+findGoods.getGoodsSrl()+"/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * 재고 파라미미터 마이너스 체크
     *  ex) stock : -10
     * @throws Exception
     */
    @Test
    public void minusUpdateGoodsInventory() throws Exception{

        Goods findGoods = goodsRepository.findGoods("prd-a", "opt-aa");
        UpdateInventoryDto updateInventoryDto = new UpdateInventoryDto();
        updateInventoryDto.setStock(-10L);
        updateInventoryDto.setStockType("increase");

        String param = objectMapper.writeValueAsString(updateInventoryDto);
        //when //then
        this.mockMvc.perform(put("/api/v1/goods/"+findGoods.getGoodsSrl()+"/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    /**
     * 재고 차감
     * @throws Exception
     */

    @Test
    public void updateGoodsInventoryDeduction() throws Exception{

        Goods findGoods = goodsRepository.findGoods("prd-a", "opt-aa");
        UpdateInventoryDto updateInventoryDto = new UpdateInventoryDto();
        updateInventoryDto.setStock(10L);
        updateInventoryDto.setStockType("deduction");

        String param = objectMapper.writeValueAsString(updateInventoryDto);
        //when //then
        this.mockMvc.perform(put("/api/v1/goods/"+findGoods.getGoodsSrl()+"/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * 재고 차감 0이하일때 예외처리
     * @throws Exception
     */
    @Test
    public void updateGoodsInventoryDeductionZero() throws Exception{

        Goods findGoods = goodsRepository.findGoods("prd-a", "opt-aa");
        UpdateInventoryDto updateInventoryDto = new UpdateInventoryDto();
        updateInventoryDto.setStock(15L);
        updateInventoryDto.setStockType("deduction");

        String param = objectMapper.writeValueAsString(updateInventoryDto);
        //when //then
        this.mockMvc.perform(put("/api/v1/goods/"+findGoods.getGoodsSrl()+"/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    /**
     * 상품명 조회
     * @throws Exception
     */
    @Test
    public void findGoodsNm() throws Exception{
        //given
        SearchGoodsDto searchGoodsDto = new SearchGoodsDto();
        searchGoodsDto.setGoodsNm("prd-a");

        //when
        String param = objectMapper.writeValueAsString(searchGoodsDto);

        //then
        this.mockMvc.perform(get("/api/v1/goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * 상품명 옵션명 조회
     * @throws Exception
     */
    @Test
    public void findGoodsNmAndOptionNm() throws Exception{
        //given
        SearchGoodsDto searchGoodsDto = new SearchGoodsDto();
        searchGoodsDto.setGoodsNm("prd-a");
        searchGoodsDto.setOptionNm("opt-ab");

        String param = objectMapper.writeValueAsString(searchGoodsDto);

        //when then
        this.mockMvc.perform(get("/api/v1/goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isOk())
                .andDo(print());
    }
}