package com.thunder.pay.greendaodb;

/**
 * Created by ist on 16/10/16.
 */

public class TransferDetails {

    private String senderBankAccountNumber;
    private String receiverBankAccountNumber;
    private String receiverWalletEmail;
    private String senderWalletEmail;
    private String email;
    private String comment;
    private String amount;
    private String receiverBkash;

    public String getReceiverBkash() {
        return receiverBkash;
    }

    public void setReceiverBkash(String receiverBkash) {
        this.receiverBkash = receiverBkash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReceiverWalletEmail() {
        return receiverWalletEmail;
    }

    public void setReceiverWalletEmail(String receiverWalletEmail) {
        this.receiverWalletEmail = receiverWalletEmail;
    }

    public String getSenderWalletEmail() {
        return senderWalletEmail;
    }

    public void setSenderWalletEmail(String senderWalletEmail) {
        this.senderWalletEmail = senderWalletEmail;
    }

    public String getSenderBankAccountNumber() {
        return senderBankAccountNumber;
    }

    public void setSenderBankAccountNumber(String senderBankAccountNumber) {
        this.senderBankAccountNumber = senderBankAccountNumber;
    }

    public String getReceiverBankAccountNumber() {
        return receiverBankAccountNumber;
    }

    public void setReceiverBankAccountNumber(String receiverBankAccountNumber) {
        this.receiverBankAccountNumber = receiverBankAccountNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
