package com.fama.app.greendaodb;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BANK_DETAIL".
*/
public class BankDetailDao extends AbstractDao<BankDetail, Long> {

    public static final String TABLENAME = "BANK_DETAIL";

    /**
     * Properties of entity BankDetail.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BankName = new Property(1, String.class, "bankName", false, "SENDER_EMAIL");
        public final static Property BankCode = new Property(2, String.class, "bankCode", false, "PASSWORD");
        public final static Property MicrCode = new Property(3, Long.class, "micrCode", false, "MICR_CODE");
        public final static Property Description = new Property(4, String.class, "description", false, "DESCRIPTION");
        public final static Property Address = new Property(5, String.class, "address", false, "ADDRESS");
        public final static Property Latitude = new Property(6, Double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(7, Double.class, "longitude", false, "LONGITUDE");
        public final static Property BranchContactNumber = new Property(8, Long.class, "branchContactNumber", false, "BRANCH_CONTACT_NUMBER");
        public final static Property City = new Property(9, String.class, "city", false, "CITY");
        public final static Property Country = new Property(10, String.class, "country", false, "COUNTRY");
        public final static Property IsHeadQuarter = new Property(11, Boolean.class, "isHeadQuarter", false, "IS_HEAD_QUARTER");
    }


    public BankDetailDao(DaoConfig config) {
        super(config);
    }
    
    public BankDetailDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BANK_DETAIL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"SENDER_EMAIL\" TEXT," + // 1: bankName
                "\"PASSWORD\" TEXT," + // 2: bankCode
                "\"MICR_CODE\" INTEGER," + // 3: micrCode
                "\"DESCRIPTION\" TEXT," + // 4: description
                "\"ADDRESS\" TEXT," + // 5: address
                "\"LATITUDE\" REAL," + // 6: latitude
                "\"LONGITUDE\" REAL," + // 7: longitude
                "\"BRANCH_CONTACT_NUMBER\" INTEGER," + // 8: branchContactNumber
                "\"CITY\" TEXT," + // 9: city
                "\"COUNTRY\" TEXT," + // 10: country
                "\"IS_HEAD_QUARTER\" INTEGER);"); // 11: isHeadQuarter
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BANK_DETAIL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BankDetail entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String bankName = entity.getBankName();
        if (bankName != null) {
            stmt.bindString(2, bankName);
        }
 
        String bankCode = entity.getBankCode();
        if (bankCode != null) {
            stmt.bindString(3, bankCode);
        }
 
        Long micrCode = entity.getMicrCode();
        if (micrCode != null) {
            stmt.bindLong(4, micrCode);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(6, address);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(7, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(8, longitude);
        }

//        String branchContactNumber = entity.getBranchContactNumber();
//        if (branchContactNumber != null) {
//            stmt.bindLong(9, branchContactNumber);
//        }
//
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(10, city);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(11, country);
        }
 
        Boolean isHeadQuarter = entity.getIsHeadQuarter();
        if (isHeadQuarter != null) {
            stmt.bindLong(12, isHeadQuarter ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BankDetail entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String bankName = entity.getBankName();
        if (bankName != null) {
            stmt.bindString(2, bankName);
        }
 
        String bankCode = entity.getBankCode();
        if (bankCode != null) {
            stmt.bindString(3, bankCode);
        }
 
        Long micrCode = entity.getMicrCode();
        if (micrCode != null) {
            stmt.bindLong(4, micrCode);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(6, address);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(7, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(8, longitude);
        }

//        String branchContactNumber = entity.getBranchContactNumber();
//        if (branchContactNumber != null) {
//            stmt.bindLong(9, branchContactNumber);
//        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(10, city);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(11, country);
        }
 
        Boolean isHeadQuarter = entity.getIsHeadQuarter();
        if (isHeadQuarter != null) {
            stmt.bindLong(12, isHeadQuarter ? 1L: 0L);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public BankDetail readEntity(Cursor cursor, int offset) {
        BankDetail entity = new BankDetail( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // bankName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // bankCode
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // micrCode
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // description
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // address
            cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6), // latitude
            cursor.isNull(offset + 7) ? null : cursor.getDouble(offset + 7), // longitude
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // branchContactNumber
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // city
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // country
            cursor.isNull(offset + 11) ? null : cursor.getShort(offset + 11) != 0 // isHeadQuarter
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BankDetail entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setBankName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBankCode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMicrCode(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAddress(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLatitude(cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6));
        entity.setLongitude(cursor.isNull(offset + 7) ? null : cursor.getDouble(offset + 7));
        entity.setBranchContactNumber(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCity(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCountry(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setIsHeadQuarter(cursor.isNull(offset + 11) ? null : cursor.getShort(offset + 11) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BankDetail entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BankDetail entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BankDetail entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
