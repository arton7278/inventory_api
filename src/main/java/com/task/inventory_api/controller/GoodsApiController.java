package com.task.inventory_api.controller;

import com.task.inventory_api.dto.ApiReponseDto;
import com.task.inventory_api.dto.CreateGoodsDto;
import com.task.inventory_api.dto.SearchGoodsDto;
import com.task.inventory_api.dto.UpdateInventoryDto;
import com.task.inventory_api.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class GoodsApiController {

    private final GoodsService goodsService;

    @ApiOperation(value = "상품등록")
    @PostMapping("/api/v1/goods")
    public ResponseEntity<ApiReponseDto>  createGoods(@RequestBody @Valid CreateGoodsDto createGoodsDto) {
        ApiReponseDto response = new ApiReponseDto(HttpStatus.CREATED.value(), goodsService.createGoods(createGoodsDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "재고 수정")
    @PutMapping("/api/v1/goods/{goodsSrl}/inventory")
    public  ResponseEntity<ApiReponseDto> updateGoodsInventory(@PathVariable("goodsSrl") Long goodsSrl, @RequestBody @Valid UpdateInventoryDto updateInventoryDto){
        ApiReponseDto response = new ApiReponseDto(HttpStatus.OK.value(), goodsService.updateGoodsInventory(goodsSrl, updateInventoryDto));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 조회")
    @GetMapping("/api/v1/goods")
    public ResponseEntity<ApiReponseDto> findGoodsAll(@ModelAttribute SearchGoodsDto searchGoodsDto) {
        ApiReponseDto apiReponseDto = new ApiReponseDto(HttpStatus.OK.value(),  goodsService.findGoodsAll(searchGoodsDto));
        return new ResponseEntity<>(apiReponseDto, HttpStatus.OK);
    }
}
