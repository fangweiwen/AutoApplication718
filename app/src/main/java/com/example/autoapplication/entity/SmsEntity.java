package com.example.autoapplication.entity;

import java.io.Serializable;

/**
 * author WeiWen Fang
 * on 2019/6/5.
 */
public class SmsEntity implements Serializable {
    private String strAddress;

    private String strbody;

    private String strDate;

    private String strType;

    @Override
    public String toString() {
        return "SmsEntity{" +
                "strAddress='" + strAddress + '\'' +
                ", strbody='" + strbody + '\'' +
                ", strDate='" + strDate + '\'' +
                ", strType='" + strType + '\'' +
                '}';
    }

    public String getStrAddress() {
        return strAddress;
    }

    public void setStrAddress(String strAddress) {
        this.strAddress = strAddress;
    }

    public String getStrbody() {
        return strbody;
    }

    public void setStrbody(String strbody) {
        this.strbody = strbody;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }
}
