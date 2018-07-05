package lcsd.com.whirlpool.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
public class DbManager {
    private static MySqliteHelper helper;

    public static MySqliteHelper getIntance(Context context){
        if(helper==null){
            helper=new MySqliteHelper(context);
        }
        return helper;
    }
    /**
     * 根据sql语句查询cursor对象
     *
     */
    public static Cursor selectDataBySql(SQLiteDatabase db,String sql,String[] selectionArgs){
        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(sql,selectionArgs);
        }
        return cursor;
    }
    /**
     * 将Cursor转化为List
     */
    public static List<Sqlentity> cursorToList(Cursor cursor){
        List<Sqlentity> list=new ArrayList<>();
        while (cursor.moveToNext()){
            Sqlentity sqlentity=new Sqlentity();
            sqlentity.setId(cursor.getInt(cursor.getColumnIndex(Constant.ID)));
            sqlentity.setTitle(cursor.getString(cursor.getColumnIndex(Constant.TITLE)));
            sqlentity.setImg(cursor.getString(cursor.getColumnIndex(Constant.IMG)));
            sqlentity.setType(cursor.getString(cursor.getColumnIndex(Constant.TYPE)));
            sqlentity.setTime(cursor.getString(cursor.getColumnIndex(Constant.TIME)));
            list.add(sqlentity);
        }
        return list;
    }
}
