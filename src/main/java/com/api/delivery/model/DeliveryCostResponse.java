package com.api.delivery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryCostResponse {
	
	private String message;
    private double originalCost;
    private double discount;
    private double finalCost;

    public DeliveryCostResponse(String message, double originalCost, double discount) {
        this.message = message;
        this.originalCost = originalCost;
        this.discount = discount;
        this.finalCost = originalCost - discount;
    }

}
