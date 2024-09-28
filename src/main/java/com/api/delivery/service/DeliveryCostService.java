package com.api.delivery.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.delivery.constants.Constants;
import com.api.delivery.model.DeliveryCostRequest;
import com.api.delivery.model.DeliveryCostResponse;
import com.api.delivery.model.VoucherResponse;


@Service
public class DeliveryCostService {
	
		private static final Logger logger = LoggerFactory.getLogger(DeliveryCostService.class);
		
	 	private final RestTemplate restTemplate = new RestTemplate();
	    
	    public DeliveryCostResponse calculateDeliveryCost(DeliveryCostRequest request) {
	    	logger.info("Calculating delivery cost : IN");
	        double weight = request.getWeight();
	        double height = request.getHeight();
	        double width = request.getWidth();
	        double length = request.getLength();
	        String voucherCode = request.getVoucherCode();
	        
	        // Calculate volume
	        double volume = height * width * length;

	        // Reject if weight exceeds the maximum weight
	        if (weight > Constants.MAX_WEIGHT) {
	            return new DeliveryCostResponse(Constants.MESSAGE_EXCEEDS_KG, 0, 0);
	        }

	        // Calculate cost based on rules
	        double cost = calculateCostBasedOnRules(weight, volume);

	        // Apply voucher discount if voucher code is provided
	        if (voucherCode != null && !voucherCode.isEmpty()) {
	        	VoucherResponse voucher = getVoucherDetails(voucherCode);
	        	if (voucher.getDiscount() != 0) {
	        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate expiryDate = LocalDate.parse(voucher.getExpiry(), formatter);
					// Current date
					LocalDate currentDate = LocalDate.now();
					double discount = 0;
					if(expiryDate.isAfter(currentDate)){
						discount = voucher.getDiscount();
					}else {
						return new DeliveryCostResponse(Constants.VOUCHER_EXPIRED, cost, discount, cost);
					}
	                double finalCost = cost - (cost * discount / 100);
                    return new DeliveryCostResponse(Constants.SUCCESS, cost, discount, finalCost);
	            } else {
	                return new DeliveryCostResponse(voucher.getCode(), cost, 0, cost);
	            }
	        }
	        logger.info("Calculating delivery cost : OUT");
	        return new DeliveryCostResponse("Success", cost, 0);
	        
	    }
	    
	    
	    private double calculateCostBasedOnRules(double weight, double volume) {
	    	logger.info("Calculating delivery cost base on rules : IN");
	        if (weight > Constants.HEAVY_PARCEL_WEIGHT_LIMIT) {
	        	// Heavy Parcel
	            return Constants.HEAVY_PARCEL_COST_MULTIPLIER * weight; 
	        } else if (volume < Constants.SMALL_PARCEL_VOLUME_LIMIT) {
	        	// Small Parcel
	            return Constants.SMALL_PARCEL_COST_MULTIPLIER * volume; 
	        } else if (volume < Constants.MEDIUM_PARCEL_VOLUME_LIMIT) {
	        	// Medium Parcel
	            return Constants.MEDIUM_PARCEL_COST_MULTIPLIER * volume; 
	        } else {
	        	// Large Parcel
	            return Constants.LARGE_PARCEL_COST_MULTIPLIER * volume; 
	        }
	    }
	    
	    public VoucherResponse getVoucherDetails(String code) {
	    	logger.info("Retrieving voucher details : IN");
	        String url = UriComponentsBuilder.fromHttpUrl(Constants.VOUCHER_API_BASE_URL)
	                                         .pathSegment(code)
	                                         .toUriString();
	        try {
	            return restTemplate.getForObject(url, VoucherResponse.class);
	        } catch (HttpClientErrorException.NotFound e) {
	        	logger.debug("Voucher code not found: " + code);
	            return new VoucherResponse(Constants.VOUCHER_NOT_FOUND, 0, "");
	        } catch (RestClientException e) {
	        	logger.debug("Error while calling the API: " + e.getMessage());
	            return new VoucherResponse(Constants.ERROR_CALLING_API, 0, "");
	        } finally {
	        	logger.info("Retrieving voucher details : OUT");
	        }
	    }

}
