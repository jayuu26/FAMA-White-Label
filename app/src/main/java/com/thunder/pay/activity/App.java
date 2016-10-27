package com.thunder.pay.activity;

import android.app.Application;

import com.thunder.pay.greendaodb.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = false;
    private com.thunder.pay.greendaodb.DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        initDB();

    }

    public com.thunder.pay.greendaodb.DaoSession getDaoSession() {
        return daoSession;
    }

    public void initDB(){

        DaoMaster.DevOpenHelper helper = new com.thunder.pay.greendaodb.DaoMaster.DevOpenHelper(this,ENCRYPTED ? "iTune-db-encrypted" : "iTune-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new com.thunder.pay.greendaodb.DaoMaster(db).newSession();
    }
}
