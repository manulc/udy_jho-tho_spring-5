package com.mlorenzo.activemqexample.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.mlorenzo.activemqexample.commands.ProductForm;
import com.mlorenzo.activemqexample.config.ActiveMQConfig;
import com.mlorenzo.activemqexample.converters.ProductFormToProduct;
import com.mlorenzo.activemqexample.domain.Product;
import com.mlorenzo.activemqexample.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductRepository productRepository;
    private ProductFormToProduct productFormToProduct;
    private JmsTemplate jmsTemplate;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductFormToProduct productFormToProduct,
                              JmsTemplate jmsTemplate) {
        this.productRepository = productRepository;
        this.productFormToProduct = productFormToProduct;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public List<Product> listAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add); //fun with Java 8
        return products;
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product saveOrUpdateProductForm(ProductForm productForm) {
        Product savedProduct = saveOrUpdate(productFormToProduct.convert(productForm));
        System.out.println("Saved Product Id: " + savedProduct.getId());
        return savedProduct;
    }

    @Override
    public void sendMessage(String id) {
        Map<String, String> actionmap = new HashMap<>();
        actionmap.put("id", id);
        log.info("Sending the index request through queue message");
        jmsTemplate.convertAndSend(ActiveMQConfig.PRODUCT_MESSAGE_QUEUE, actionmap);
    }
}
