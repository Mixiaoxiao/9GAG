package com.mixiaoxiao.android.youdaofanyi;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import net.tsz.afinal.annotation.sqlite.Id;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class YoudaoFanyiItem {
	
	private String translation;
	private String basic;
	private String basic_phonetic;
	private String basic_explains;
	@Id
	private String query;
	private int errorCode;
	private String web;
	@net.tsz.afinal.annotation.sqlite.Transient
	private ArrayList<WebItem> webItems;
	
	public YoudaoFanyiItem() {
		super();
		webItems = new ArrayList<WebItem>();
		// TODO Auto-generated constructor stub
	}

	public YoudaoFanyiItem(String jsonItemString){
		super();
		webItems = new ArrayList<WebItem>();
		if(TextUtils.isEmpty(jsonItemString)) return;
		try {
			JSONObject item = new JSONObject(jsonItemString);
//			String translation_array = item.getJSONArray("translation").toString();
//			if(translation_array.length()>4){
//				this.translation = translation_array.substring(2,translation_array.length()-2);
//			}
			this.translation = parseJsonArray2String(item.getJSONArray("translation"));
			if(item.has("basic")){
				JSONObject basic = item.getJSONObject("basic");
				this.basic = basic.toString();
				this.basic_explains = parseJsonArray2String(basic.getJSONArray("explains"));//basic_explains_array.substring(1, basic_explains_array.length()-1).replaceAll(",", "\n");
				this.basic_phonetic = basic.getString("phonetic");
			}
			this.query = item.getString("query");
			
			this.errorCode = item.getInt("errorCode");
			setWeb(item.getJSONArray("web").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 将返回的翻译结果中的josnArray转化为易读的文本
	 * 如"explains": [
            "n. 女孩；姑娘，未婚女子；女职员，女演员；（男人的）女朋友",
            "n. （捷）吉尔"
        ]
	 * @param jsonArray
	 * @return
	 */
	public static String parseJsonArray2String(JSONArray jsonArray){
		String arrayString = jsonArray.toString();
		StringBuilder builder = new StringBuilder();
		if(arrayString!=null && arrayString.length()>4){
			arrayString = arrayString.substring(1, arrayString.length()-1);
			String[] arrays =arrayString.split(",");
			for(int i=0;i<arrays.length;i++){
				String str = arrays[i];
				builder.append(str.replaceAll("\"", ""));
				if(i!=arrays.length-1){
					builder.append("\n");
				}
			}
		}
		return builder.toString();
	}
	
	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getBasic() {
		return basic;
	}

	public void setBasic(String basic) {
		this.basic = basic;
	}

	public String getBasic_phonetic() {
		return basic_phonetic;
	}

	public void setBasic_phonetic(String basic_phonetic) {
		this.basic_phonetic = basic_phonetic;
	}

	public String getBasic_explains() {
		return basic_explains;
	}

	public void setBasic_explains(String basic_explains) {
		this.basic_explains = basic_explains;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
		try {
			JSONArray webArray = new JSONArray(web);
			for(int i=0;i<webArray.length();i++){
				this.webItems.add(new WebItem(webArray.getJSONObject(i).toString()));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean hasBasic(){
		return !TextUtils.isEmpty(basic);
	}
	

	public ArrayList<WebItem> getWebItems() {
		return webItems;
	}

	public void setWebItems(ArrayList<WebItem> webItems) {
		this.webItems = webItems;
	}
	
	public String getTranslationContent(boolean withWebTranslation){
		StringBuilder builder = new StringBuilder();
		String query_tmp = new String(query);
		try {
			query_tmp = java.net.URLDecoder.decode(query_tmp, "utf-8") ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		builder.append(query_tmp);
		builder.append("\n");
		if(!TextUtils.isEmpty(translation)){
			builder.append(translation);
			builder.append("\n");
		}
		if(hasBasic()){
			if(!TextUtils.isEmpty(basic_phonetic)){
				builder.append("\n");
				builder.append(basic_phonetic);
				builder.append("\n");
			}
			if(!TextUtils.isEmpty(basic_explains)){
				builder.append(basic_explains);
				builder.append("\n");
			}
		}
		if(withWebTranslation){
			if(webItems.size()>0){
				
				builder.append("\nWeb translation:");
				for(WebItem webItem : webItems){
					builder.append("\n");
					builder.append(webItem.getContent());
				}
			}
			
		}
		return builder.toString();
		
	}



	public static class WebItem{
		private String key;
		private String value;
		public WebItem(String jsonItemString){
			if(TextUtils.isEmpty(jsonItemString)) return;
			try {
				JSONObject item = new JSONObject(jsonItemString);
				this.key = item.getString("key");
				this.value =parseJsonArray2String(item.getJSONArray("value"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public String getKey() {
			return key;
		}
		public String getValue() {
			return value;
		}
		public String getContent(){
			return key +"\n" +value;
		}
		
	}

}
