package com.example.qunlchitiu.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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
import com.example.qunlchitiu.Object.Spending;
import com.example.qunlchitiu.R;
import com.example.qunlchitiu.SQLite.DatabaseSpending;
import com.example.qunlchitiu.UtilAnimation;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sa90.materialarcmenu.ArcMenu;

import java.util.List;

public class FragmentDetails extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    AdapterSpending adapter;
    RecyclerView mRecyclerView;
    List<Spending> mSpendings;
    DatabaseSpending spendingData;
    View view;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ViewPager mViewPager;

    //AppBarLayout: bố cục thanh ứng dụng
    AppBarLayout mAppBarLayout;
    //CollapsingToolbarLayout: thu gọn bố cục thanh công cụ
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    //của tk androidx,Toolbar: thanh công cụ
    Toolbar mToolbar;
    //là nút add, FloatingActionButton: nút hành động nỗi
    FloatingActionButton mFloatingActionButton;
    ArcMenu mArcMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);
        Anhxa();

        mSpendings = spendingData.AllSpending();
        adapter = new AdapterSpending(mSpendings);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);

        //SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //this chín là phương thức ta đã implements ở trên
        //set màu cho SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.teal_200));
        initToolbarAnimations();
        onClickBtnAdd();

        mRecyclerView.setOnTouchListener(new UtilAnimation(getContext(), mArcMenu));

        return view;
    }
    private void Anhxa(){
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        spendingData = new DatabaseSpending(getContext());
        mViewPager = getActivity().findViewById(R.id.mViewPager);
        mArcMenu = (ArcMenu) view.findViewById(R.id.acrMenu);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mSwipeRefreshLayout);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.mAppBarLayout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.mCollapsingToolbarLayout);
        mToolbar = (Toolbar) view.findViewById(R.id.mToolbar);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.mFloatingActionButton);
    }

    private void initToolbarAnimations(){
        mCollapsingToolbarLayout.setTitle("Tháng: " + "     Tuần: ");
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

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200){
                    mCollapsingToolbarLayout.setTitleEnabled(true);
                }else mCollapsingToolbarLayout.setTitleEnabled(false);
            }
        });
    }

    private void onClickBtnAdd(){
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override//hàm bắt sự kiện Button add
            public void onClick(View v) {
                //bắt sự kiện nút add khi chưa add vào menu của resoucre
                mViewPager.setCurrentItem(0);
            }
        });
    }

    @Override
    public void onRefresh() {
        setAnimation();
        //vd cho nó load trong 2s với Handler
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //lệnh tắt SwipeRefreshLayout
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);//tắt sau 1.5s
    }

    private void setAnimation(){//nhận vào Resoucre animations
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_up_to_dow);
        mRecyclerView.setLayoutAnimation(layoutAnimationController);
        adapter.setSpendings(spendingData.AllSpending());
        mRecyclerView.setAdapter(adapter);
    }
}
