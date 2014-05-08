package com.mixiaoxiao.ninegag.manager;

import java.util.ArrayList;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.mixiaoxiao.android.util.MxxFileUtil;
import com.mixiaoxiao.android.util.MxxHttpUtil;
import com.mixiaoxiao.ninegag.bean.FeedItem;

public class FeedsManager {
	
	
	public static final String TYPE_FRESH = "fresh";
	public static final String TYPE_HOT = "hot";
	public static final String TYPE_TRENDING = "trending";
	private ArrayList<FeedItem> feedItems;
	private String next = "";
	private String base_url;
	private FinalDb finalDb;
	
	public FeedsManager(String type, Context context){
		this.feedItems = new ArrayList<FeedItem>();
		next = "";
		base_url = "http://infinigag-us.aws.af.cm/" + type + "/";
		finalDb = FinalDb.create(context, type + "_db", false);
	}
	
	
	public ArrayList<FeedItem> getFeedItems() {
		return feedItems;
	}
	/**
	 * 加载finalDb存储的数据
	 * @return 是否存在缓存数据
	 */
	public boolean loadDbData(){
		try {
			this.feedItems.addAll(finalDb.findAll(FeedItem.class));
			if (feedItems.size() > 0) {
				this.next = feedItems.get(feedItems.size() - 1).getNext();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	public void updateFirstPage(){
		this.next = "";
		updateListInBackground();
	}
	
	public void updateNextPage(){
		updateListInBackground();
	}


	/**
	 * 获取数据
	 * 必须在非UI线程
	 */
	private void updateListInBackground(){
		String json = MxxHttpUtil.get(getRequestUrl());
		
		if(TextUtils.isEmpty(json)) return;
		 ArrayList<FeedItem> feedItems_tmp = new ArrayList<FeedItem>();
		 String next_tmp = "";
//		Log.e("JSON", json);
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray feedList = jsonObject.getJSONArray("data");
			if(this.next.equals("")){
				finalDb.deleteByWhere(FeedItem.class, null);
			}
			next_tmp = jsonObject.getJSONObject("paging").getString("next");
			for(int i=0;i<feedList.length();i++){
				FeedItem item = new FeedItem(feedList.getJSONObject(i));
				item.setNext(next_tmp);
				feedItems_tmp.add(item);
				finalDb.save(item);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this.next.equals("")){
			feedItems.clear();
		}
		this.next = next_tmp;
		feedItems.addAll(feedItems_tmp);
	}
	/**
	 * 请求链接 如http://infinigag-us.aws.af.cm/fresh/aAYLQ7p
	 * @return
	 */
	private String getRequestUrl(){
		return base_url + next;
	}
			

}
