package vip.pk.lib.bean;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import vip.pk.pklib.utils.DBHelper;
import vip.pk.pklib.utils.T;

public class QueryData {

    public static String getInitDbSql(String sql){
        if(sql.equals("")){
            return "CREATE TABLE IF NOT EXISTS sport (id INTEGER PRIMARY KEY AUTOINCREMENT, riqi VARCHAR, bushu INTEGER, other TEXT)";
        }else{
            return sql;
        }
    }



}
