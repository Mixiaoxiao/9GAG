package com.mixiaoxiao.ninegag.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import org.json.JSONException;
import org.json.JSONObject;


public class FeedItem {
	
	@Id
	private String id;
	private String caption;
	private String images_small;
	private String images_normal;
	private String images_large;
	private String link;
	private String next;
	private String votes;
	
	public FeedItem(){
		super();
	}
	
	public FeedItem(JSONObject jsonObject){
		try {
			this.id = jsonObject.getString("id");
			this.link = jsonObject.getString("link");
			this.caption = jsonObject.getString("caption");
			this.votes  = jsonObject.getJSONObject("votes").getString("count");
			JSONObject imageJsonObject = jsonObject.getJSONObject("images");
			this.images_small = imageJsonObject.getString("small");
			this.images_normal = imageJsonObject.getString("normal");
			this.images_large = imageJsonObject.getString("large");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public String getId() {
		return id;
	}

	public String getCaption() {
		return caption;
	}

	public String getImages_small() {
		return images_small;
	}

	public String getImages_normal() {
		return images_normal;
	}

	public String getImages_large() {
		return images_large;
	}

	public String getLink() {
		return link;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getVotes() {
		return votes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setImages_small(String images_small) {
		this.images_small = images_small;
	}

	public void setImages_normal(String images_normal) {
		this.images_normal = images_normal;
	}

	public void setImages_large(String images_large) {
		this.images_large = images_large;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setVotes(String votes) {
		this.votes = votes;
	}
	
	
}
