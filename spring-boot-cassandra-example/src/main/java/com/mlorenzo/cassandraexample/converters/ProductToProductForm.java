package com.mlorenzo.cassandraexample.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mlorenzo.cassandraexample.commands.ProductForm;
import com.mlorenzo.cassandraexample.domain.Product;

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
