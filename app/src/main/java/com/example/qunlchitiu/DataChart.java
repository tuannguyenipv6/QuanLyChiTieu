package com.example.qunlchitiu;

public class DataChart {
    private String mMoth;
    private int mMoney;

    public String getmMoth() {
        return mMoth;
    }

    public void setmMoth(String mMoth) {
        this.mMoth = mMoth;
    }

    public int getmMoney() {
        return mMoney;
    }

    public void setmMoney(int mMoney) {
        this.mMoney = mMoney;
    }

    public DataChart(String mMoth, int mMoney) {
        this.mMoth = mMoth;
        this.mMoney = mMoney;
    }
}
