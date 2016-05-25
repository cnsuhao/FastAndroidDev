package com.ijustyce.fastandroiddev.greendaojava;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoExample {

    public static void main(String[] args){

        //  使用greendao你需要先修改fastandroiddev的build.gradle文件，去掉相应注释！
        //  这里是一些原码

        Schema schema = new Schema(2, "com.lzhplus.lzh.db");
        addUser(schema);
        try {
            new DaoGenerator().generateAll(schema, "greendaojava/src-model");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void addUser(Schema schema) {

        Entity brand = schema.addEntity("BrandDb");
        brand.addIntProperty("brandId");
        brand.addIntProperty("since");
        Property cityId = brand.addIntProperty("cityId").getProperty();
        brand.addIntProperty("provinceId");
        brand.addIntProperty("operationType");  //1 新增 2 修改 3 删除
        brand.addStringProperty("brandName");
        brand.addStringProperty("brandLogoImg");
        brand.addStringProperty("bigLogoImg");
        brand.addStringProperty("brandTitle");
        brand.addStringProperty("brandDesc");
        brand.addIdProperty().autoincrement().notNull();

        Entity city = schema.addEntity("CityDb");
        city.addIdProperty().notNull().autoincrement();
     //   city.addToMany(brand, cityId, "brands");
        city.addIntProperty("cityId");
        city.addIntProperty("cityLevel");
        city.addStringProperty("cityName");
        city.addStringProperty("pinyin");
        city.addStringProperty("pyFirstChar");
        city.addIntProperty("pCityId");
    }
}