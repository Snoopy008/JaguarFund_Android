package com.example.macavilang.model;

import java.io.Serializable;

/**
 * Created by macavilang on 16/9/19.
 */
public class CustomerModel implements Serializable {
    String id;
    String name;
    String mobile;
    String address;
    String investProductCount;
    String investShareTotal;
    String investShareCurrent;
    String investShareAmountTotal;
    String investShareAmountCurrent;
    String redeemShareTotal;
    String redeemShareAmountTotal;
    String investShareAmountTotalCN;
    String customerType;
    String customerTypeView;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInvestProductCount() {
        return investProductCount;
    }

    public void setInvestProductCount(String investProductCount) {
        this.investProductCount = investProductCount;
    }

    public String getInvestShareTotal() {
        return investShareTotal;
    }

    public void setInvestShareTotal(String investShareTotal) {
        this.investShareTotal = investShareTotal;
    }

    public String getInvestShareCurrent() {
        return investShareCurrent;
    }

    public void setInvestShareCurrent(String investShareCurrent) {
        this.investShareCurrent = investShareCurrent;
    }

    public String getInvestShareAmountTotal() {
        return investShareAmountTotal;
    }

    public void setInvestShareAmountTotal(String investShareAmountTotal) {
        this.investShareAmountTotal = investShareAmountTotal;
    }

    public String getInvestShareAmountCurrent() {
        return investShareAmountCurrent;
    }

    public void setInvestShareAmountCurrent(String investShareAmountCurrent) {
        this.investShareAmountCurrent = investShareAmountCurrent;
    }

    public String getRedeemShareTotal() {
        return redeemShareTotal;
    }

    public void setRedeemShareTotal(String redeemShareTotal) {
        this.redeemShareTotal = redeemShareTotal;
    }

    public String getRedeemShareAmountTotal() {
        return redeemShareAmountTotal;
    }

    public void setRedeemShareAmountTotal(String redeemShareAmountTotal) {
        this.redeemShareAmountTotal = redeemShareAmountTotal;
    }

    public String getInvestShareAmountTotalCN() {
        return investShareAmountTotalCN;
    }

    public void setInvestShareAmountTotalCN(String investShareAmountTotalCN) {
        this.investShareAmountTotalCN = investShareAmountTotalCN;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerTypeView() {
        return customerTypeView;
    }

    public void setCustomerTypeView(String customerTypeView) {
        this.customerTypeView = customerTypeView;
    }
}
