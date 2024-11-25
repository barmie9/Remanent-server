package com.example.demo.controller.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatusResponse {
    private Long id;
    private String name;
    private String quantity;
    private Double netPrice;
    private Integer year;
    private String netContentUnit;

}
