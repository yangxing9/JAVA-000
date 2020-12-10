package com.yx1.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long productId;

    private String productName;

    private String productCode;

    private BigDecimal price;

    private String descript;

    private LocalDateTime createTime;

    private LocalDateTime modifiedTime;

    private Integer productStatus;

    private Long spuId;

    private Integer isDeleted;

    private String productImg;

    private Integer inventory;

}