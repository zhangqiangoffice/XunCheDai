package com.minsheng.app.xunchedai.home.bean;

/**
 * Created by Administrator on 2016/8/9.
 * 贷款申请的实体类
 */
public class Scheme {
    private int installment;
    private double interest;

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double countCapital (double sum) {
        double capital =  sum / installment;
        return capital;
    }

    public double countInterest (double sum) {
        double interests = (sum * interest) / 100;
        return interests;
    }

    public double countTotal (double sum) {
        double total = countCapital(sum) + countInterest(sum);
        return total;
    }

}
