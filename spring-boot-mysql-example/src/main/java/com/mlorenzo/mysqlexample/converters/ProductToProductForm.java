package com.mlorenzo.mysqlexample.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mlorenzo.mysqlexample.commands.ProductForm;
import com.mlorenzo.mysqlexample.domain.Product;

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
