package com.example.macavilang.model;

/**
 * Created by macavilang on 16/9/22.
 */
public class ProductNetValuePercentModel {
    String oneMonthAgoRate;
    String latelyRate;
    String totalRate;
    String latestNetValue;
    String threeMonthAgoRate;
    String sixMonthAgoRate;
    String threeYearAgoRate;
    String totalNetValue;
    String oneYearAgoRate;

    public String getOneMonthAgoRate() {
        if (oneMonthAgoRate.equals(""))
        {
            return "无";
        }else {
            return oneMonthAgoRate;
        }

    }

    public void setOneMonthAgoRate(String oneMonthAgoRate) {
        this.oneMonthAgoRate = oneMonthAgoRate;
    }

    public String getLatelyRate() {
        return latelyRate;
    }

    public void setLatelyRate(String latelyRate) {
        this.latelyRate = latelyRate;
    }

    public String getTotalRate() {
        if (totalRate.equals(""))
        {
            return "无";
        }else {
            return totalRate;
        }
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }

    public String getLatestNetValue() {
        return latestNetValue;
    }

    public void setLatestNetValue(String latestNetValue) {
        this.latestNetValue = latestNetValue;
    }

    public String getThreeMonthAgoRate() {
        if (threeMonthAgoRate.equals(""))
        {
            return "无";
        }else {
            return threeMonthAgoRate;
        }
    }

    public void setThreeMonthAgoRate(String threeMonthAgoRate) {
        this.threeMonthAgoRate = threeMonthAgoRate;
    }

    public String getSixMonthAgoRate() {
        if (sixMonthAgoRate.equals(""))
        {
            return "无";
        }else {
            return sixMonthAgoRate;
        }
    }

    public void setSixMonthAgoRate(String sixMonthAgoRate) {
        this.sixMonthAgoRate = sixMonthAgoRate;
    }

    public String getThreeYearAgoRate() {
        if (threeYearAgoRate.equals(""))
        {
            return "无";
        }else {
            return threeYearAgoRate;
        }
    }

    public void setThreeYearAgoRate(String threeYearAgoRate) {
        this.threeYearAgoRate = threeYearAgoRate;
    }

    public String getTotalNetValue() {
        return totalNetValue;
    }

    public void setTotalNetValue(String totalNetValue) {
        this.totalNetValue = totalNetValue;
    }

    public String getOneYearAgoRate() {
        if (oneYearAgoRate.equals(""))
        {
            return "无";
        }else {
            return oneYearAgoRate;
        }
    }

    public void setOneYearAgoRate(String oneYearAgoRate) {
        this.oneYearAgoRate = oneYearAgoRate;
    }
}
