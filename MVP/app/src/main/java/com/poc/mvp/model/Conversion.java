package com.poc.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Conversion {

    @SerializedName("USD_INR")
    @Expose
    private Double uSDINR;

    public Double getUSDINR() {
        return uSDINR;
    }

    public void setUSDINR(Double uSDINR) {
        this.uSDINR = uSDINR;
    }

}