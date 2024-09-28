package com.api.delivery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.delivery.model.DeliveryCostRequest;
import com.api.delivery.model.DeliveryCostResponse;
import com.api.delivery.service.DeliveryCostService;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeliveryController.class);

	@Autowired
    private DeliveryCostService deliveryCostService;

    @PostMapping("/calculate")
    public DeliveryCostResponse calculateCost(@RequestBody DeliveryCostRequest request) {
    	logger.info("Calculating cost : IN");
        return deliveryCostService.calculateDeliveryCost(request);
    }
    
}
