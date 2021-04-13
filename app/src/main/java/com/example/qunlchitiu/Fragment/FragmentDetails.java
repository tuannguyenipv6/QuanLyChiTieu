package com.example.qunlchitiu.Fragment;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.qunlchitiu.Adapter.AdapterSpending;
import com.example.qunlchitiu.Object.Spending;
import com.example.qunlchitiu.R;
import com.example.qunlchitiu.SQLite.DatabaseSpending;

import java.util.List;

public class FragmentDetails extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    AdapterSpending adapter;
    RecyclerView mRecyclerView;
    List<Spending> mSpendings;
    DatabaseSpending spendingData;
    View view;
    SwipeRefreshLayout mSwipeRefreshLayout;
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


        return view;
    }
    private void Anhxa(){
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        spendingData = new DatabaseSpending(getContext());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mSwipeRefreshLayout);
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
