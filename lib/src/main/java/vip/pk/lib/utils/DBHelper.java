package vip.pk.pklib.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Joshua 用法： DBHelper dbHelper = new DBHelper(this);
 *         dbHelper.createDataBase(); SQLiteDatabase db =
 *         dbHelper.getWritableDatabase(); Cursor cursor = db.query()
 *         db.execSQL(sqlString); 注意：execSQL不支持带;的多条SQL语句，只能一条一条的执行，晕了很久才明白
 *         见execSQL的源码注释 (Multiple statements separated by ;s are not
 *         supported.) 将把assets下的数据库文件直接复制到DB_PATH，但数据库文件大小限制在1M以下
 *         如果有超过1M的大文件，则需要先分割为N个小文件，然后使用copyBigDatabase()替换copyDatabase()
 */
public class DBHelper  extends SQLiteOpenHelper {
    private static Map<String, DBHelper> dbMaps = new HashMap<String, DBHelper>();
    private OnSqliteUpdateListener onSqliteUpdateListener;
    /**
     * 建表语句列表
     */
    private List<String> createTableList;
    private String nowDbName;

    private DBHelper(Context context, String dbName, int dbVersion, List<String> tableSqls) {
        super(context, dbName, null, dbVersion);
        nowDbName = dbName;
        createTableList = new ArrayList<String>();
        createTableList.addAll(tableSqls);
    }

    /**
     *
     * @Title: getInstance
     * @Description: 获取数据库实例
     * @param @param context
     * @param @param userId
     * @param @return
     * @return DataBaseOpenHelper
     * @author lihy
     */
    public static DBHelper getInstance(Context context, String dbName, int dbVersion, List<String> tableSqls) {
        DBHelper dataBaseOpenHelper = dbMaps.get(dbName);
        if (dataBaseOpenHelper == null) {
            dataBaseOpenHelper = new DBHelper(context, dbName, dbVersion, tableSqls);
        }
        dbMaps.put(dbName, dataBaseOpenHelper);
        return dataBaseOpenHelper;
    };
    public static DBHelper getInstance(Context context, String dbName, int dbVersion, String... tableSqlsStr) {
        DBHelper dataBaseOpenHelper = dbMaps.get(dbName);
        List<String> tableSqls = new ArrayList<String>();
        for(String sql:tableSqlsStr){
            tableSqls.add(sql);
        }
        if (dataBaseOpenHelper == null) {
            dataBaseOpenHelper = new DBHelper(context, dbName, dbVersion, tableSqls);
        }
        dbMaps.put(dbName, dataBaseOpenHelper);
        return dataBaseOpenHelper;
    };

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sqlString : createTableList) {
            db.execSQL(sqlString);
        }
    }

    /**
     *
     * @Title: execSQL
     * @Description: Sql写入
     * @param @param sql
     * @param @param bindArgs
     * @return void
     * @author lihy
     */
    public void execSQL(String sql, Object[] bindArgs) {
        DBHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.execSQL(sql, bindArgs);
        }
    }

    /**
     *
     * @Title: rawQuery
     * @Description:
     * @param @param sql查询
     * @param @param bindArgs
     * @param @return
     * @return Cursor
     * @author lihy
     */
    public Cursor rawQuery(String sql, String[] bindArgs) {
        DBHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, bindArgs);
            return cursor;
        }
    }

    /**
     *
     * @Title: insert
     * @Description: 插入数据
     * @param @param table
     * @param @param contentValues 设定文件
     * @return void 返回类型
     * @author lihy
     * @throws
     */
    public void insert(String table, ContentValues contentValues) {
        DBHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.insert(table, null, contentValues);
        }
    }

    /**
     *
     * @Title: update
     * @Description: 更新
     * @param @param table
     * @param @param values
     * @param @param whereClause
     * @param @param whereArgs 设定文件
     * @return void 返回类型
     * @throws
     */
    public void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        DBHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.update(table, values, whereClause, whereArgs);

        }
    }
    /**
     *
     * @Title: delete
     * @Description:删除
     * @param @param table
     * @param @param whereClause
     * @param @param whereArgs
     * @return void
     * @author lihy
     */
    public void delete(String table, String whereClause, String[] whereArgs) {
        DBHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.delete(table, whereClause, whereArgs);
        }
    }

    /**
     *
     * @Title: query
     * @Description: 查
     * @param @param table
     * @param @param columns
     * @param @param selection
     * @param @param selectionArgs
     * @param @param groupBy
     * @param @param having
     * @param @param orderBy
     * @return void
     * @author lihy
     */
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        DBHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            // Cursor cursor = database.rawQuery("select * from "
            // + TableName.TABLE_NAME_USER + " where userId =" + userId, null);
            Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            return cursor;
        }
    }
    /**
     *
     * @Description:查
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return
     * Cursor
     * @exception:
     * @author: lihy
     * @time:2015-4-3 上午9:37:29
     */
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                        String orderBy,String limit) {
        DBHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            // Cursor cursor = database.rawQuery("select * from "
            // + TableName.TABLE_NAME_USER + " where userId =" + userId, null);
            Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            return cursor;
        }
    }

    /**
     *
     * @Description 查询，方法重载,table表名，sqlString条件
     * @param @return
     * @return Cursor
     * @author lihy
     */
    public Cursor query(String tableName, String sqlString) {
        DBHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("select * from " + tableName + " " + sqlString, null);

            return cursor;
        }
    }

    /**
     * @see android.database.sqlite.SQLiteOpenHelper#close()
     */
    public void clear() {
        DBHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        dataBaseOpenHelper.close();
        dbMaps.remove(dataBaseOpenHelper);
    }

    /**
     * onUpgrade()方法在数据库版本每次发生变化时都会把用户手机上的数据库表删除，然后再重新创建。<br/>
     * 一般在实际项目中是不能这样做的，正确的做法是在更新数据库表结构时，还要考虑用户存放于数据库中的数据不会丢失,从版本几更新到版本几。(非
     * Javadoc)
     *
     * @see //android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
     *      .SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        if (onSqliteUpdateListener != null) {
            onSqliteUpdateListener.onSqliteUpdateListener(db, arg1, arg2);
        }
    }

    public void setOnSqliteUpdateListener(OnSqliteUpdateListener onSqliteUpdateListener) {
        this.onSqliteUpdateListener = onSqliteUpdateListener;
    }


    public interface OnSqliteUpdateListener {
        public void onSqliteUpdateListener(SQLiteDatabase db, int oldVersion, int newVersion);
    }
}