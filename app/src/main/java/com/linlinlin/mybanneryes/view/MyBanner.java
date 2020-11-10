package com.linlinlin.mybanneryes.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.linlinlin.mybanneryes.R;
import com.linlinlin.mybanneryes.SizeUtil;

public class MyBanner extends LinearLayout {
    private ViewPager vp;
    private TextView tvTitle;
    private LinearLayout llPoints;
    private InnerPageAdapter mInnerPageAdapter = null;
    private TitleBindListener mTitleBindListener = null;

    public Handler mHandler = new Handler();

    public MyBanner(Context context) {
        this(context, null);
    }

    public MyBanner(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBanner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_my_banner, this);
        initView();
        initEvent();
    }

    private void initView() {
        vp = this.findViewById(R.id.vp);
        tvTitle = this.findViewById(R.id.tv_title);
        llPoints = this.findViewById(R.id.ll_points);
    }

    public void setData(InnerPageAdapter pagerAdapter, TitleBindListener titleBindListener) {
        this.mTitleBindListener = titleBindListener;
        this.mInnerPageAdapter = pagerAdapter;
        vp.setAdapter(pagerAdapter);
        vp.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mInnerPageAdapter.getDataSize());
        updateIndicator();
        mInnerPageAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                updateIndicator();
            }
        });
        mHandler.postDelayed(mRunnable, 3000);
    }

    public Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int position = vp.getCurrentItem() + 1;
            vp.setCurrentItem(position);
            postDelayed(this, 4000);
        }
    };

    private void initEvent() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = position % mInnerPageAdapter.getDataSize();
                if (mTitleBindListener != null) {
                    tvTitle.setText(mTitleBindListener.getTitle(realPosition));
                }
                updateIndicator();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void updateIndicator() {
        if (mInnerPageAdapter != null) {
            llPoints.removeAllViews();
            int realSize = mInnerPageAdapter.getDataSize();
            for (int i = 0; i < realSize; i++) {
                View view = new View(getContext());
                view.setBackgroundResource(R.drawable.selector_indicator);
                int px = SizeUtil.dip2Px(getContext(), 8);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(px, px);
                view.setEnabled(i == vp.getCurrentItem() % realSize);
                if (i != 0) {
                    params.leftMargin = px;
                }
                view.setLayoutParams(params);

                llPoints.addView(view);
            }
        }
    }


    //获取title
    public interface TitleBindListener {
        String getTitle(int position);
    }

    //提供方法给外部设置适配器进来，这个适配器怎么我们有规定，所以使用了一个抽象类来描述。
    //而TitleBindListener用来获取标题，我们要标题的时候调用即可。
    public abstract static class InnerPageAdapter extends PagerAdapter {

        //实际总数
        public abstract int getDataSize();

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int realPosition = position % getDataSize();
            View view = getItemView(container, realPosition);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        //至于要什么view，我不管，由外面给我。
        protected abstract View getItemView(ViewGroup container, int itemPosition);
    }


}
