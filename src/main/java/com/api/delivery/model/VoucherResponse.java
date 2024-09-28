package com.api.delivery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherResponse {
	private String code;
    private double discount;
    private String expiry;
}
