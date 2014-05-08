package com.mixiaoxiao.ninegag;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.mixiaoxiao.android.util.MxxSystemBarTintUtil;
import com.mixiaoxiao.android.util.MxxToastUtil;
import com.mixiaoxiao.android.util.SystemBarTintManager;
import com.mixiaoxiao.android.view.MxxPagerSlidingTabStrip;
import com.mixiaoxiao.android.view.MxxScaleImageView;
import com.mixiaoxiao.android.view.TypefaceSpan;
import com.mixiaoxiao.ninegag.bean.FeedItem;
import com.mixiaoxiao.ninegag.fragment.GagFragment;
import com.mixiaoxiao.ninegag.fragment.GagFragmentFresh;
import com.mixiaoxiao.ninegag.fragment.GagFragmentHot;
import com.mixiaoxiao.ninegag.fragment.GagFragmentTrending;
import com.mixiaoxiao.ninegag.fragment.ImageFragment;

public class MainActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private MxxPagerSlidingTabStrip mTabStrip;
	private View tabLayout;
//	private MxxScaleImageView scaleImageView;
	private ImageFragment mImageFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MxxSystemBarTintUtil.setSystemBarTintColor(this);
		SpannableString spannableString = new SpannableString("9GAG");
		String font = "LockScreen_Clock.ttf";
		
		spannableString.setSpan(new TypefaceSpan(font, Typeface.createFromAsset(getAssets(), font)), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(spannableString);
		getActionBar().setDisplayShowHomeEnabled(false);
		setContentView(R.layout.activity_main);
		
		mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
//		scaleImageView = (MxxScaleImageView) findViewById(R.id.main_mxxScaleImageView1);
		mViewPager.setOffscreenPageLimit(3);
		mTabStrip = (MxxPagerSlidingTabStrip) findViewById(R.id.main_tab);
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new GagFragmentHot() );
		fragments.add(new GagFragmentTrending() );
		fragments.add(new GagFragmentFresh() );
		mViewPager.setAdapter(new GagAdapter2(getSupportFragmentManager(),fragments,new String[]{"hot", "trending" ,"fresh"}));
		mTabStrip.setViewPager(mViewPager);
		initTint();
		mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				if(((ViewGroup)tabLayout).getChildAt(0).getTranslationY()!=0){
					((ViewGroup)tabLayout).getChildAt(0).setTranslationY(0);
				}
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		mImageFragment = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.main_image_fragment);
		mTabStrip.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getSupportFragmentManager().beginTransaction().hide(mImageFragment).commit();
			}
		});
		
	}
	
	
	private FinalBitmap mFinalBitmap;
	
	public FinalBitmap getFinalBitmap(){
		if(mFinalBitmap==null){
			mFinalBitmap = FinalBitmap.create(this);
//			mFinalBitmap.configLoadingImage(R.drawable.favorites_nopicture_icon);
			
			mFinalBitmap.configBitmapMaxWidth(720);
			mFinalBitmap.configBitmapMaxHeight(720);
//			mFinalBitmap.configLoadfailImage(R.drawable.favorites_nopicture_icon);
			mFinalBitmap.configLoadingImage(null);
			mFinalBitmap.configLoadfailImage(null);
			mFinalBitmap.configDiskCacheSize(1024 * 1024 * 50);
		}
		return mFinalBitmap;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mFinalBitmap!=null){
			mFinalBitmap.onPause();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(mFinalBitmap!=null){
			mFinalBitmap.onResume();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mFinalBitmap!=null){
			mFinalBitmap.onDestroy();
		}
	}
	
//	public void startScaleAnimation(ImageView smallImageView){
//		scaleImageView.startScaleAnimation(smallImageView);
		
//	}
	
	public void showImageFragment(ImageView smallImageView , boolean show, FeedItem item){
		//showActionbarWithTabs(!show);
		if(show){
			getSupportFragmentManager().beginTransaction().show(mImageFragment).commit();
			mImageFragment.startScaleAnimation(smallImageView, item);
		}else{
			getSupportFragmentManager().beginTransaction().hide(mImageFragment).commit();
		}
		
	}
	public void showActionbarWithTabs(boolean show){
		if(show){
//			getActionBar().show();
			tabLayout.setVisibility(View.VISIBLE);
		}else{
//			getActionBar().hide();
			tabLayout.setVisibility(View.GONE);
		}
	}
	
	public View getTabStripLayout(){
		return ((ViewGroup)tabLayout).getChildAt(0);
	}
	
	private void initTint(){
		SystemBarTintManager tintManager = new SystemBarTintManager(this);  
		SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
//		tintManager.setStatusBarTintEnabled(true);
//		tintManager.setStatusBarTintDrawable(new ColorDrawable(getResources().getColor(R.color.mxx_item_theme_color_alpha)));
		//mPagerSlidingTabStrip.setPadding(0, config.getPixelInsetTop(true), config.getPixelInsetRight(), 0);
//		View rootView = findViewById(R.id.main_layout_root);
		tabLayout = findViewById(R.id.main_tab_layout);
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) tabLayout.getLayoutParams();
		layoutParams.topMargin = config.getPixelInsetTop(true);
//		layoutParams.height = MxxUiUtil.dip2px(this, 48) + config.getPixelInsetTop(true);
		tabLayout.requestLayout();
	}
	private class GagAdapter2 extends android.support.v4.app.FragmentPagerAdapter{

		String[] titles;// = new String[]{"hot", "trending" ,"fresh"};
		ArrayList<Fragment> fragments;
		public GagAdapter2(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		public GagAdapter2(FragmentManager fm,
				ArrayList<Fragment> fragments, String[] titles) {
			super(fm);
			this.fragments = fragments;
			this.titles = titles;
		}

		@Override
		public GagFragment getItem(int position) {
			// TODO Auto-generated method stub
			return (GagFragment) fragments.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titles[position];
//			return super.getPageTitle(position);
		}
		
	}
	
	private class GagAdapter extends FragmentStatePagerAdapter{

		String[] titles;// = new String[]{"hot", "trending" ,"fresh"};
		ArrayList<Fragment> fragments;
		public GagAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		public GagAdapter(FragmentManager fm,
				ArrayList<Fragment> fragments, String[] titles) {
			super(fm);
			this.fragments = fragments;
			this.titles = titles;
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titles[position];
//			return super.getPageTitle(position);
		}
		
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if(mImageFragment!=null){
			menu.findItem(R.id.action_refresh).setVisible(!mImageFragment.canBack());
			menu.findItem(R.id.action_more).setVisible(!mImageFragment.canBack());
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.action_refresh){
			GagFragment gagFragment = ((GagAdapter2)mViewPager.getAdapter()).getItem(mViewPager.getCurrentItem());
			gagFragment.refresh();
			return true;
		}else if(item.getItemId() == R.id.action_about){
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	long last_back_time = 0;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(mImageFragment.canBack()){
			mImageFragment.goBack();
			
		}else{
			long cur_time = System.currentTimeMillis();
			
			if((cur_time - last_back_time) < 1000 ){
				super.onBackPressed();
			}else{
				last_back_time = cur_time;
				//Toast.makeText(MainActivity.this,getResources().getString(R.string.str_back_twice_exit), Toast.LENGTH_SHORT).show();
				MxxToastUtil.showToast(MainActivity.this, getResources().getString(R.string.str_back_twice_exit));
			}
			
		}
	}

}
