package com.mixiaoxiao.ninegag.manager;

import android.content.Context;

import com.mixiaoxiao.ninegag.bean.TranslationItem;

import net.tsz.afinal.FinalDb;

public class TranslateDbManager {
	private FinalDb finalDb;
	
	public TranslateDbManager(Context context){
		finalDb = FinalDb.create(context, "TranslateDbManager_db", false);
	}
	
	public TranslationItem findTranslationItem(String content){
		return finalDb.findById(content, TranslationItem.class);
	}
	public void saveTranslationItem(TranslationItem translationItem){
		if(translationItem ==null) return;
		finalDb.save(translationItem);
	}
}
