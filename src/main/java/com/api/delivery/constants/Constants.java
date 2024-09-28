package com.api.delivery.constants;

public class Constants {
	
	//Voucher API
	public static final String VOUCHER_API_BASE_URL = "https://mynt-exam.mocklab.io/vouchers";
	
	// Weight limits
    public static final double MAX_WEIGHT = 50.0; // in kg
    public static final double HEAVY_PARCEL_WEIGHT_LIMIT = 10.0; // in kg

    // Volume limits
    public static final double SMALL_PARCEL_VOLUME_LIMIT = 1500.0; // in cm³
    public static final double MEDIUM_PARCEL_VOLUME_LIMIT = 2500.0; // in cm³

    // Cost multipliers
    public static final double HEAVY_PARCEL_COST_MULTIPLIER = 20.0; // per kg
    public static final double SMALL_PARCEL_COST_MULTIPLIER = 0.03; // per cm³
    public static final double MEDIUM_PARCEL_COST_MULTIPLIER = 0.04; // per cm³
    public static final double LARGE_PARCEL_COST_MULTIPLIER = 0.05; // per cm³
    
    // Messages
    public static final String MESSAGE_EXCEEDS_KG = "Weight exceeds 50kg";
    public static final String VOUCHER_EXPIRED= "Voucher is expired!";
    public static final String SUCCESS ="Success";
    public static final String VOUCHER_NOT_FOUND = "Voucher code not found";
    public static final String ERROR_CALLING_API= "Error while calling the API";

    // Prevent instantiation
    private Constants() {}
}
