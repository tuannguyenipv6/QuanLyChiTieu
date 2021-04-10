package com.example.qunlchitiu.Object;

import java.util.Date;

public class Spending {
    private String mExpenses;
    private int mMoney;
    private Date mTime;

    public Spending(String mExpenses, int mMoney, Date mTime) {
        this.mExpenses = mExpenses;
        this.mMoney = mMoney;
        this.mTime = mTime;
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
