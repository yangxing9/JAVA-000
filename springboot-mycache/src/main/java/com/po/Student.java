package com.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/19 0019 17:50
 */
@Data
public class Student implements Serializable {

    private String name;

    private int age;

    private String id;
}
