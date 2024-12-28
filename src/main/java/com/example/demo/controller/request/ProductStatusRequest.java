package com.example.demo.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStatusRequest {
    private String name;
    private String quantity;
    private Double price; // zmiana typu ze String na Double
    private String brandName;
    private String category;
    private String eanCode;
    private String netContentUnit;
    private String employeeName;


}
