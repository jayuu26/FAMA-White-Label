package com.thunder.pay.dbhelper;

import android.app.Activity;

import com.thunder.pay.activity.App;
import com.thunder.pay.greendaodb.BankDetail;
import com.thunder.pay.greendaodb.BankDetailDao;
import com.thunder.pay.greendaodb.DaoSession;
import com.thunder.pay.util.AppUtills;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;

/**
 * Created by ist on 7/9/16.
 */
public class BankLocationDBHelper {
    DaoSession daoSession;
    BankDetailDao bankDetailDao;
    private Query<BankDetail> bankDetailDaoQuery;

    public enum single {
        INSTANCE;
        BankLocationDBHelper genericDB = new BankLocationDBHelper();

        public BankLocationDBHelper getInstnce() {
            if (genericDB == null)
                return new BankLocationDBHelper();
            else return genericDB;
        }
    }

    public void delete(Activity activity) {
        try {
            daoSession = ((App) activity.getApplication()).getDaoSession();
            bankDetailDao = daoSession.getBankDetailDao();
            bankDetailDao.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert items into Database table
     */
    public void insertItemList(ArrayList<BankDetail> bankDetails, Activity activity) {
        try {
            System.out.println("inside insertItemList " + bankDetails.size());
            daoSession = ((App) activity.getApplication()).getDaoSession();
            this.bankDetailDao = daoSession.getBankDetailDao();
            for (int i = 0; i < bankDetails.size(); i++) {
                    this.bankDetailDao.insertOrReplace(bankDetails.get(i));
            }
            AppUtills.exportDatabase(activity,activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert items into Database table
     */
    public void updateItemList(BankDetailDao bankDetailDao, BankDetail bankDetail) {
        try {
            System.out.println("inside updateItemList " + bankDetail.getBankCode());
            bankDetailDao.updateInTx(bankDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<BankDetail> getBankNameList(Activity activity,String kind) {
        daoSession = ((App) activity.getApplication()).getDaoSession();
        bankDetailDao = daoSession.getBankDetailDao();
        QueryBuilder qb;
        /**
         * Normal Return all items in table
         */
        qb = bankDetailDao.queryBuilder();
        bankDetailDaoQuery = qb.build();

        return (ArrayList<BankDetail>) bankDetailDaoQuery.list();
    }
    /**
     * Fetching items from Database table
     */
    public ArrayList<BankDetail> getItemList(Activity activity,String kind) {
        daoSession = ((App) activity.getApplication()).getDaoSession();
        bankDetailDao = daoSession.getBankDetailDao();
        QueryBuilder qb;
        /**
         * Normal Return all items in table
         */
        qb = bankDetailDao.queryBuilder();
        /**
         *Return items order basis of songs name properties
         */
//        bankDetailDaoQuery = videoDetailsDao.queryBuilder().orderDesc(SongsDetailsDao.Properties.TrackId).build();
        /**
         *Where condition query example
         */
        qb.where(BankDetailDao.Properties.BankCode.eq(kind));

        /**
         *WhereOr condition  query example
         */
//        QueryBuilder qb = videoDetailsDao.queryBuilder();
//        qb.whereOr(SongsDetailsDao.Properties.TrackName.like("%Sajda%"),
//        SongsDetailsDao.Properties.TrackName.like("%Kal Ho Naa Ho%"));

        /**
         *Where condition with OR query example
         */
//        QueryBuilder qb = videoDetailsDao.queryBuilder();
//        qb.where(qb.or(SongsDetailsDao.Properties.TrackName.like("%Sajda%"),
//        SongsDetailsDao.Properties.ArtistName.like("%Sonu%")));

        /**
         *Where condition with AND query example
         */
//        QueryBuilder qb = videoDetailsDao.queryBuilder();
//        qb.where(qb.and(SongsDetailsDao.Properties.TrackName.like("%Kal Ho Naa Ho%"),
//        SongsDetailsDao.Properties.ArtistName.like("%Sonu%"),
//                SongsDetailsDao.Properties.PrimaryGenreName.like("%World%")));

        /**
         *Where condition with LIMIT and OFFSET query example
         */
//        QueryBuilder qb = videoDetailsDao.queryBuilder();
//        qb.offset(0);//Set starting index 0
//        qb.limit(10);//Total number of items return is 10


        bankDetailDaoQuery = qb.build();

        return (ArrayList<BankDetail>) bankDetailDaoQuery.list();
    }



    private boolean isExsist(BankDetailDao bankDetailDao, BankDetail bankDetail) {
        boolean isTrue = false;
        long count =0;
        QueryBuilder qb;

        try {
            qb = bankDetailDao.queryBuilder();
            qb.where(BankDetailDao.Properties.BankCode.eq(bankDetail.getBankCode()));
            count = qb.count();
            if(count>0) {
                isTrue = true;
               updateItemList(bankDetailDao, bankDetail);
            }

        } catch (Exception e) {
            e.printStackTrace();
            isTrue = false;
        }
        System.out.println("BankDetail isExsist "+isTrue+ "count "+count);
        return isTrue;
    }

}
