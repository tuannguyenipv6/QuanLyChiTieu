package com.example.qunlchitiu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlchitiu.SQLite.DatabaseSpending;

import java.util.Date;

public class MainActivity_Chart_Day extends AppCompatActivity {
    TextView T2, T3, T4, T5, T6, T7, TCN;
    TextView VNDT2, VNDT3, VNDT4, VNDT5, VNDT6, VNDT7, VNDCN;
    DatabaseSpending database;
    RelativeLayout mRelativeLayout;
    ScaleGestureDetector scaleGestureDetector;
    int max = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__chart__day);
        Anhxa();
        Date date = new Date();
        int Thu2 = database.Day(1, date);
        int Thu3 = database.Day(2, date);
        int Thu4 = database.Day(3, date);
        int Thu5 = database.Day(4, date);
        int Thu6 = database.Day(5, date);
        int Thu7 = database.Day(6, date);
        int CN = database.Day(7, date);

//        Toast.makeText(MainActivity_Chart_Day.this, "???: " + Thu5, Toast.LENGTH_LONG).show();

        //lấy ra Ngày cao nhất
        int[] mMax = {Thu2, Thu3, Thu4, Thu5, Thu6, Thu7, CN};
        for (int num: mMax) {
            if(max < num)
                max = num;
        }

        //set Dữ liệu từng Ngày:
        setDataChar(Thu2, T2, VNDT2);
        setDataChar(Thu3, T3, VNDT3);
        setDataChar(Thu4, T4, VNDT4);
        setDataChar(Thu5, T5, VNDT5);
        setDataChar(Thu6, T6, VNDT6);
        setDataChar(Thu7, T7, VNDT7);
        setDataChar(CN, TCN, VNDCN);


        //phóng to thu nhỏ
        scaleGestureDetector = new ScaleGestureDetector(this, new MyGesture());
        mRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }
    private void Anhxa(){
        database = new DatabaseSpending(this);
        mRelativeLayout = findViewById(R.id.RV_Char_Day);
        T2 = findViewById(R.id.Day2);
        T3 = findViewById(R.id.Day3);
        T4 = findViewById(R.id.Day4);
        T5 = findViewById(R.id.Day5);
        T6 = findViewById(R.id.Day6);
        T7 = findViewById(R.id.Day7);
        TCN = findViewById(R.id.DayCN);

        VNDT2 = findViewById(R.id.VNDT2);
        VNDT3 = findViewById(R.id.VNDT3);
        VNDT4 = findViewById(R.id.VNDT4);
        VNDT5 = findViewById(R.id.VNDT5);
        VNDT6 = findViewById(R.id.VNDT6);
        VNDT7 = findViewById(R.id.VNDT7);
        VNDCN = findViewById(R.id.VNDCN);
    }

    private void setDataChar(int gtrHT, TextView Thu, TextView VND){
        double Height = (gtrHT * 1000) / max;
        Thu.setHeight((int) Height);
        VND.setText(gtrHT + " VNĐ");
    }

    //các funsion dùng để phóng to thu nhỏ
    class MyGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        //khai báo 3 biến lll: biến lấy giá trị phóng ban đầu, biến giữ giá trị phóng, và biến cuôi cùng kết thúc phóng
        float scale = 1.0F, onScaleStart = 0, onScaleEnd = 0;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //gọi lại biến bắt đầu "scale" nhân cho tỉ lệ mà người dùng phóng "detector.getScaleFactor();"
            scale *= detector.getScaleFactor();
            mRelativeLayout.setScaleX(scale); //chiều rộn
            mRelativeLayout.setScaleY(scale); //chiều cao
//            lvTKB.setSca(scale);
            return super.onScale(detector);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            //lần lần phóng tiếp theo t cũng bắt nó tự độ phóng trk đó và gán vào onScaleStart
            onScaleStart = scale;
            return super.onScaleBegin(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            //tương tự khi phóng kết thúc thì gán lại vào onScaleEnd
            onScaleEnd = scale;
            super.onScaleEnd(detector);
        }
    }
}