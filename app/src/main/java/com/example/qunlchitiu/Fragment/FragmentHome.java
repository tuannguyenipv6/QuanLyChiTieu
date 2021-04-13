package com.example.qunlchitiu.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qunlchitiu.ImageConverter;
import com.example.qunlchitiu.Object.Spending;
import com.example.qunlchitiu.R;
import com.example.qunlchitiu.SQLite.DatabaseSpending;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class FragmentHome extends Fragment {
    View view;
    EditText edtConten, edtMoney;
    TextView txtOK, txtStatistic;
    DatabaseSpending spendingData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Anhxa();
        setImg();
        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sConten = edtConten.getText().toString().trim();
                String sMoney = edtMoney.getText().toString().trim();
                if (!sConten.isEmpty() && !sMoney.isEmpty()){
                    Date date = new Date();
                    int iMoney = Integer.parseInt(sMoney);
                    Spending spending = new Spending(sConten, iMoney, date);
                    spendingData.newAdd(spending);
                    Toast.makeText(getContext(), "Đã lưu!", Toast.LENGTH_SHORT).show();
                    edtConten.setText("");
                    edtMoney.setText("");
                }
            }
        });
        return view;
    }
    private void Anhxa(){
        edtConten = view.findViewById(R.id.edt_Conten);
        edtMoney = view.findViewById(R.id.edt_Money);
        txtOK = view.findViewById(R.id.ok);
        txtStatistic = view.findViewById(R.id.txStatistic);
        spendingData = new DatabaseSpending(getContext());
    }
    private void setImg(){
        //set bo tròn cho image
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.img_tietkiem);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
        ImageView circularImageView = (ImageView) view.findViewById(R.id.imageView);
        circularImageView.setImageBitmap(circularBitmap);
    }
}
