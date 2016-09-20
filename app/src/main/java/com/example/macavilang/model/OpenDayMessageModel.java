package com.example.macavilang.model;

/**
 * Created by macavilang on 16/9/18.
 */
public class OpenDayMessageModel {
    String productId;
    String openDay;
    String productName;
    String openDayMessageStr;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOpenDay() {
        return openDay;
    }

    public void setOpenDay(String openDay) {
        this.openDay = openDay;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOpenDayMessageStr() {
        String openDayStr = openDay + "是" + productName + "的开放日";
        return openDayStr;
    }

    public void setOpenDayMessageStr(String openDayMessageStr) {
        this.openDayMessageStr = openDayMessageStr;
    }
}
