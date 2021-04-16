package com.example.qunlchitiu.Object;

import java.util.Date;

public class Spending {
    private String mExpenses;
    private int mMoney;
    private Date mTime;
    private int mAuto;

    public Spending(String mExpenses, int mMoney, Date mTime) {
        this.mExpenses = mExpenses;
        this.mMoney = mMoney;
        this.mTime = mTime;
    }

    public Spending(String mExpenses, int mMoney, Date mTime, int mAuto) {
        this.mExpenses = mExpenses;
        this.mMoney = mMoney;
        this.mTime = mTime;
        this.mAuto = mAuto;
    }

    public Spending(String mExpenses, int mMoney, int mAuto) {
        this.mExpenses = mExpenses;
        this.mMoney = mMoney;
        this.mAuto = mAuto;
    }

    public int getmAuto() {
        return mAuto;
    }

    public void setmAuto(int mAuto) {
        this.mAuto = mAuto;
    }

    public String getmExpenses() {
        return mExpenses;
    }

    public void setmExpenses(String mExpenses) {
        this.mExpenses = mExpenses;
    }

    public int getmMoney() {
        return mMoney;
    }

    public void setmMoney(int mMoney) {
        this.mMoney = mMoney;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }
}
