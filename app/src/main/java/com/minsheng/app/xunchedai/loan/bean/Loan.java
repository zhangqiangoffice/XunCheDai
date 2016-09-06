package com.minsheng.app.xunchedai.loan.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/9.
 * 贷款申请的实体类
 */
public class Loan implements Serializable {
    private long id;
    private int state;                   //申请状态
    private String name;                 //申请人姓名
    private String phone;                //申请人电话
    private String insurance_amount;     //商业险金额
    private String protection_date;      //起保日期
    private String installment;          //分期数
    private String loan_object;          //放款对象（0=借款人，1=业务员）
    private String loan_amount;          //可贷款额度
    private String warranty;             //保单照片
    private String invoice;              //车险发票
    private String care_card;            //保卡
    private String idcard1;              //借贷人身份证正面
    private String idcard2;              //借贷人身份证反面
    private String bankcard1;            //借贷人储蓄卡正面
    private String bankcard2;            //借贷人储蓄卡反面
    private String license;              //行驶证
    private String agreement_apply;      //借贷申请协议
    private String agreement_entrust;    //三方受托协议
    private String agreement_transfer;   //划扣协议
    private String reason;               //原因
    private String edit_date;            //编辑日期

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCare_card() {
        return care_card;
    }

    public void setCare_card(String care_card) {
        this.care_card = care_card;
    }

    public String getIdcard1() {
        return idcard1;
    }

    public void setIdcard1(String idcard1) {
        this.idcard1 = idcard1;
    }

    public String getIdcard2() {
        return idcard2;
    }

    public void setIdcard2(String idcard2) {
        this.idcard2 = idcard2;
    }

    public String getBankcard1() {
        return bankcard1;
    }

    public void setBankcard1(String bankcard1) {
        this.bankcard1 = bankcard1;
    }

    public String getBankcard2() {
        return bankcard2;
    }

    public void setBankcard2(String bankcard2) {
        this.bankcard2 = bankcard2;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getInsurance_amount() {
        return insurance_amount;
    }

    public void setInsurance_amount(String insurance_amount) {
        this.insurance_amount = insurance_amount;
    }

    public String getProtection_date() {
        return protection_date;
    }

    public void setProtection_date(String protection_date) {
        this.protection_date = protection_date;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getAgreement_apply() {
        return agreement_apply;
    }

    public void setAgreement_apply(String agreement_apply) {
        this.agreement_apply = agreement_apply;
    }

    public String getAgreement_entrust() {
        return agreement_entrust;
    }

    public void setAgreement_entrust(String agreement_entrust) {
        this.agreement_entrust = agreement_entrust;
    }

    public String getAgreement_transfer() {
        return agreement_transfer;
    }

    public void setAgreement_transfer(String agreement_transfer) {
        this.agreement_transfer = agreement_transfer;
    }

    public String getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(String edit_date) {
        this.edit_date = edit_date;
    }

    public String getLoan_object() {
        return loan_object;
    }

    public void setLoan_object(String loan_object) {
        this.loan_object = loan_object;
    }
}
