package com.ijustyce.fastandroiddev.greendaojava;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoExample {

    public static void main(String[] args){

        //  使用greendao你需要先修改fastandroiddev的build.gradle文件，去掉相应注释！
        //  这里是一些原码

        Schema schema = new Schema(1, "com.ijustyce.health.model");
        addRecord(schema);
        addUser(schema);
        try {
            new DaoGenerator().generateAll(schema, "greendaojava/src-model");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void showExample(){

        /**
         for this example here is some code

         DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
         SQLiteDatabase db = helper.getWritableDatabase();
         DaoMaster daoMaster = new DaoMaster(db);
         DaoSession daoSession = daoMaster.newSession();

         noteDao.insert(new Note());
         noteDao.update(new Note());
         noteDao.delete(new Note());
         noteDao.deleteByKey(new Note().getId());
         noteDao.loadAll();
         noteDao.queryBuilder().where(NoteDao.Properties.Id.ge(10)).list();  //  >=
         noteDao.queryBuilder().where(NoteDao.Properties.Id.gt(10)).list();  //  >
         noteDao.queryBuilder().where(NoteDao.Properties.Id.le(10)).list();  //  <=
         noteDao.queryBuilder().where(NoteDao.Properties.Id.lt(10)).list();  //  <

         //  ge greater or equal gt greater than , le less or equal lt less then

         */
    }

    private static void addRecord(Schema schema){
        Entity note = schema.addEntity("Record");

        note.addIdProperty();
        note.addStringProperty("desc");
        note.addIntProperty("userId");
        note.addIntProperty("ownerId");
        note.addIntProperty("type");
        note.addBooleanProperty("delete");
        note.addIntProperty("status");
    }

    private static void addUser(Schema schema) {
        Entity note = schema.addEntity("User");

        note.addIdProperty();
        note.addStringProperty("phone");
        note.addStringProperty("pw");
        note.addStringProperty("name");
        note.addIntProperty("identity");
        note.addStringProperty("head");
        note.addBooleanProperty("delete");
    }
}
