package com.thunder.pay.daomodel;

import com.thunder.pay.greendaodb.FAMA;
import com.thunder.pay.greendaodb.Inventory;

/**
 * Created by ist on 25/8/16.
 */
public class DataHandler {



    public enum Single{
        INSTANCE;
        DataHandler s=new DataHandler();
        public DataHandler getInstance(){
            if(s==null)
                return new DataHandler();
            else return s;
        }
    }

    private  Inventory inventory;
    private FAMA famaWallet;

    public FAMA getFamaWallet() {
        return famaWallet;
    }

    public void setFamaWallet(FAMA famaWallet) {
        this.famaWallet = famaWallet;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
