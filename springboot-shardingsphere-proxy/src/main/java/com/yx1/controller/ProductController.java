package com.yx1.controller;

import com.yx1.page.BaseResponse;
import com.yx1.page.PageQueryResponse;
import com.yx1.po.Product;
import com.yx1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/12/2 0002 17:12
 */

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public BaseResponse add(){
        Product product = Product.builder()
                .productName("xiaomi")
                .productCode("aeqweqwe")
                .descript("asdasd")
                .modifiedTime(LocalDateTime.now())
                .productStatus(0)
                .spuId(10L)
                .isDeleted(0)
                .inventory(1000)
                .productImg("asdasd/asdasd")
                .price(new BigDecimal(5000.21))
                .createTime(LocalDateTime.now())
                .build();
        productService.addProduct(product);
        return BaseResponse.success();
    }

    @PostMapping("/get")
    public BaseResponse get(){
        Product product = productService.getProduct(10L);
        return BaseResponse.success(product);
    }

    @PostMapping("/list")
    public BaseResponse<PageQueryResponse> list(){
        return productService.getProductList(1,10);
    }

    @PostMapping("/delete")
    public BaseResponse delete(){
        productService.deleteProduct(1L);
        return BaseResponse.success();
    }

    @PostMapping("/update")
    public BaseResponse update(){
        return BaseResponse.success();
    }

}
