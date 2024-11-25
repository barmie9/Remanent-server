package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private String name;
    private String quantity;
    private Double netPrice;
    //private String brandName;
    private Integer year;
    //private String netContentUnit;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
