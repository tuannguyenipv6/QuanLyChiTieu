package com.example.qunlchitiu.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.qunlchitiu.Adapter.AdapterSpending;
import com.example.qunlchitiu.ItemSpenOnClickListener;
import com.example.qunlchitiu.MainActivity;
import com.example.qunlchitiu.MainActivity_Chart;
import com.example.qunlchitiu.MainActivity_Chart_Day;
import com.example.qunlchitiu.MainActivity_Chart_Week;
import com.example.qunlchitiu.Object.Spending;
import com.example.qunlchitiu.R;
import com.example.qunlchitiu.SQLite.DatabaseSpending;
import com.example.qunlchitiu.UtilAnimation;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sa90.materialarcmenu.ArcMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentDetails extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    AdapterSpending adapter;
    RecyclerView mRecyclerView;
    List<Spending> mSpendings;
    DatabaseSpending spendingData;
    View view;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ViewPager mViewPager;
    TextView TTthang, TTtuan;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    EditText edtConten;

    //AppBarLayout: b??? c???c thanh ???ng d???ng
    AppBarLayout mAppBarLayout;
    //CollapsingToolbarLayout: thu g???n b??? c???c thanh c??ng c???
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    //c???a tk androidx,Toolbar: thanh c??ng c???
    Toolbar mToolbar;
    //l?? n??t add, FloatingActionButton: n??t h??nh ?????ng n???i
    FloatingActionButton mFloatingActionButton;
    ArcMenu mArcMenu;
    int cThang, cTuan;

    FloatingActionButton iArcToday, iArcWeek, iArcMoth, iArcChNgay, iArcChTuan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);
        Anhxa();

        //set l???i t???ng th??ng, tu???n v?? RecyclerView
        Date date = new Date();
        setTongCT(date);
        mSpendings = spendingData.AllSpending();
        adapter = new AdapterSpending(mSpendings);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);

        //SwipeRefreshLayout: vu???t l??m m???i l???i
        mSwipeRefreshLayout.setOnRefreshListener(this); //this ch??n l?? ph????ng th???c ta ???? implements ??? tr??n
        //set m??u cho SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.teal_200));
        //sau khi vu???t set l???i data
        initToolbarAnimations();
        onClickBtnAdd();

        //tr?????t ???n hi???n ArcMenu
        mRecyclerView.setOnTouchListener(new UtilAnimation(getContext(), mArcMenu));

        //s??? ki???n on click item
        adapter.setListener(new ItemSpenOnClickListener() {

            @Override   //
            public void onClick(Spending spending) {
                DialogShow(spending);
            }

            @Override   //hi???n dialog x??a ho???c s???a
            public void onLongClick(Spending spending) {
                DialogXoaSua(spending);
            }

        });

        //b???t s??? ki???n c??c FloatingActionButton trong ArcMenu

        //s??? ki???n h??m nay
        iArcToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataDate("E, dd/MM/yyyy");
            }
        });

        //s??? ki???n tu???n n??y
        iArcWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataDate("W, MM/yyyy");
            }
        });

        //s??? ki???n th??ng n??y
        iArcMoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataDate("MM/yyyy");
            }
        });

        //S??? ki???n bi???u ????? Tu???n
        iArcChTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity_Chart_Week.class);
                startActivity(intent);
            }
        });

        //S??? ki???n bi???u ????? ng??y
        iArcChNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity_Chart_Day.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void Anhxa(){
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        TTthang = view.findViewById(R.id.TTthang);
        TTtuan = view.findViewById(R.id.TTtuan);
        spendingData = new DatabaseSpending(getContext());
        mViewPager = getActivity().findViewById(R.id.mViewPager);
        mArcMenu = (ArcMenu) view.findViewById(R.id.acrMenu);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mSwipeRefreshLayout);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.mAppBarLayout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.mCollapsingToolbarLayout);
        mToolbar = (Toolbar) view.findViewById(R.id.mToolbar);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.mFloatingActionButton);
        iArcToday = view.findViewById(R.id.iArcToday);
        iArcWeek = view.findViewById(R.id.iArcWeek);
        iArcMoth = view.findViewById(R.id.iArcMoth);
        iArcChTuan = view.findViewById(R.id.iArcChTuan);
        iArcChNgay = view.findViewById(R.id.iArcChNgay);
    }


    private void initToolbarAnimations(){
        mCollapsingToolbarLayout.setTitle("Th??ng: " + cThang + "VN??-Tu???n: " + cTuan + "VN??");
        mCollapsingToolbarLayout.setTitleEnabled(false);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_dong_tien);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                int myColor = palette.getVibrantColor(getResources().getColor(R.color.color_toolbar));
                mCollapsingToolbarLayout.setContentScrimColor(myColor);
                mCollapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.black_trans));
            }
        });

        //set ???n hi???n title CollapsingToolbarLayout
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200){
                    mCollapsingToolbarLayout.setTitleEnabled(true);
                }else mCollapsingToolbarLayout.setTitleEnabled(false);
            }
        });
    }

    //click btn home
    private void onClickBtnAdd(){
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override//h??m b???t s??? ki???n Button
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity_Chart.class);
                startActivity(intent);
            }
        });
    }

    //h??m tr?????t set l???i data
    @Override
    public void onRefresh() {
        setAnimation();
        setTongCT(new Date());
        //vd cho n?? load trong 2s v???i Handler
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //l???nh t???t SwipeRefreshLayout
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);//t???t sau 1s
    }

    //set data cho RV v?? animotion
    private void setAnimation(){
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_up_to_dow);
        mRecyclerView.setLayoutAnimation(layoutAnimationController);
        adapter.setSpendings(spendingData.AllSpending());
        mRecyclerView.setAdapter(adapter);
    }

    //set data theo ng??y
    private void setDataDate(String formatter){
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_up_to_dow);
        mRecyclerView.setLayoutAnimation(layoutAnimationController);
        Date date = new Date();
        adapter.setSpendings(spendingData.PresentSpending(date, formatter));
        mRecyclerView.setAdapter(adapter);
    }

    //set ti???n chi ti??u th??ng & tu???n
    private void setTongCT(Date date){
        cThang = spendingData.TieuThuThang(date, "MM/yyyy");
        cTuan = spendingData.TieuThuThang(date, "W/MM/yyyy");
        TTthang.setText("Th??ng n??y: " + cThang + " VN??");
        TTtuan.setText("Tu???n n??y: " + cTuan + " VN??");
    }

    //dialog x??a, s???a.
    private void DialogXoaSua(Spending spending){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Th??ng b??o!");
        dialog.setMessage("B???n mu???n th???c hi???n: ");
        dialog.setNegativeButton("X??a", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (spendingData.Delete(spending.getmAuto()) > 0){
                    Toast.makeText(getContext(), "???? x??a!", Toast.LENGTH_SHORT).show();
                    setAnimation();
                    setTongCT(new Date());
                }else Toast.makeText(getContext(), "Th???t b???i!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setPositiveButton("S???a", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogUpdate(spending);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //dialog s???a data trong SQLi
    private void DialogUpdate(Spending spending){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_sua_spending);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        //Bo tr??n v?? set v??? tr?? hi???n th??? dialog
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowLayoutParams = window.getAttributes();
        windowLayoutParams.gravity = Gravity.CENTER; //hi???n th??? ??? gi???a
        window.setAttributes(windowLayoutParams);

        edtConten = dialog.findViewById(R.id.dlgConten);
        EditText edtMoney = dialog.findViewById(R.id.dlgMoney);
        ImageView btnClear = dialog.findViewById(R.id.dlgClear);
        TextView btnOK = dialog.findViewById(R.id.dlgOk);

        edtConten.setText(spending.getmExpenses());
        edtMoney.setText(spending.getmMoney() + "");

        //b???t s??? ki???n icon c???a edtConten
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

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Conten = edtConten.getText().toString().trim();
                String Money = edtMoney.getText().toString().trim();
                if (!Conten.isEmpty() && !Money.isEmpty()){
                    int iMoney = Integer.parseInt(Money);
                    Spending spending1 = new Spending(Conten, iMoney, spending.getmAuto());
                    if (spendingData.Update(spending1) > 0){
                        Toast.makeText(getContext(), "???? thay ?????i", Toast.LENGTH_SHORT).show();
                        setAnimation();
                        setTongCT(new Date());
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.show();
    }

    //dialog show th??ng tin
    private void DialogShow(Spending spending){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_show_spending);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        //Bo tr??n v?? set v??? tr?? hi???n th??? dialog
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowLayoutParams = window.getAttributes();
        windowLayoutParams.gravity = Gravity.CENTER; //hi???n th??? ??? gi???a
        window.setAttributes(windowLayoutParams);

        TextView txConten = dialog.findViewById(R.id.ShConten);
        TextView txMoney = dialog.findViewById(R.id.ShMoney);
        TextView txDate = dialog.findViewById(R.id.ShDate);
        ImageView btnClear = dialog.findViewById(R.id.ShClear);

        SimpleDateFormat formatter = new SimpleDateFormat("E, dd/MM/yyyy-HH:mm");
        String strDate = formatter.format(spending.getmTime());

        txConten.setText(spending.getmExpenses());
        txMoney.setText(spending.getmMoney() + " VN??");
        txDate.setText(strDate);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    /**
     * G???i dialog c???a google speech th??ng qua Intent
     * M???t s??? action quan tr???ng trong Intent nh??
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
     * Tr??? l???i d??? li???u sau khi nh???p gi???ng n??i v??o
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
