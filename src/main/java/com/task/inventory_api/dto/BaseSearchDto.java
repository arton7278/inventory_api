package com.task.inventory_api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class BaseSearchDto {

    @ApiModelProperty(value = "offset")
    private Integer offset;

    @ApiModelProperty(value = "limit")
    private Integer limit;

    @ApiModelProperty(value = "정렬 타입 ASC(오름차순), DESC(내림차순)")
    private String sortType;

    @ApiModelProperty(value = "정렬 필드")
    private String sortField;

    public void setDefaultParam() {
        setDefaultOffsetLimit();
        setDefaultsortType();
        setDefaultSortField();
    }


    public void setDefaultOffsetLimit(){
        if(this.offset == null && this.limit == null){
            this.offset = 0;
            this.limit = 10;
        }
    }

    public void setDefaultsortType(){
        if(this.sortType == null){
            this.sortType = "DESC";
        }
    }

    public void setDefaultSortField(){
        if(this.sortField == null){
            this.sortField = "createdAt";
        }
    }
}
