package com.example.macavilang.model;

public class BirthdayMessageModel{
    String clientBirthday;
    String clientId;
    String clientName;
    String birthdayMessageStr;

    public String getClientBirthday() {
        return clientBirthday;
    }

    public void setClientBirthday(String clientBirthday) {
        this.clientBirthday = clientBirthday;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getBirthdayMessageStr() {
        String birthdayStr = clientBirthday + "是" + clientName + "的生日";
        return birthdayStr;
    }

    public void setBirthdayMessageStr(String birthdayMessageStr) {
        this.birthdayMessageStr = birthdayMessageStr;
    }
}
