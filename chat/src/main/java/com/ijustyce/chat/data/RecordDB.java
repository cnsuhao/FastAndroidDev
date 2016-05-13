package com.ijustyce.chat.data;

import com.ijustyce.chat.AppApplication;
import com.ijustyce.chat.model.Record;
import com.ijustyce.chat.model.RecordDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by yc on 2016/5/3 0003.  信息
 */
public class RecordDB {

    public static Record saveOrUpdate(Record record) {

        AppApplication.getRecordDao().insertOrReplace(record);
        return record;
    }

    public static Record getRecordById(long id) {

        return AppApplication.getRecordDao().queryBuilder().where(RecordDao.Properties.Id.eq(id)).unique();
    }

    public static List<Record> searchRecord(String key, int pageSize, int pageNo) {

        key = "%" + key + "%";
        QueryBuilder<Record> builder = AppApplication.getRecordDao().queryBuilder();
        builder.whereOr(RecordDao.Properties.Msg.like(key), RecordDao.Properties.Date.like(key));
        return builder.offset((pageNo - 1) * pageSize).limit(pageSize)
                .orderDesc(RecordDao.Properties.Id).list();
    }

    public static void deleteById(long id) {

        AppApplication.getRecordDao().deleteByKey(id);
    }

    public static List<Record> listRecord(int pageSize, int pageNo) {

        QueryBuilder<Record> builder = AppApplication.getRecordDao().queryBuilder();
        builder.offset((pageNo - 1) * pageSize).limit(pageSize).orderDesc(RecordDao.Properties.Id);
        return builder.list();
    }
}
