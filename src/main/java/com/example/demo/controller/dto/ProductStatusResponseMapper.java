package com.example.demo.controller.dto;

import com.example.demo.controller.response.ProductStatusResponse;
import com.example.demo.model.ProductStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ProductStatusResponseMapper {

    private ProductStatusResponseMapper(){}

    public static ProductStatusResponse mapToProductStatusResponse(ProductStatus productStatus){
        return ProductStatusResponse.builder()
                .id(productStatus.getId())
                .name(productStatus.getProduct().getName())
                .year(productStatus.getYear())
                .netContentUnit(productStatus.getProduct().getNetContentUnit())
                .quantity(productStatus.getQuantity())
                .netPrice(productStatus.getNetPrice())
                .build();
    }

    public static List<ProductStatusResponse> mapToProductStatusResponses(List<ProductStatus> productStatuses){
        return productStatuses.stream()
                .map(ProductStatusResponseMapper::mapToProductStatusResponse)
                .collect(Collectors.toList());
    }

}
