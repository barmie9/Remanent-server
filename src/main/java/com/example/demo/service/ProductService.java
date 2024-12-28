package com.example.demo.service;

import com.example.demo.controller.request.ProductStatusRequest;
import com.example.demo.controller.response.ProductStatusResponse;
import com.example.demo.model.Product;
import com.example.demo.model.ProductStatus;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductStatusRepository productStatusRepository;

    public Product getProductByCode(String eanCode){
        if(eanCode.equals("0")|| eanCode.isEmpty() )
            return null;
        Product product =  productRepository.findByEanCode(eanCode).orElse(null);
        if(product != null) return product;
        else return null;
    }
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public ProductStatus addProduct(ProductStatusRequest productStatusRequest){ //TODO do poprawy przez zmiany w DB
        Product product =  productRepository.findByEanCode(productStatusRequest.getEanCode()).orElse(null);

        if(product == null){
            product = new Product();
            product.setName(productStatusRequest.getName());
            product.setPrice(productStatusRequest.getPrice());
            product.setBrandName(productStatusRequest.getBrandName());
            product.setCategory(productStatusRequest.getCategory());
            product.setEanCode(productStatusRequest.getEanCode());
            product.setNetContentUnit(productStatusRequest.getNetContentUnit());
            productRepository.save(product);
        }


        ProductStatus productStatus = new ProductStatus();
        productStatus.setQuantity(productStatusRequest.getQuantity());
        productStatus.setYear(Year.now().getValue()); // TODO do poprawy co gdyby, ktoś robił renament z opóźnieniem np 1 stycznia
        productStatus.setNetPrice(productStatusRequest.getPrice());
        productStatus.setProduct(product);
        productStatus.setEmployeeName(productStatusRequest.getEmployeeName());




        return  productStatusRepository.save(productStatus);

    }

    public List<ProductStatus> getProductsStatus(Integer year) {
        return productStatusRepository.findAllByYear(year).orElse(new ArrayList<ProductStatus>());

    }

    public String deleteProductStatus(Long productId) {
        ProductStatus productStatus = productStatusRepository.findById(productId).orElse(null);

        if(productStatus == null)
            return "ERROR: productStatus not found by id: " + productId;
        else {
            productStatusRepository.delete(productStatus);
            return "OK";
        }
    }
}
