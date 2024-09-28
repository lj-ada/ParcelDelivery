package com.api.delivery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryCostRequest {
	private double weight;
    private double height;
    private double width;
    private double length;
    private String voucherCode;
}
