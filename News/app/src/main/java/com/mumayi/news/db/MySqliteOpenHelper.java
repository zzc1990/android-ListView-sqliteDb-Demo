package com.mumayi.news.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenhe on 2016/11/7.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
//        Android应用程序更新的时候如果数据库修改了字段需要更新数据库，并且保留原来的数据库数据：
//        这是原有的数据库表
//        CREATE_BOOK = "create table book(bookId integer primarykey,bookName text);";
//        然后我们增加一个字段：
//        CREATE_BOOK = "create table book(bookId integer primarykey,bookName text,bookContent text);";
//        首先我们需要把原来的数据库表重命名一下
//        CREATE_TEMP_BOOK = "alter table book rename to _temp_book";
//        然后把备份表中的数据copy到新创建的数据库表中
//        INSERT_DATA = "insert into book select *,' ' from _temp_book";（注意' '是为新加的字段插入默认值的必须加上，否则就会出错）。
//        然后我们把备份表干掉就行啦。
//        DROP_BOOK = "drop table _temp_book";

    /////////////数据库升级 //////////
    private String CREATE_NEWS = "create table news (_id integer primary key , title varchar(200) ,des varchar(300)" +
            ",time varchar(50),\" +\n" + "\"news_url varchar(120)" +
            ",icon_url varchar(120),comment integer,type integer\" +\n" + "\");";

    private String CREATE_TEMP_NEWS = "alter table news rename to _temp_news";

    private String INSERT_DATA = "insert into news select *,'' from _temp_news";

    private String DROP_NEWS = "drop table _temp_news";
    /////////////数据库升级/////////


    public MySqliteOpenHelper(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, "mumayi.db", null, 2);//由 1 >>> 2 (数据库升级)
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table news (_id integer primary key , title varchar(200) ,des varchar(300),time varchar(50)," +
                "news_url varchar(120),icon_url varchar(120),comment integer,type integer" +
                ")");

    }

    // 升级数据库版本
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        什么时候需要更新版本? --- app 功能升级,字段和原来不同,需要改变数据库表结构
//        为什么要在方法里写for循环，主要是考虑到夸版本升级，比如有的用户一直不升级版本，数据库版本号一直是1，而客
//        户端最新版本其实对应的数据库版本已经是4了，那么我中途可能对数据库做了很多修改，通过这个for循环，可以迭代
//        升级，不会发生错误。
        for (int j = oldVersion; j <= newVersion; j++) {
            switch (j) {
                case 2:
                    db.execSQL(CREATE_TEMP_NEWS);

                    db.execSQL(CREATE_NEWS);

                    db.execSQL(INSERT_DATA);

                    db.execSQL(DROP_NEWS);

                    break;

            }
        }

    }

    //降级的方法
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}

