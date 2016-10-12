package com.example.macavilang.model;

/**
 * Created by macavilang on 16/9/21.
 */
public class CustomerInverstProductModel {
    String productId;
    String productShortName;
    String investShare;
    String investAmount;
    String netValue;
    String totalNetValue;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductShortName() {
        return productShortName;
    }

    public void setProductShortName(String productShortName) {
        this.productShortName = productShortName;
    }

    public String getInvestShare() {
        return investShare;
    }

    public void setInvestShare(String investShare) {
        this.investShare = investShare;
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount;
    }

    public String getNetValue() {
        return netValue;
    }

    public void setNetValue(String netValue) {
        this.netValue = netValue;
    }

    public String getTotalNetValue() {
        return totalNetValue;
    }

    public void setTotalNetValue(String totalNetValue) {
        this.totalNetValue = totalNetValue;
    }
}
