package com.mlorenzo.activemqexample.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.mlorenzo.activemqexample.config.ActiveMQConfig;
import com.mlorenzo.activemqexample.domain.Product;
import com.mlorenzo.activemqexample.repositories.ProductRepository;

import java.util.Map;

/**
 * This is the queue listener class, its receiveMessage() method ios invoked with the
 * message as the parameter.
 */
@Component
public class MessageListener {
	private static final Logger log = LogManager.getLogger(MessageListener.class);
    private ProductRepository productRepository;

    public MessageListener(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * This method is invoked whenever any new message is put in the queue.
     * See {@link com.mlorenzo.activemqexample.SpringBootActiveMQApplication} for more details
     * @param message
     */
    @JmsListener(destination = ActiveMQConfig.PRODUCT_MESSAGE_QUEUE, containerFactory = "jmsFactory")
    public void receiveMessage(Map<String, String> message) {
        log.info("Received <" + message + ">");
        Long id = Long.valueOf(message.get("id"));
        Product product = productRepository.findById(id).orElse(null);
        product.setMessageReceived(true);
        product.setMessageCount(product.getMessageCount() + 1);
        productRepository.save(product);
        log.info("Message processed...");
    }
}
