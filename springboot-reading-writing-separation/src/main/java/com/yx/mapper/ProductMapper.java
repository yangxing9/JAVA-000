package com.yx.mapper;

import com.yx.po.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> getProductPageList();
}