package com.example.macavilang.model;

import java.io.Serializable;

/**
 * Created by macavilang on 16/9/19.
 */
public class ProductModel implements Serializable {
    String id;
    String fundName;
    String fundShortName;
    String fundCode;
    String bizCode;
    String custodian;
    String fundType;
    String fundShare;
    String currency;
    String fundOwnerNumber;
    String fundTotalAmount;
    String fundCurrentShare;
    String fundCurrentOwnerNumber;
    String fundRedeemShare;
    String fundRedeemShareAmount;
    String warningPrice;
    String fundRemark;
    String companyId;
    String warningPriceView;
    String latestNetValue;
    String latestNetValueView;
    String latestNetValueDate;
    String latestAccumulativeNetValue;
    String latestAccumulativeNetValueView;
    String releaseDate;
    String status;
    String manager;
    String subscriptionFee;
    String redemptionFee;
    String managementFee;
    String lockUpPeriod;
    String openDaySetting;
    String governingBody;
    String cusInstitution;
    String opsId;
    String recentOpenDay;
    String fundShareCurrent;
    String fundAmountCurrent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundShortName() {
        return fundShortName;
    }

    public void setFundShortName(String fundShortName) {
        this.fundShortName = fundShortName;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getFundShare() {
        return fundShare;
    }

    public void setFundShare(String fundShare) {
        this.fundShare = fundShare;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFundOwnerNumber() {
        return fundOwnerNumber;
    }

    public void setFundOwnerNumber(String fundOwnerNumber) {
        this.fundOwnerNumber = fundOwnerNumber;
    }

    public String getFundTotalAmount() {
        return fundTotalAmount;
    }

    public void setFundTotalAmount(String fundTotalAmount) {
        this.fundTotalAmount = fundTotalAmount;
    }

    public String getFundCurrentShare() {
        return fundCurrentShare;
    }

    public void setFundCurrentShare(String fundCurrentShare) {
        this.fundCurrentShare = fundCurrentShare;
    }

    public String getFundCurrentOwnerNumber() {
        return fundCurrentOwnerNumber;
    }

    public void setFundCurrentOwnerNumber(String fundCurrentOwnerNumber) {
        this.fundCurrentOwnerNumber = fundCurrentOwnerNumber;
    }

    public String getFundRedeemShare() {
        return fundRedeemShare;
    }

    public void setFundRedeemShare(String fundRedeemShare) {
        this.fundRedeemShare = fundRedeemShare;
    }

    public String getFundRedeemShareAmount() {
        return fundRedeemShareAmount;
    }

    public void setFundRedeemShareAmount(String fundRedeemShareAmount) {
        this.fundRedeemShareAmount = fundRedeemShareAmount;
    }

    public String getWarningPrice() {
        return warningPrice;
    }

    public void setWarningPrice(String warningPrice) {
        this.warningPrice = warningPrice;
    }

    public String getFundRemark() {
        return fundRemark;
    }

    public void setFundRemark(String fundRemark) {
        this.fundRemark = fundRemark;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getWarningPriceView() {
        return warningPriceView;
    }

    public void setWarningPriceView(String warningPriceView) {
        this.warningPriceView = warningPriceView;
    }

    public String getLatestNetValue() {
        return latestNetValue;
    }

    public void setLatestNetValue(String latestNetValue) {
        this.latestNetValue = latestNetValue;
    }

    public String getLatestNetValueView() {
        return latestNetValueView;
    }

    public void setLatestNetValueView(String latestNetValueView) {
        this.latestNetValueView = latestNetValueView;
    }

    public String getLatestNetValueDate() {
        return latestNetValueDate;
    }

    public void setLatestNetValueDate(String latestNetValueDate) {
        this.latestNetValueDate = latestNetValueDate;
    }

    public String getLatestAccumulativeNetValue() {
        return latestAccumulativeNetValue;
    }

    public void setLatestAccumulativeNetValue(String latestAccumulativeNetValue) {
        this.latestAccumulativeNetValue = latestAccumulativeNetValue;
    }

    public String getLatestAccumulativeNetValueView() {
        return latestAccumulativeNetValueView;
    }

    public void setLatestAccumulativeNetValueView(String latestAccumulativeNetValueView) {
        this.latestAccumulativeNetValueView = latestAccumulativeNetValueView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getStatus() {
        if (status.equals("1"))
        {
            status = "募集";
        }else if (status.equals("2"))
        {
            status = "封闭";
        }else if (status.equals("3"))
        {
            status = "运作";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getSubscriptionFee() {
        return subscriptionFee;
    }

    public void setSubscriptionFee(String subscriptionFee) {
        this.subscriptionFee = subscriptionFee;
    }

    public String getRedemptionFee() {
        return redemptionFee;
    }

    public void setRedemptionFee(String redemptionFee) {
        this.redemptionFee = redemptionFee;
    }

    public String getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(String managementFee) {
        this.managementFee = managementFee;
    }

    public String getLockUpPeriod() {
        return lockUpPeriod;
    }

    public void setLockUpPeriod(String lockUpPeriod) {
        this.lockUpPeriod = lockUpPeriod;
    }

    public String getOpenDaySetting() {
        return openDaySetting;
    }

    public void setOpenDaySetting(String openDaySetting) {
        this.openDaySetting = openDaySetting;
    }

    public String getGoverningBody() {
        return governingBody;
    }

    public void setGoverningBody(String governingBody) {
        this.governingBody = governingBody;
    }

    public String getCusInstitution() {
        return cusInstitution;
    }

    public void setCusInstitution(String cusInstitution) {
        this.cusInstitution = cusInstitution;
    }

    public String getOpsId() {
        return opsId;
    }

    public void setOpsId(String opsId) {
        this.opsId = opsId;
    }

    public String getRecentOpenDay() {
        return recentOpenDay;
    }

    public void setRecentOpenDay(String recentOpenDay) {
        this.recentOpenDay = recentOpenDay;
    }

    public String getFundShareCurrent() {
        return fundShareCurrent;
    }

    public void setFundShareCurrent(String fundShareCurrent) {
        this.fundShareCurrent = fundShareCurrent;
    }

    public String getFundAmountCurrent() {
        return fundAmountCurrent;
    }

    public void setFundAmountCurrent(String fundAmountCurrent) {
        this.fundAmountCurrent = fundAmountCurrent;
    }
}
