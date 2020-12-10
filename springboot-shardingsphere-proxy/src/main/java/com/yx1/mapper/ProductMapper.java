package com.yx1.mapper;

import com.yx1.po.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> getProductPageList();
}