package com.example.qunlchitiu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.qunlchitiu.Adapter.AdapterViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView mBottomNavigationView;
    ViewPager mViewPager;
    AdapterViewPager adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        setUpViewPager();
        //bắt sự kiện onClick chuyển Fragment
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnHome:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.mnDetails:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.mnSearch:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return true;//return về true
            }
        });

    }
    private void Anhxa(){
        mBottomNavigationView = findViewById(R.id.mBottomNavigationView);
        mViewPager = findViewById(R.id.mViewPager);
    }
    private void setUpViewPager(){
        //khởi tạo
        adapter = new AdapterViewPager(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        //set Adapter
        mViewPager.setAdapter(adapter);

        //bắt sự kiện trượt chuyển Fragment
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //bắt sự kiện trượt trong hàm onPageSelected
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.mnHome).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.mnDetails).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.mnSearch).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}