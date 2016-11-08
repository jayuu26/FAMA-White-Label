package com.fama.app.dbhelper;

import android.app.Activity;

import com.fama.app.activity.App;
import com.fama.app.greendaodb.DaoSession;
import com.fama.app.greendaodb.Inventory;
import com.fama.app.greendaodb.InventoryDao;
import com.fama.app.util.AppUtills;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by ist on 7/9/16.
 */
public class InventoryDBHelper {
    DaoSession daoSession;
    InventoryDao inventoryDao;
    private Query<Inventory> inventoryQuery;

    public enum single {
        INSTANCE;
        InventoryDBHelper genericDB = new InventoryDBHelper();

        public InventoryDBHelper getInstnce() {
            if (genericDB == null)
                return new InventoryDBHelper();
            else return genericDB;
        }
    }

    public void deleteSongList(Activity activity) {
        try {
            daoSession = ((App) activity.getApplication()).getDaoSession();
            inventoryDao = daoSession.getInventoryDao();
            inventoryDao.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert items into Database table
     */
    public void insertItem(Inventory inventory, Activity activity) {
        try {
            System.out.println("inside insertItemList " + inventory);
            daoSession = ((App) activity.getApplication()).getDaoSession();
            this.inventoryDao = daoSession.getInventoryDao();
            this.inventoryDao.insertOrReplace(inventory);
            AppUtills.exportDatabase(activity,activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert items into Database table
     */
    public void updateItemList(InventoryDao inventoryDao, Inventory inventory) {
        try {
            System.out.println("inside updateItemList " + inventory.getEmail());
            //inventory.setTrackName("Update-"+ inventory.getTrackName());
            //videoDetailsDao.save(songsDetails);
            //videoDetailsDao.update(songsDetails);
            inventoryDao.updateInTx(inventory);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetching items from Database table
     */
    public Inventory getItemList(Activity activity,String kind) {
        daoSession = ((App) activity.getApplication()).getDaoSession();
        inventoryDao = daoSession.getInventoryDao();
        QueryBuilder qb;
        /**
         * Normal Return all items in table
         */
        qb = inventoryDao.queryBuilder();
        /**
         *Return items order basis of songs name properties
         */
//        songsDetailsQuery = videoDetailsDao.queryBuilder().orderDesc(SongsDetailsDao.Properties.TrackId).build();
        /**
         *Where condition query example
         */
//        qb.where(InventoryDao.Properties.Userid.eq(kind));

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


        inventoryQuery = qb.build();

        return inventoryQuery.unique();
    }


    private boolean isExsist(InventoryDao inventoryDao, Inventory inventory) {
        boolean isTrue = false;
        long count =0;
        QueryBuilder qb;

        try {
            qb = inventoryDao.queryBuilder();
            qb.where(InventoryDao.Properties.Userid.eq(inventory.getUserid()));
            count = qb.count();
            if(count>0) {
                isTrue = true;
               updateItemList(inventoryDao, inventory);
            }

        } catch (Exception e) {
            e.printStackTrace();
            isTrue = false;
        }
        System.out.println("SongsDetails isExsist "+isTrue+ "count "+count);
        return isTrue;
    }

}
