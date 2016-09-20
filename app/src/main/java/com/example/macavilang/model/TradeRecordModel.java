package com.example.macavilang.model;

/**
 * Created by macavilang on 16/9/7.
 */
public class TradeRecordModel {
    String bankAccount;
    String bankName;
    String clientAddress;
    String clientId;
    String clientMobile;
    String clientName;
    String contractNo;
    String fundTradePurchaseHistoryId;
    String id;
    String productId;
    String productName;
    String tradeAmount;
    String tradeDate;
    String tradeShare;
    String tradeType;
    String unitPrice;
    String unitPriceView;

    private String _tradeRecordStr;
    public String getTradeRecordStr() {
        if (tradeType.equals("1"))
        {
            _tradeType = "认购";
        }else if (tradeType.equals("2"))
        {
            _tradeType = "申购";
        }else if (tradeType.equals("3"))
        {
            _tradeType = "赎回";
        }
        _tradeRecordStr = this.clientName + _tradeType + this.productName + tradeShare + "份";
        return _tradeRecordStr;
    }


    public void setTradeRecordStr(String tradeRecordStr) {
        this.tradeRecordStr = tradeRecordStr;
    }

    String tradeRecordStr;

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientMobile() {
        return clientMobile;
    }

    public void setClientMobile(String clientMobile) {
        this.clientMobile = clientMobile;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getFundTradePurchaseHistoryId() {
        return fundTradePurchaseHistoryId;
    }

    public void setFundTradePurchaseHistoryId(String fundTradePurchaseHistoryId) {
        this.fundTradePurchaseHistoryId = fundTradePurchaseHistoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeShare() {
        return tradeShare;
    }

    public void setTradeShare(String tradeShare) {
        this.tradeShare = tradeShare;
    }

    private String _tradeType;
    public String getTradeType() {

        if (tradeType.equals("1"))
        {
            _tradeType = "认购";
        }else if (tradeType.equals("2"))
        {
            _tradeType = "申购";
        }else if (tradeType.equals("3"))
        {
            _tradeType = "赎回";
        }
        return _tradeType;
    }
    public void setTradeType(String tradeType) { this.tradeType = tradeType; }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPriceView() {
        return unitPriceView;
    }

    public void setUnitPriceView(String unitPriceView) {
        this.unitPriceView = unitPriceView;
    }
}
