package com.lzhplus.lzh.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.lzhplus.lzh.db.CityDb;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CITY_DB".
*/
public class CityDbDao extends AbstractDao<CityDb, Long> {

    public static final String TABLENAME = "CITY_DB";

    /**
     * Properties of entity CityDb.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property CityId = new Property(1, Integer.class, "cityId", false, "CITY_ID");
        public final static Property CityLevel = new Property(2, Integer.class, "cityLevel", false, "CITY_LEVEL");
        public final static Property CityName = new Property(3, String.class, "cityName", false, "CITY_NAME");
        public final static Property Pinyin = new Property(4, String.class, "pinyin", false, "PINYIN");
        public final static Property PyFirstChar = new Property(5, String.class, "pyFirstChar", false, "PY_FIRST_CHAR");
        public final static Property PCityId = new Property(6, Integer.class, "pCityId", false, "P_CITY_ID");
    };


    public CityDbDao(DaoConfig config) {
        super(config);
    }
    
    public CityDbDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CITY_DB\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"CITY_ID\" INTEGER," + // 1: cityId
                "\"CITY_LEVEL\" INTEGER," + // 2: cityLevel
                "\"CITY_NAME\" TEXT," + // 3: cityName
                "\"PINYIN\" TEXT," + // 4: pinyin
                "\"PY_FIRST_CHAR\" TEXT," + // 5: pyFirstChar
                "\"P_CITY_ID\" INTEGER);"); // 6: pCityId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CITY_DB\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CityDb entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        Integer cityId = entity.getCityId();
        if (cityId != null) {
            stmt.bindLong(2, cityId);
        }
 
        Integer cityLevel = entity.getCityLevel();
        if (cityLevel != null) {
            stmt.bindLong(3, cityLevel);
        }
 
        String cityName = entity.getCityName();
        if (cityName != null) {
            stmt.bindString(4, cityName);
        }
 
        String pinyin = entity.getPinyin();
        if (pinyin != null) {
            stmt.bindString(5, pinyin);
        }
 
        String pyFirstChar = entity.getPyFirstChar();
        if (pyFirstChar != null) {
            stmt.bindString(6, pyFirstChar);
        }
 
        Integer pCityId = entity.getPCityId();
        if (pCityId != null) {
            stmt.bindLong(7, pCityId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CityDb readEntity(Cursor cursor, int offset) {
        CityDb entity = new CityDb( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // cityId
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // cityLevel
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // cityName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // pinyin
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // pyFirstChar
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6) // pCityId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CityDb entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setCityId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setCityLevel(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setCityName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPinyin(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPyFirstChar(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPCityId(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CityDb entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CityDb entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}