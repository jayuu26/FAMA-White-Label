package com.fama.app.greendaodb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ist on 13/10/16.
 */

@Entity
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @NotNull
    private Long userid;
    private String city;
    private String country;
    private String activationkey;
    private String email;
    private Boolean enabled;
    private String firstname;
    private String lastname;
    private String password;
    private String state;
    private String telephone;
    private String username;
    private Long zip;
    private String merchantPin;
    private FAMA famaWallet;

    @Generated(hash = 241287118)
    public Inventory(@NotNull Long userid, String city, String country,
            String activationkey, String email, Boolean enabled, String firstname,
            String lastname, String password, String state, String telephone,
            String username, Long zip, String merchantPin) {
        this.userid = userid;
        this.city = city;
        this.country = country;
        this.activationkey = activationkey;
        this.email = email;
        this.enabled = enabled;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.state = state;
        this.telephone = telephone;
        this.username = username;
        this.zip = zip;
        this.merchantPin = merchantPin;
    }

    @Generated(hash = 375708430)
    public Inventory() {
    }

    public FAMA getFamaWallet() {
        return famaWallet;
    }

    public void setFamaWallet(FAMA famaWallet) {

        this.famaWallet = new FAMA();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getZip() {
        return zip;
    }

    public void setZip(Long zip) {
        this.zip = zip;
    }

    public String getMerchantPin() {
        return merchantPin;
    }

    public void setMerchantPin(String merchantPin) {
        this.merchantPin = merchantPin;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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

    public String getActivationkey() {
        return activationkey;
    }

    public void setActivationkey(String activationkey) {
        this.activationkey = activationkey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
