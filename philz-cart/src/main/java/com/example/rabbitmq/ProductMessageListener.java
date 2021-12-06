package com.example.rabbitmq;

import com.example.philzproduct.PhilzProductRepository;
import com.example.philzproduct.PhilzProducts;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;

/**
 * This is the queue listener class, its receiveMessage() method ios invoked with the
 * message as the parameter.
 */
@Component
public class ProductMessageListener {

    private PhilzProductRepository productRepository;

    private static final Logger log = LogManager.getLogger(ProductMessageListener.class);

    public ProductMessageListener(PhilzProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * This method is invoked whenever any new message is put in the queue.
     * @param message
     */
    public void receiveMessage(String message) {
        log.info("Received Order:" + message + ">");
        Long id = Long.valueOf(message);
        PhilzProducts product = productRepository.findById(id).orElse(null);
      

        productRepository.save(product);
        log.info("Message processed...");
    }
}