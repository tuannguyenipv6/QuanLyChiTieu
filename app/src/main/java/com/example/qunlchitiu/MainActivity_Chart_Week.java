package com.example.qunlchitiu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlchitiu.SQLite.DatabaseSpending;

import java.util.Date;

public class MainActivity_Chart_Week extends AppCompatActivity {
    TextView Tuan1, Tuan2, Tuan3, Tuan4, Tuan5, VNDTuan1, VNDTuan2, VNDTuan3, VNDTuan4, VNDTuan5;

    DatabaseSpending database;
    RelativeLayout mRelativeLayout;
    ScaleGestureDetector scaleGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__chart__week);
        Anhxa();

        Date date = new Date();
        int T1 = database.Week(1, date);
        int T2 = database.Week(2, date);
        int T3 = database.Week(3, date);
        int T4 = database.Week(4, date);
        int T5 = database.Week(5, date);


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
        Tuan1 = findViewById(R.id.Week1);
        Tuan2 = findViewById(R.id.Week2);
        Tuan3 = findViewById(R.id.Week3);
        Tuan4 = findViewById(R.id.Week4);
        Tuan5 = findViewById(R.id.Week5);
        VNDTuan1 = findViewById(R.id.VNDW1);
        VNDTuan2 = findViewById(R.id.VNDW2);
        VNDTuan3 = findViewById(R.id.VNDW3);
        VNDTuan4 = findViewById(R.id.VNDW4);
        VNDTuan5 = findViewById(R.id.VNDW5);
        database = new DatabaseSpending(this);
        mRelativeLayout = findViewById(R.id.RV_Char_Week);
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