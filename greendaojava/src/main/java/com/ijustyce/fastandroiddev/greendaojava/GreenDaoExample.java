package com.ijustyce.fastandroiddev.greendaojava;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoExample {

    public static void main(String[] args){

        //  使用greendao你需要先修改fastandroiddev的build.gradle文件，去掉相应注释！
        //  这里是一些原码

        Schema schema = new Schema(2, "com.ijustyce.chat.model");
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
        Entity Record = schema.addEntity("Record");

        Record.addIdProperty();
        Record.addStringProperty("date");
        Record.addStringProperty("msg");
        Record.addStringProperty("from");
        Record.addIntProperty("type");
//        Property phone = note.addStringProperty("phone").notNull().getProperty();
//        note.addToOne(entity, phone);
    }

    private static void addUser(Schema schema) {
        Entity note = schema.addEntity("User");

        note.addIdProperty();
        note.addStringProperty("email");
        note.addStringProperty("head");
        note.addStringProperty("name");
        note.addIntProperty("identity");
        note.addStringProperty("head");
        note.addBooleanProperty("delete");
    }
}