package com.mixiaoxiao.ninegag;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mixiaoxiao.android.util.MxxSystemBarTintUtil;
import com.mixiaoxiao.android.util.MxxTextUtil;
import com.mixiaoxiao.android.util.MxxUiUtil;
import com.mixiaoxiao.android.util.SystemBarTintManager;
import com.mixiaoxiao.android.view.MxxNotifyingScrollView;
import com.mixiaoxiao.android.view.MxxPagerSlidingTabStrip;

public class AboutActivity extends FragmentActivity {
	
	private ViewPager mViewPager;
	private MxxPagerSlidingTabStrip mTabStrip;
	private View tabLayout;
	private MxxNotifyingScrollView view_gag, view_me;
	int last_t = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(MxxTextUtil.getTypefaceSpannableString(this, "About", false));
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		MxxSystemBarTintUtil.setSystemBarTintColor(this);
		setContentView(R.layout.activity_about);
		Typeface	typeface = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		
		mViewPager = (ViewPager) findViewById(R.id.about_viewpager);
//		scaleImageView = (MxxScaleImageView) findViewById(R.id.about_mxxScaleImageView1);
		mViewPager.setOffscreenPageLimit(3);
		mTabStrip = (MxxPagerSlidingTabStrip) findViewById(R.id.about_tab);
		ArrayList<View> views = new ArrayList<View>(2);
		 view_gag = (MxxNotifyingScrollView) LayoutInflater.from(this).inflate(R.layout.about_layout_9gag, null);
		 view_me = (MxxNotifyingScrollView) LayoutInflater.from(this).inflate(R.layout.about_layout_author, null);;
		 ((TextView)view_gag.findViewById(R.id.about_textview)).setTypeface(typeface);
		 ((TextView)view_me.findViewById(R.id.about_textview)).setTypeface(typeface);
		
		views.add(view_me);
		views.add(view_gag);
		mViewPager.setAdapter(new ViewpagerAdapter(views,new String[]{ "author","9gag"}));
		mTabStrip.setViewPager(mViewPager);
		initInsetTop();
		
		final int max_tranY = MxxUiUtil.dip2px(this, 48);
		
		MxxNotifyingScrollView.OnScrollChangedListener onScrollChangedListener = new MxxNotifyingScrollView.OnScrollChangedListener() {
			
			@Override
			public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
				// TODO Auto-generated method stub
				if(t<0){
					return;
				}
				int deltaY = last_t - t;
				last_t = t;
				float tran_y = mTabStrip.getTranslationY() + deltaY;
				if(tran_y >= 0){
					mTabStrip.setTranslationY(0);
				}else if(tran_y < -max_tranY){
					mTabStrip.setTranslationY(-max_tranY);
				}else{
					mTabStrip.setTranslationY(tran_y);
				}
//				Log.e("TEMP", String.format("l:%d t:%d oldl:%d oldt:%d dy:%d", l, t, oldl, oldt, deltaY));
			}
		};
		view_gag.setOnScrollChangedListener(onScrollChangedListener);
		view_me.setOnScrollChangedListener(onScrollChangedListener);
		mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				if(mTabStrip.getTranslationY()!=0){
					mTabStrip.setTranslationY(0);
				}
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
	private void initInsetTop(){
		SystemBarTintManager tintManager = new SystemBarTintManager(this);  
		SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
		tabLayout = findViewById(R.id.about_tab_layout);
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) tabLayout.getLayoutParams();
		layoutParams.topMargin = config.getPixelInsetTop(true);
//		layoutParams.height = MxxUiUtil.dip2px(this, 48) + config.getPixelInsetTop(true);
		tabLayout.requestLayout();
		final int top_margin = MxxUiUtil.dip2px(this, 48);
		view_gag.setPadding(0,  config.getPixelInsetTop(true) + top_margin, config.getPixelInsetRight(), config.getPixelInsetBottom());
		view_gag.requestLayout();
		view_me.setPadding(0,  config.getPixelInsetTop(true)+ top_margin, config.getPixelInsetRight(), config.getPixelInsetBottom());
		view_me.requestLayout();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public class ViewpagerAdapter extends PagerAdapter{

		private List<View> views;
		private String[] titles;
		
		public ViewpagerAdapter(List<View> views, String[] titles){
			this.views=views;
			this.titles = titles;
		}
		
		public ViewpagerAdapter(List<View> views){
			this.views=views;
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		@Override
		public int getCount() {
			return views.size();
		}
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager)container).removeView(views.get(position));
		}
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager)container).addView(views.get(position));
			return views.get(position);
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			if(titles==null){
				return super.getPageTitle(position);
			}else{
				return titles[position];
			}
			
		}
		
	}

}
