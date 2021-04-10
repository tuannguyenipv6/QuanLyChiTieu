package com.example.qunlchitiu.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qunlchitiu.Adapter.AdapterSpending;
import com.example.qunlchitiu.Object.Spending;
import com.example.qunlchitiu.R;
import com.example.qunlchitiu.SQLite.DatabaseSpending;

import java.util.List;

public class FragmentDetails extends Fragment {
    AdapterSpending adapter;
    RecyclerView mRecyclerView;
    List<Spending> mSpendings;
    DatabaseSpending spendingData;
    View view;
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
        return view;
    }
    private void Anhxa(){
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        spendingData = new DatabaseSpending(getContext());
    }
}
