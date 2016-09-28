package com.example.macavilang.model;

/**
 * Created by macavilang on 16/9/18.
 */
public class TradeRecordDetailModel {
    String tradeId;
    String clientId;
    String productId;
    String clientName;
    String customerType;
    String clientMobile;
    String pidType;
    String pid;
    String clientAddress;
    String clientRemark;
    String productName;
    String fundCode;
    String contractNo;
    String tradeType;
    String unitPrice;
    String latestNetValue;
    String tradeShare;
    String tradeAmount;
    String bankName;
    String bankAccount;
    String attachment;
    String tradeDate;
    String tradeRemark;
    String tacode;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCustomerType() {
        if (customerType.equals("1"))
        {
            customerType = "A类";
        }else if (customerType.equals("2"))
        {
            customerType = "B类";
        }
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getClientMobile() {
        return clientMobile;
    }

    public void setClientMobile(String clientMobile) {
        this.clientMobile = clientMobile;
    }

    public String getPidType() {
        if (pidType.equals("1"))
        {
            pidType = "身份证";
        }else if (pidType.equals("2"))
        {
            pidType = "护照";
        }else if (pidType.equals("3"))
        {
            pidType = "驾驶证";
        }
        return pidType;
    }

    public void setPidType(String pidType) {
        this.pidType = pidType;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientRemark() {
        return clientRemark;
    }

    public void setClientRemark(String clientRemark) {
        this.clientRemark = clientRemark;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getTradeType() {
        if (tradeType.equals("1"))
        {
            tradeType = "认购";
        }else if (tradeType.equals("2"))
        {
            tradeType = "申购";
        }else if (tradeType.equals("3"))
        {
            tradeType = "赎回";
        }
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getLatestNetValue() {
        return latestNetValue;
    }

    public void setLatestNetValue(String latestNetValue) {
        this.latestNetValue = latestNetValue;
    }

    public String getTradeShare() {
        return tradeShare;
    }

    public void setTradeShare(String tradeShare) {
        this.tradeShare = tradeShare;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeRemark() {
        return tradeRemark;
    }

    public void setTradeRemark(String tradeRemark) {
        this.tradeRemark = tradeRemark;
    }

    public String getTacode() {
        return tacode;
    }

    public void setTacode(String tacode) {
        this.tacode = tacode;
    }
}
