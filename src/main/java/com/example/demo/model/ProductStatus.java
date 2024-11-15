package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private String name;
    private String quantity;
    private String netPrice;
    //private String brandName;
    private Integer year;
    //private String netContentUnit;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
