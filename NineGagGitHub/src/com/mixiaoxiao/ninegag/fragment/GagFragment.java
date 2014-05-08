package com.mixiaoxiao.ninegag.fragment;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mixiaoxiao.android.util.MxxToastUtil;
import com.mixiaoxiao.android.util.MxxUiUtil;
import com.mixiaoxiao.android.util.SystemBarTintManager;
import com.mixiaoxiao.android.view.ListViewScrollObserver;
import com.mixiaoxiao.android.view.ListViewScrollObserver.OnListViewScrollListener;
import com.mixiaoxiao.android.view.MxxRefreshableListView;
import com.mixiaoxiao.ninegag.MainActivity;
import com.mixiaoxiao.ninegag.R;
import com.mixiaoxiao.ninegag.bean.FeedItem;
import com.mixiaoxiao.ninegag.manager.FeedsManager;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class GagFragment extends Fragment {
	
	private MxxRefreshableListView mListView;
	private Context mContext;
	private FeedsManager mFeedsManager;
	private FinalBitmap mFinalBitmap;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mListView = (MxxRefreshableListView) inflater.inflate(R.layout.mxx_base_refreshable_listview, null);
		initInsetTop(mListView);
		return mListView;
	}
	
	
	
	private void initInsetTop(View rootView){
		SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());  
		SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();  
		rootView.setPadding(0, config.getPixelInsetTop(true) + MxxUiUtil.dip2px(getActivity(), 48), config.getPixelInsetRight(), config.getPixelInsetBottom());
		rootView.requestLayout();
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		mFeedsManager = getFeedsManager();
		if(mFeedsManager==null) return;
		mFinalBitmap = ((MainActivity)getActivity()).getFinalBitmap();
//		QuickAdapter<FeedItem> quickAdapter = new QuickAdapter<FeedItem>(mContext, R.layout.listitem_feed, mFeedsManager.getFeedItems()) {
//			
//			@Override
//			protected void convert(BaseAdapterHelper helper, FeedItem feedItem) {
//				// TODO Auto-generated method stub
//				
//			}
//		};
		
		final CardsAnimationAdapter adapter = new CardsAnimationAdapter( new FeedsAdapter());
//		CardsAnimationAdapter adapter = new CardsAnimationAdapter( quickAdapter);
		adapter.setAbsListView(mListView);
		mListView.setAdapter(adapter); 
		
		mListView.setOnTopRefreshListener(new MxxRefreshableListView.OnTopRefreshListener() {
			@Override
			public void onStart() {}
			@Override
			public void onEnd() {}
			
			@Override
			public void onDoinBackground() {
				mFeedsManager.updateFirstPage();
			}
		});
		mListView.setOnBottomRefreshListener(new MxxRefreshableListView.OnBottomRefreshListener() {
			@Override
			public void onStart() {}
			@Override
			public void onEnd() {
				adapter.setShouldAnimateFromPosition(mListView.getLastVisiblePosition());
			}
			
			@Override
			public void onDoinBackground() {
				mFeedsManager.updateNextPage();
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View convertView, int position,
					long arg3) {
				// TODO Auto-generated method stub
//				position = position - 1;//´æÔÚÒ»¸öheader
//				final FeedItem  item = mFeedsManager.getFeedItems().get(position);
//				Log.e("TEMP", item.getImages_large());
//				ImageViewerActivity.startWithData(getActivity(), item);
				final ImageView imageView = (ImageView) convertView.findViewById(R.id.feed_item_image);
				if(imageView.getDrawable()==null || imageView.getDrawable().getIntrinsicWidth() ==0){
					MxxToastUtil.showToast(getActivity(), "Please wait...");
					return;
				}
				((MainActivity)getActivity()).showImageFragment(imageView,true,mFeedsManager.getFeedItems().get(position - 1));
			}
		});
		mListView.post(new Runnable() {
			@Override
			public void run() {
				if(mFeedsManager.loadDbData()){
					mListView.notifyDataSetChanged();
				}else{
					mListView.startUpdateImmediate();
				}
			}
		});
		initScrollListener();
	}
	
	private void initScrollListener(){
		final int max_tranY = MxxUiUtil.dip2px(mContext, 48);
		final View tabview = ((MainActivity)getActivity()).getTabStripLayout();
		ListViewScrollObserver observer = new ListViewScrollObserver(mListView);
        observer.setOnScrollUpAndDownListener(new OnListViewScrollListener() {
			
			@Override
			public void onScrollUpDownChanged(int delta, int scrollPosition,
					boolean exact) {
				// TODO Auto-generated method stub
				if(exact){
					float tran_y = tabview.getTranslationY() + delta;
					if(tran_y >= 0){
						tabview.setTranslationY(0);
					}else if(tran_y < -max_tranY){
						tabview.setTranslationY(-max_tranY);
					}else{
						tabview.setTranslationY(tran_y);
					}
				}
				
			}
			
			@Override
			public void onScrollIdle() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void refresh(){
		mListView.startUpdateImmediate();
	}
	
	protected FeedsManager getFeedsManager(){
		return null;
	}
	
	private class FeedsAdapter extends BaseAdapter{
		private Typeface typeface;
		public FeedsAdapter(){
			super();
			typeface = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mFeedsManager.getFeedItems().size();
		}

		@Override
		public FeedItem getItem(int position) {
			// TODO Auto-generated method stub
			return mFeedsManager.getFeedItems().get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_feed, null);
//				ImageView loadingImageView = (ImageView) convertView.findViewById(R.id.feed_item_image_loading);
//				AnimationDrawable animationDrawable = (AnimationDrawable) loadingImageView.getDrawable();
//				animationDrawable.start();
				((TextView)convertView.findViewById(R.id.feed_item_text_loading)).setTypeface(typeface);
				holder.title = (TextView) convertView.findViewById(R.id.feed_item_title);
				holder.title.setTypeface(typeface);
				holder.info = (TextView) convertView.findViewById(R.id.feed_item_text_info);
				holder.info.setTypeface(typeface);
				holder.image = (ImageView) convertView.findViewById(R.id.feed_item_image);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			final FeedItem item = mFeedsManager.getFeedItems().get(position);
			if(item!=null){
				if(item.getCaption()!=null){
					holder.title.setText(item.getCaption());
				}else{
					holder.title.setText("unknown caption");
				}
				mFinalBitmap.display(holder.image, item.getImages_normal());
				holder.info.setText(item.getVotes());
			}
			
			
			return convertView;
		}
		class ViewHolder{
			public TextView title;
			public TextView info;
			public ImageView image;
		}
		
	}
	class CardsAnimationAdapter extends AnimationAdapter {
	    private float mTranslationY = 400;

	    private float mRotationX = 15;

	    private long mDuration = 400;

	    public CardsAnimationAdapter(BaseAdapter baseAdapter) {
	        super(baseAdapter);
	    }

	    @Override
	    protected long getAnimationDelayMillis() {
	        return 30;
	    }

	    @Override
	    protected long getAnimationDurationMillis() {
	        return mDuration;
	    }

	    @Override
	    public Animator[] getAnimators(ViewGroup parent, View view) {
	        return new Animator[]{
	                ObjectAnimator.ofFloat(view, "translationY", mTranslationY, 0),
	                ObjectAnimator.ofFloat(view, "rotationX", mRotationX, 0)
	        };
	    }
	}
	
	


}
