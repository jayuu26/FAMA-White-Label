package com.thunder.pay.greendaodb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class BankDetail implements Serializable {



    public static final long serialVersionUID = 1L;
    @Id
    @NotNull
    private Long id;

    private String bankName;

    private String bankCode;

    private Long micrCode;

    private String description;

    private String address;

    private Double latitude;

    private Double longitude;

    private String branchContactNumber;

    private String city;

    private String country;

    private Boolean isHeadQuarter;

    private String accountNumber;

    private String balance;

    private String accountHolderName;

    private String customerId;

    private User user;

    private AccountDetail accountDetail;

    private String lastUsedOn;

    private String ifscCode;

    /*For Bank History*/
    private String amount;
    private String transactionType;
    private String transferType;
    private String previousBankAmount;
    private String updatedBankAmount;
    private String usedOn;
    private String comment;

    /*For FAMA Wallet History*/
    private String previousWalletAmount;
    private String updatedWalletAmount;

    private ProcessedBy processedBy;

    public ProcessedBy getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(ProcessedBy processedBy) {
        this.processedBy = processedBy;
    }

    @Generated(hash = 1504205932)
    public BankDetail(@NotNull Long id, String bankName, String bankCode,
            Long micrCode, String description, String address, Double latitude,
            Double longitude, String branchContactNumber, String city, String country,
            Boolean isHeadQuarter) {
        this.id = id;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.micrCode = micrCode;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.branchContactNumber = branchContactNumber;
        this.city = city;
        this.country = country;
        this.isHeadQuarter = isHeadQuarter;
    }

    @Generated(hash = 80651026)
    public BankDetail() {
    }


    /*For Wallet History*/

    public String getPreviousWalletAmount() {
        return previousWalletAmount;
    }

    public void setPreviousWalletAmount(String previousWalletAmount) {
        this.previousWalletAmount = previousWalletAmount;
    }

    public String getUpdatedWalletAmount() {
        return updatedWalletAmount;
    }

    public void setUpdatedWalletAmount(String updatedWalletAmount) {
        this.updatedWalletAmount = updatedWalletAmount;
    }

    /*For Bank History*/

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getPreviousBankAmount() {
        return previousBankAmount;
    }

    public void setPreviousBankAmount(String previousBankAmount) {
        this.previousBankAmount = previousBankAmount;
    }

    public String getUpdatedBankAmount() {
        return updatedBankAmount;
    }

    public void setUpdatedBankAmount(String updatedBankAmount) {
        this.updatedBankAmount = updatedBankAmount;
    }

    public String getUsedOn() {
        return usedOn;
    }

    public void setUsedOn(String usedOn) {
        this.usedOn = usedOn;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /*For History*/

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getLastUsedOn() {
        return lastUsedOn;
    }

    public void setLastUsedOn(String lastUsedOn) {
        this.lastUsedOn = lastUsedOn;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccountDetail getAccountDetail() {
        return accountDetail;
    }

    public void setAccountDetail(AccountDetail accountDetail) {
        this.accountDetail = accountDetail;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Long getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(Long micrCode) {
        this.micrCode = micrCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getBranchContactNumber() {
        return branchContactNumber;
    }

    public void setBranchContactNumber(String branchContactNumber) {
        this.branchContactNumber = branchContactNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getIsHeadQuarter() {
        return isHeadQuarter;
    }

    public void setIsHeadQuarter(Boolean isHeadQuarter) {
        this.isHeadQuarter = isHeadQuarter;
    }


}