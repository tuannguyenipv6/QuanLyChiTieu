package com.example.qunlchitiu.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FragmentHome extends Fragment {
    View view;
    EditText edtConten, edtMoney;
    TextView txtOK, txtStatistic;
    DatabaseSpending spendingData;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Anhxa();
        setImg();

        //bắt sự kiện icon của edtConten
        edtConten.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edtConten.getRight() - edtConten.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        promptSpeechInput();
                        return true;
                    }
                }
                return false;
            }
        });


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


    //sử dụng voice

    /**
     * Gọi dialog của google speech thông qua Intent
     * Một số action quan trọng trong Intent như
     * ACTION_RECOGNIZE_SPEECH, LANGUAGE_MODEL_FREE_FORM, EXTRA_PROMPT
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Trả lại dữ liệu sau khi nhập giọng nói vào
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == getActivity().RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edtConten.setText(result.get(0));
                }
                break;
            }
        }
    }
}
