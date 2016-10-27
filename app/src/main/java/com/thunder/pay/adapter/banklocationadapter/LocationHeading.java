package com.thunder.pay.adapter.banklocationadapter;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

public class LocationHeading implements Parent<LocationSubHeading> {

    private String bankName;
    private String bankAdd;
    private List<LocationSubHeading> locationDetails;

    public LocationHeading() {
    }

    public LocationHeading(String name, List<LocationSubHeading> locationDetails) {
        bankName = name;
        this.locationDetails = locationDetails;
    }

    public String getName() {
        return bankName;
    }

    @Override
    public List<LocationSubHeading> getChildList() {
        return locationDetails;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public LocationSubHeading getIngredient(int position) {
        return locationDetails.get(position);
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAdd() {
        return bankAdd;
    }

    public void setBankAdd(String bankAdd) {
        this.bankAdd = bankAdd;
    }

    public List<LocationSubHeading> getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(List<LocationSubHeading> locationDetails) {
        this.locationDetails = locationDetails;
    }
}
