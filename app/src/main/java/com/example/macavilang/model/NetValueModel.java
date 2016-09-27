package com.example.macavilang.model;

/**
 * Created by macavilang on 16/9/13.
 */
public class NetValueModel {
    String productId;
    String productName;
    String productShortName;
    String latestMarketDate;
    String latestMarketPrice;
    String latestAccumulativeMarketPrice;
    String previousMarketDate;
    String previousMarketPrice;
    String previousAccumulativeMarketPrice;

    public String getProductShortName() {
        return productShortName;
    }

    public void setProductShortName(String productShortName) {
        this.productShortName = productShortName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLatestMarketDate() {
        return latestMarketDate;
    }

    public void setLatestMarketDate(String latestMarketDate) {
        this.latestMarketDate = latestMarketDate;
    }

    public String getLatestMarketPrice() {
        return latestMarketPrice;
    }

    public void setLatestMarketPrice(String latestMarketPrice) {
        this.latestMarketPrice = latestMarketPrice;
    }

    public String getLatestAccumulativeMarketPrice() {
        return latestAccumulativeMarketPrice;
    }

    public void setLatestAccumulativeMarketPrice(String latestAccumulativeMarketPrice) {
        this.latestAccumulativeMarketPrice = latestAccumulativeMarketPrice;
    }

    public String getPreviousMarketDate() {
        return previousMarketDate;
    }

    public void setPreviousMarketDate(String previousMarketDate) {
        this.previousMarketDate = previousMarketDate;
    }

    public String getPreviousMarketPrice() {
        return previousMarketPrice;
    }

    public void setPreviousMarketPrice(String previousMarketPrice) {
        this.previousMarketPrice = previousMarketPrice;
    }

    public String getPreviousAccumulativeMarketPrice() {
        return previousAccumulativeMarketPrice;
    }

    public void setPreviousAccumulativeMarketPrice(String previousAccumulativeMarketPrice) {
        this.previousAccumulativeMarketPrice = previousAccumulativeMarketPrice;
    }
}
