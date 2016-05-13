package com.ijustyce.xingche.data;

import com.ijustyce.xingche.AppApplication;
import com.ijustyce.xingche.model.Record;
import com.ijustyce.xingche.model.RecordDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by yc on 2016/5/3 0003.  信息
 */
public class RecordDB {

    public static Record saveOrUpdate(Record record) {

        if (getCount() >= 100){
            deleteById(listRecord(1,1).get(0).getId());
        }
        AppApplication.getRecordDao().insertOrReplace(record);
        return record;
    }

    public static Record getRecordById(long id) {

        return AppApplication.getRecordDao().queryBuilder().where(RecordDao.Properties.Id.eq(id)).unique();
    }

    public static List<Record> searchRecord(String key, int pageSize, int pageNo) {

        key = "%" + key + "%";
        QueryBuilder<Record> builder = AppApplication.getRecordDao().queryBuilder();
        if (!key.equals("%%") && !key.equals("%null%")){
            builder.whereOr(RecordDao.Properties.Desc.like(key), RecordDao.Properties.Title.like(key), RecordDao.Properties.Date.like(key));
        }
        return builder.offset((pageNo - 1) * pageSize).limit(pageSize)
                .orderDesc(RecordDao.Properties.Id).list();
    }

    public static void deleteById(long id) {

        AppApplication.getRecordDao().deleteByKey(id);
    }

    public static long getCount(){
        return AppApplication.getRecordDao().queryBuilder().count();
    }

    public static List<Record> listRecord(int pageSize, int pageNo) {

        QueryBuilder<Record> builder = AppApplication.getRecordDao().queryBuilder();
        builder.offset((pageNo - 1) * pageSize).limit(pageSize).orderDesc(RecordDao.Properties.Id);
        return builder.list();
    }
}
