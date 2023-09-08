package com.mlorenzo.activemqexample.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mlorenzo.activemqexample.domain.Product;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
    static final BigDecimal BIG_DECIMAL_100 = BigDecimal.valueOf(100.00);
    static final String PRODUCT_DESCRIPTION = "a cool product";
    static final String IMAGE_URL = "http://an-imageurl.com/image1.jpg";

    @Autowired
    ProductRepository productRepository;

    @Test
    public void testPersistence() {
        //given
        Product product = new Product();
        product.setDescription(PRODUCT_DESCRIPTION);
        product.setImageUrl(IMAGE_URL);
        product.setPrice(BIG_DECIMAL_100);
        //when
        productRepository.save(product);
        //then
        Assert.assertNotNull(product.getId());
        Product newProduct = productRepository.findById(product.getId()).orElse(null);
        Assert.assertEquals((Long) 1L, newProduct.getId());
        Assert.assertEquals(PRODUCT_DESCRIPTION, newProduct.getDescription());
        Assert.assertEquals(BIG_DECIMAL_100.compareTo(newProduct.getPrice()), 0);
        Assert.assertEquals(IMAGE_URL, newProduct.getImageUrl());
    }
}