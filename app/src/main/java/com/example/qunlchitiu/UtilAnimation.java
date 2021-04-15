package com.example.qunlchitiu;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class UtilAnimation implements View.OnTouchListener{
    private GestureDetector mGestureDetector;//dò cử chỉ
    //khởi tạo GestureDetector trong contrctor với 2 tham số Context và View
    public UtilAnimation(Context context, View view) {
        mGestureDetector = new GestureDetector(context, new SimpleGestureDetector(view));
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
    public class SimpleGestureDetector extends GestureDetector.SimpleOnGestureListener{
        private View mViewAnimation;
        private boolean isFinishAnimation = true;//để check xem khi nào animation này nó finish
        //Contructor
        public SimpleGestureDetector(View mViewAnimation) {
            this.mViewAnimation = mViewAnimation;
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (distanceY > 0 ){
                AnView();
            }else HienView();
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
        private void HienView() {
            if (mViewAnimation == null || mViewAnimation.getVisibility() == View.VISIBLE){
                //nếu view đang hiển thị thì return luôn
                return;
            }
            Animation animHien = AnimationUtils.loadAnimation(mViewAnimation.getContext(), R.anim.anim_hien);
            animHien.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mViewAnimation.setVisibility(View.VISIBLE);
                    isFinishAnimation = false;
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    isFinishAnimation = true;
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            //check isFinishAnimation
            if (isFinishAnimation){//finish
                mViewAnimation.startAnimation(animHien);
            }
        }
        private void AnView() {
            if (mViewAnimation == null || mViewAnimation.getVisibility() == View.GONE){
                //nếu view đang hiển thị thì return luôn
                return;
            }
            Animation animAn = AnimationUtils.loadAnimation(mViewAnimation.getContext(), R.anim.anim_an);
            animAn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mViewAnimation.setVisibility(View.VISIBLE);
                    isFinishAnimation = false;
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    mViewAnimation.setVisibility(View.GONE);
                    isFinishAnimation = true;
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            //check isFinishAnimation
            if (isFinishAnimation){//finish
                mViewAnimation.startAnimation(animAn);
            }
        }
    }
}
