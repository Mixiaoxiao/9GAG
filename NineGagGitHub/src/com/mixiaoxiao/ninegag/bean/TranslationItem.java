package com.mixiaoxiao.ninegag.bean;

import net.tsz.afinal.annotation.sqlite.Id;

public class TranslationItem {
	@Id
	private String content;
	private String result;
	public TranslationItem(String content, String result) {
		super();
		this.content = content;
		this.result = result;
	}
	public TranslationItem() {
		super();
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	

}
