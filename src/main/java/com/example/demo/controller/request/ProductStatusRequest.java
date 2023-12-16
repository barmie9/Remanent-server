package com.example.demo.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStatusRequest {
    private String name;
    private String quantity;
    private String price;
    private String brandName;
    private String category;
    private String eanCode;
    private String netContentUnit;


}
