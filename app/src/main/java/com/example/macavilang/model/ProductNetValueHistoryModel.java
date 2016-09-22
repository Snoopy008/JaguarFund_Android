package com.example.macavilang.model;

/**
 * Created by macavilang on 16/9/22.
 */
public class ProductNetValueHistoryModel {
    String id;
    String marketDate;
    String marketPrice;
    String marketPriceView;
    String accumulativeMarketPrice;
    String companyId;
    String accumulativeMarketPriceView;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketDate() {
        return marketDate;
    }

    public void setMarketDate(String marketDate) {
        this.marketDate = marketDate;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getMarketPriceView() {
        return marketPriceView;
    }

    public void setMarketPriceView(String marketPriceView) {
        this.marketPriceView = marketPriceView;
    }

    public String getAccumulativeMarketPrice() {
        return accumulativeMarketPrice;
    }

    public void setAccumulativeMarketPrice(String accumulativeMarketPrice) {
        this.accumulativeMarketPrice = accumulativeMarketPrice;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAccumulativeMarketPriceView() {
        return accumulativeMarketPriceView;
    }

    public void setAccumulativeMarketPriceView(String accumulativeMarketPriceView) {
        this.accumulativeMarketPriceView = accumulativeMarketPriceView;
    }
}
