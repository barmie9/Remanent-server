package com.example.demo.service;

import com.example.demo.controller.request.ProductStatusRequest;
import com.example.demo.model.Product;
import com.example.demo.model.ProductStatus;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductStatusRepository productStatusRepository;

    public Product getProductByCode(String eanCode){
        Product product =  productRepository.findByEanCode(eanCode).orElse(null);
        if(product != null) return product;
        else return null;
    }

    public ProductStatus addProduct(ProductStatusRequest productStatusRequest){

        ProductStatus productStatus = new ProductStatus();
        productStatus.setName(productStatusRequest.getName());
        productStatus.setPrice(productStatusRequest.getPrice());
        productStatus.setQuantity(productStatusRequest.getQuantity());
        productStatus.setBrandName(productStatusRequest.getBrandName());
        productStatus.setNetContentUnit(productStatusRequest.getNetContentUnit());
        productStatus.setYear(Year.now().getValue());

        ProductStatus productStatusResponse =  productStatusRepository.save(productStatus);

        Product product =  productRepository.findByEanCode(productStatusRequest.getEanCode()).orElse(null);
        if(product == null){
            Product newProduct = new Product();
            newProduct.setName(productStatusRequest.getName());
            newProduct.setPrice(productStatusRequest.getPrice());
            newProduct.setBrandName(productStatusRequest.getBrandName());
            newProduct.setCategory(productStatusRequest.getCategory());
            newProduct.setEanCode(productStatusRequest.getEanCode());
            newProduct.setNetContentUnit(productStatusRequest.getNetContentUnit());
            productRepository.save(newProduct);
        }


        return productStatusResponse;
    }

}
