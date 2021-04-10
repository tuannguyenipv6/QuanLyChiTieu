package com.example.qunlchitiu.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qunlchitiu.Object.Spending;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseSpending extends SQLiteOpenHelper {

    public DatabaseSpending(@Nullable Context context) {
        super(context, "Spending.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //khởi tạo 1 bảng Spending
        String sql = "CREATE TABLE IF NOT EXISTS " +
                "Spending(dAuto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dExpenses VARCHAR(200), " +
                "dMoney INTEGER(200), " +
                "dTime TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //thêm Spending
    public void newAdd(Spending spending){
        //chuyển từ date sang long
        long unixTime = spending.getmTime().getTime() / 1000;
        //long sang string
        String time = String.valueOf(unixTime);

        SQLiteDatabase database = this.getWritableDatabase();   //phương thức getWritableDatabase() dùng để nghi lên Database
        //Không thể lưu trực tiếp 1 đối tượng vào thẳng Database mà phải thông qua ContenValues để lưu g.trị vào Database
        ContentValues contentValues = new ContentValues();
        contentValues.put("dExpenses", spending.getmExpenses());
        contentValues.put("dMoney", spending.getmMoney());
        contentValues.put("dTime", time);//put time kiểu string

        database.insert("Spending", "", contentValues);//insert dữ liệu vừa thêm vào database
        database.close();
    }

    //lấy tất cả Spending
    public List<Spending> AllSpending(){
        String sql = "SELECT * FROM Spending ";        //truy vấn tất cả trong bản
        List<Spending> mSpendings = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor

            String pExpenses = cursor.getString(1);
            int pMoney = cursor.getInt(2);
            String pDate = cursor.getString(3);

            long lDate = Long.parseLong(pDate);
            // convert seconds to milliseconds
            Date date = new java.util.Date(lDate*1000L);

            Spending spending = new Spending(pExpenses, pMoney, date);
            mSpendings.add(spending);

            cursor.moveToNext();
        }
        this.close();
        return mSpendings;
    }
}
