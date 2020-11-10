package com.linlinlin.mybanneryes;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.linlinlin.mybanneryes.view.MyBanner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyBanner mMyBanner;
    private List<PagerItem> mDatas;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyBanner = findViewById(R.id.my_banner);
        initData();
        initEvent();


    }

    private void initEvent() {
        mMyBanner.setData(new MyBanner.InnerPageAdapter() {
            @Override
            public int getDataSize() {
                return mDatas.size();
            }

            @Override
            protected View getItemView(ViewGroup container, int itemPosition) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                //设置图片
                imageView.setImageResource(mDatas.get(itemPosition).getPicResID());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);
                return imageView;
            }
        }, position -> mDatas.get(position).getTitle());
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new PagerItem("第一个界面", R.drawable.a));
        mDatas.add(new PagerItem("第2个界面", R.drawable.b));
        mDatas.add(new PagerItem("第三个界面", R.drawable.c));
        mDatas.add(new PagerItem("第4个界面", R.drawable.d));
    }


}