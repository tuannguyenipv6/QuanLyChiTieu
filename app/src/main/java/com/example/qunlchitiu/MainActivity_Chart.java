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

public class MainActivity_Chart extends AppCompatActivity {
    DatabaseSpending database;
    RelativeLayout mRelativeLayout;
    ScaleGestureDetector scaleGestureDetector;

    TextView Th1, Th2, Th3, Th4, Th5, Th6, Th7, Th8, Th9, Th10, Th11, Th12;
    TextView VNDTh1, VNDTh2, VNDTh3, VNDTh4, VNDTh5, VNDTh6, VNDTh7, VNDTh8, VNDTh9, VNDTh10, VNDTh11, VNDTh12;

    int max = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__chart);
        Anhxa();

        Date date = new Date();
        int th1 = database.Moth(1, date);
        int th2 = database.Moth(2, date);
        int th3 = database.Moth(3, date);
        int th4 = database.Moth(4, date);
        int th5 = database.Moth(5, date);
        int th6 = database.Moth(6, date);
        int th7 = database.Moth(7, date);
        int th8 = database.Moth(8, date);
        int th9 = database.Moth(9, date);
        int th10 = database.Moth(10, date);
        int th11 = database.Moth(11, date);
        int th12 = database.Moth(12, date);

        //lấy ra tháng cao nhất
        int[] mMax = {th1, th2, th3, th4, th5, th6, th7, th8, th9, th10, th11, th12};
        for (int num: mMax) {
            if(max < num)
                max = num;
        }


        //set Dữ liệu từng tháng:

        setDataChar(th1, Th1, VNDTh1);
        setDataChar(th2, Th2, VNDTh2);
        setDataChar(th3, Th3, VNDTh3);
        setDataChar(th4, Th4, VNDTh4);
        setDataChar(th5, Th5, VNDTh5);
        setDataChar(th6, Th6, VNDTh6);
        setDataChar(th7, Th7, VNDTh7);
        setDataChar(th8, Th8, VNDTh8);
        setDataChar(th9, Th9, VNDTh9);
        setDataChar(th10, Th10, VNDTh10);
        setDataChar(th11, Th11, VNDTh11);
        setDataChar(th12, Th12, VNDTh12);




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
        mRelativeLayout = findViewById(R.id.mRelativeLayout);
        Th1 = findViewById(R.id.Moth1);
        Th2 = findViewById(R.id.Moth2);
        Th3 = findViewById(R.id.Moth3);
        Th4 = findViewById(R.id.Moth4);
        Th5 = findViewById(R.id.Moth5);
        Th6 = findViewById(R.id.Moth6);
        Th7 = findViewById(R.id.Moth7);
        Th8 = findViewById(R.id.Moth8);
        Th9 = findViewById(R.id.Moth9);
        Th10 = findViewById(R.id.Moth10);
        Th11 = findViewById(R.id.Moth11);
        Th12 = findViewById(R.id.Moth12);

        VNDTh1 = findViewById(R.id.VNDth1);
        VNDTh2 = findViewById(R.id.VNDth2);
        VNDTh3 = findViewById(R.id.VNDth3);
        VNDTh4 = findViewById(R.id.VNDth4);
        VNDTh5 = findViewById(R.id.VNDth5);
        VNDTh6 = findViewById(R.id.VNDth6);
        VNDTh7 = findViewById(R.id.VNDth7);
        VNDTh8 = findViewById(R.id.VNDth8);
        VNDTh9 = findViewById(R.id.VNDth9);
        VNDTh10 = findViewById(R.id.VNDth10);
        VNDTh11 = findViewById(R.id.VNDth11);
        VNDTh12 = findViewById(R.id.VNDth12);
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

    private void setDataChar(int gtrHT, TextView Th, TextView VND){
        double Height = (gtrHT * 1000) / max;
        Th.setHeight((int) Height);
        VND.setText(gtrHT + " VNĐ");
    }
}