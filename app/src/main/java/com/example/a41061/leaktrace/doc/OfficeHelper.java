package com.example.a41061.leaktrace.doc;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.example.a41061.leaktrace.chat.activity.TestActivity.TAG;

/**
 * 有关本地文档查询和展示的方法
 *
 * @author FanHongyu.
 * @since 18/3/27 20:33.
 * email fanhongyu@hrsoft.net.
 */

public class OfficeHelper {


    private static final String[] PROJECTION = new String[]{
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.SIZE};


    /**
     * 查询SD卡里可以上传的文档
     */
    public static void queryFiles(Context context, List<OfficeModel> officeModelList, String suffix) {


        //在系统内部的数据库中查询
        Cursor cursor = context.getContentResolver().query(
                //在哪一张表中查询
                Uri.parse("content://media/external/file")
                //投影需要哪些列
                , PROJECTION
                //设置选择条件，相当于sql中的where，
                , MediaStore.Files.FileColumns.DATA + " like ?"
                //查询后缀为.XXX的文件，%相当于任意长度的字符串
                , new String[]{"%." + suffix}
                //相当于SQL语句中的Order by，如果指定字段则默认为升序
                , null);

        /*

        上面的函数可以转化成sql语句为：
        select _id,_data,_size
        from
        where _data like %.XXX
        */


        if (cursor != null) {
            if (cursor.moveToFirst()) {

                int idIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
                int dataIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int sizeIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                do {
                    String id = cursor.getString(idIndex);
                    String path = cursor.getString(dataIndex);
                    String size = cursor.getString(sizeIndex);
                    String name = path.substring(path.lastIndexOf("/") + 1);


                    Log.i(TAG, "queryFiles: " + path);
                    officeModelList.add(new OfficeModel(id, path, size, name, suffix));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }




}
