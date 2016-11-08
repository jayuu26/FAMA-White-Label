package com.fama.app.greendaodb;

/**
 * Created by ist on 18/10/16.
 */
public class FAMA {

    private String currentAmount;
    private String lastUsedOn;
    private String createdOn;
    private String currencyCode;
    private String id;

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getLastUsedOn() {
        return lastUsedOn;
    }

    public void setLastUsedOn(String lastUsedOn) {
        this.lastUsedOn = lastUsedOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
