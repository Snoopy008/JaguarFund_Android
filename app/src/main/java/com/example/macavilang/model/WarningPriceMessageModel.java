package com.example.macavilang.model;

/**
 * Created by macavilang on 16/9/18.
 */
public class WarningPriceMessageModel {
    String productName;
    String warningPrice;
    String latestNetValueDate;
    String productId;
    String latestNetValue;
    String warningPriceMessageStr;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWarningPrice() {
        return warningPrice;
    }

    public void setWarningPrice(String warningPrice) {
        this.warningPrice = warningPrice;
    }

    public String getLatestNetValueDate() {
        return latestNetValueDate;
    }

    public void setLatestNetValueDate(String latestNetValueDate) {
        this.latestNetValueDate = latestNetValueDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLatestNetValue() {
        return latestNetValue;
    }

    public void setLatestNetValue(String latestNetValue) {
        this.latestNetValue = latestNetValue;
    }

    public String getWarningPriceMessageStr() {
        String warningPriceStr = productName + "净值已经跌至" + warningPrice;
        return warningPriceMessageStr;
    }

    public void setWarningPriceMessageStr(String warningPriceMessageStr) {
        this.warningPriceMessageStr = warningPriceMessageStr;
    }
}
