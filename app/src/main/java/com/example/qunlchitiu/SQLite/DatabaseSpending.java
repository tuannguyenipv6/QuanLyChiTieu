package com.example.qunlchitiu.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qunlchitiu.Object.Spending;

import java.text.SimpleDateFormat;
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

            int pAuto = cursor.getInt(0);
            String pExpenses = cursor.getString(1);
            int pMoney = cursor.getInt(2);
            String pDate = cursor.getString(3);

            long lDate = Long.parseLong(pDate);
            // convert seconds to milliseconds
            Date date = new java.util.Date(lDate*1000L);

            Spending spending = new Spending(pExpenses, pMoney, date, pAuto);
            mSpendings.add(spending);

            cursor.moveToNext();
        }
        this.close();
        return mSpendings;
    }

    //xuất ra tiền tiêu thụ theo sformatter
    public int TieuThuThang(Date date, String sformatter){
        int result = 0;

        //format lấy ra tháng để so sánh
        SimpleDateFormat formatter = new SimpleDateFormat(sformatter);
        String PresentDate = formatter.format(date);

        //truy vấn tất cả trong bản
        String sql = "SELECT * FROM Spending ";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
            //lấy date từ bảng ra convert và so sánh với PresentDate
            String pDate = cursor.getString(3);
            long lDate = Long.parseLong(pDate);
            Date idate = new java.util.Date(lDate*1000L);
            String striDate = formatter.format(idate);

            if (PresentDate.equals(striDate)){
                int pMoney = cursor.getInt(2);
                result = result + pMoney;
            }
            cursor.moveToNext();
        }
        this.close();
        return result;
    }

    //xóa 1 Spending
    public int Delete(int zAuto){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("Spending", "dAuto" + "=?", new String[]{String.valueOf(zAuto)});
    }

    //sửa 1 Spending
    public int Update(Spending spending){
        //cần quyền đọc database và viết lại nên ta dùng đến getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //không thể truyền thẳng 1 đối tuoq taikhoan vào database được mà phải thông qua ContentValues để put(key, value) vào, key là tên trường(cột), value giá trị thay đổi.
        ContentValues contentValues = new ContentValues();
        contentValues.put("dExpenses", spending.getmExpenses());
        contentValues.put("dMoney", spending.getmMoney());
        return sqLiteDatabase.update("Spending", contentValues, "dAuto" + "=?", new String[]{String.valueOf(spending.getmAuto())});
    }

    //List spending theo Present
    public List<Spending> PresentSpending(Date date, String sformatter){
        List<Spending> result = new ArrayList<>();

        //format lấy ra tháng để so sánh
        SimpleDateFormat formatter = new SimpleDateFormat(sformatter);
        String PresentDate = formatter.format(date);

        //truy vấn tất cả trong bản
        String sql = "SELECT * FROM Spending ";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
            //lấy date từ bảng ra convert và so sánh với PresentDate
            String pDate = cursor.getString(3);
            long lDate = Long.parseLong(pDate);
            Date idate = new java.util.Date(lDate*1000L);
            String striDate = formatter.format(idate);

            if (PresentDate.equals(striDate)){
                int pMoney = cursor.getInt(2);
                int pAuto = cursor.getInt(0);
                String pExpenses = cursor.getString(1);

                Spending spending = new Spending(pExpenses, pMoney, idate, pAuto);
                result.add(spending);
            }
            cursor.moveToNext();
        }
        this.close();
        return result;
    }

    //xuất ra tiền tiêu thụ tháng
    public int Moth(int mMoth, Date mDate){
        int result = 0;

        //format lấy ra tháng để so sánh
        SimpleDateFormat formatMoth = new SimpleDateFormat("MM");
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        int Date =  Integer.parseInt(formatYear.format(mDate));

        //truy vấn tất cả trong bản
        String sql = "SELECT * FROM Spending ";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
            //lấy date từ bảng ra convert và so sánh với PresentDate
            String pDate = cursor.getString(3);
            long lDate = Long.parseLong(pDate);
            Date idate = new java.util.Date(lDate*1000L);

            int iMoth = Integer.parseInt(formatMoth.format(idate));
            int iYear = Integer.parseInt(formatYear.format(idate));

            if (iMoth == mMoth && iYear == Date){
                int pMoney = cursor.getInt(2);
                result = result + pMoney;
            }
            cursor.moveToNext();
        }
        this.close();
        return result;
    }

    //xuất ra tiền tiêu thụ Ngày
    //với thứ 2 là 1, thứ 3 là 2...
    public int Day(int mDay, Date mDate){
        int result = 0;

        //format lấy ra tháng để so sánh
        SimpleDateFormat formatWeekYear = new SimpleDateFormat("w/yyyy");
        SimpleDateFormat  formatDay = new SimpleDateFormat("u");

        //String formatWeekYear
        String sformatDate = formatWeekYear.format(mDate);

        //truy vấn tất cả trong bản
        String sql = "SELECT * FROM Spending ";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
            //lấy date từ bảng ra convert và so sánh với PresentDate
            String pDate = cursor.getString(3);
            long lDate = Long.parseLong(pDate);
            Date idate = new java.util.Date(lDate*1000L);

            String sformatiDate = formatWeekYear.format(idate);
            int iformatiDate  = Integer.parseInt(formatDay.format(idate));

            if (mDay == iformatiDate && sformatDate.equals(sformatiDate)){
                int pMoney = cursor.getInt(2);
                result = result + pMoney;
            }
            cursor.moveToNext();
        }
        this.close();
        return result;
    }

    //xuất ra tiền tiêu thụ Tuần
    public int Week(int mWeek, Date mDate){
        int result = 0;

        //format lấy ra tuan để so sánh
        SimpleDateFormat formatMothYear = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat  formatWeek = new SimpleDateFormat("W");

        //String formatWeekYear
        String sformatDate = formatMothYear.format(mDate);

        //truy vấn tất cả trong bản
        String sql = "SELECT * FROM Spending ";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
            //lấy date từ bảng ra convert và so sánh với PresentDate
            String pDate = cursor.getString(3);
            long lDate = Long.parseLong(pDate);
            Date idate = new java.util.Date(lDate*1000L);

            String sformatiDate = formatMothYear.format(idate);
            int iformatiDate  = Integer.parseInt(formatWeek.format(idate));

            if (mWeek == iformatiDate && sformatDate.equals(sformatiDate)){
                int pMoney = cursor.getInt(2);
                result = result + pMoney;
            }
            cursor.moveToNext();
        }
        this.close();
        return result;
    }
}
