package com.mlorenzo.oracleexample.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mlorenzo.oracleexample.commands.ProductForm;
import com.mlorenzo.oracleexample.domain.Product;

@Component
public class ProductToProductForm implements Converter<Product, ProductForm> {
    
	@Override
    public ProductForm convert(Product product) {
        ProductForm productForm = new ProductForm();
        productForm.setId(product.getId());
        productForm.setDescription(product.getDescription());
        productForm.setPrice(product.getPrice());
        productForm.setImageUrl(product.getImageUrl());
        return productForm;
    }
}
