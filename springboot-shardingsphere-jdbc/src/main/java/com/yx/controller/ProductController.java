package com.yx.controller;

import com.yx.page.BaseResponse;
import com.yx.page.PageQueryResponse;
import com.yx.po.Product;
import com.yx.service.ProductService;
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
                .name("xiaomi")
                .price(new BigDecimal(5000.21))
                .createTime(LocalDateTime.now())
                .build();
        productService.addProduct(product);
        return BaseResponse.success();
    }

    @PostMapping("/get")
    public BaseResponse get(){
        Product product = productService.getProduct(1L);
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
        Product product = new Product(1L,"xiaomi_update",new BigDecimal(500.21), LocalDateTime.now());
        productService.updateProduct(product);
        return BaseResponse.success();
    }

}
