package com.mixiaoxiao.translate;

import com.mixiaoxiao.android.util.MxxDialogUtil;
import com.mixiaoxiao.android.util.MxxTextUtil;
import com.mixiaoxiao.android.util.MxxToastUtil;
import com.mixiaoxiao.ninegag.bean.TranslationItem;
import com.mixiaoxiao.ninegag.manager.TranslateDbManager;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

public class MxxTranslateManager {
	
	public interface TranslateListener{
		public void onError();
		/**
		 * 翻译成功
		 * @param content 原文
		 * @param result 译文
		 */
		public void onSuccess(String content,String result);
	}
	
	private TranslateDbManager dbManager;
	private Context context;
	
	
	public TranslateDbManager getDbManager() {
		return dbManager;
	}

	public MxxTranslateManager(Context context) {
		super();
		this.context = context;
		dbManager =new TranslateDbManager(context);
	}

	public void showTranslationResultInDialog(String content ){
		if(content==null) return;
		TranslationItem translationItem = dbManager.findTranslationItem(content);
		if(translationItem!=null){
			showTranslationResultDialog(translationItem.getContent(), translationItem.getResult());
			return ;
		}
		Dialog dialog = MxxDialogUtil.creatPorgressDialog(context, null, MxxTextUtil.getTypefaceSpannableString(context, "Translating...", MxxTextUtil.Roboto_Light, false), false, true,null);
		MxxTranslateManager.translate(content, TranslateUtil.CHINA, dialog, new MxxTranslateManager.TranslateListener() {
			
			@Override
			public void onSuccess(String content ,String result) {
				// TODO Auto-generated method stub
				//MxxToastUtil.showToast(context, result);
				dbManager.saveTranslationItem(new TranslationItem(content, result));
				showTranslationResultDialog(content, result);
			}
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				MxxToastUtil.showToast(context, "Translate error.");
			}
		});
	}
	
	public void translateNoDialog(String content ,final MxxTranslateManager.TranslateListener translateListener ){
		if(content==null) return;
		TranslationItem translationItem = dbManager.findTranslationItem(content);
		if(translationItem!=null){
			translateListener.onSuccess(content, translationItem.getResult());
			return ;
		}
//		MxxToastUtil.showToast(context, "translating...");
		MxxTranslateManager.translate(content, TranslateUtil.CHINA, null, new MxxTranslateManager.TranslateListener() {
			
			@Override
			public void onSuccess(String content ,String result) {
				// TODO Auto-generated method stub
				//MxxToastUtil.showToast(context, result);
				dbManager.saveTranslationItem(new TranslationItem(content, result));
				translateListener.onSuccess(content, result);
			}
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				MxxToastUtil.showToast(context, "Translate error.");
			}
		});
	}
	
	public void showTranslationResultDialog(String content ,String result){
		String message = content + "\n\n" + result;
		Dialog dialog = MxxDialogUtil.creatConfirmDialog(context, "Translation result",message, 
				"OK", null, true, true, new MxxDialogUtil.MxxDialogListener() {
					@Override
					public void onRightBtnClick() {
					}
					
					@Override
					public void onLeftBtnClick() {
					}
					@Override
					public void onCancel() {
					}
					@Override
					public void onListItemClick(int position, String string) {
					}

					@Override
					public void onListItemLongClick(int position, String string) {
						
					}
				});
		dialog.show();
	}
	
	public static void translate(String content,String target_lang, Dialog dialog,
				TranslateListener translateListener){
		if (Build.VERSION.SDK_INT >= 11) {
			new TranslateTask(target_lang, dialog, translateListener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, content);
        } else {
        	new TranslateTask(target_lang, dialog, translateListener).execute(content);
        }
	}
	
	public static class TranslateTask extends AsyncTask<String, Void, String>{
		private String target_lang, content;
		private Dialog dialog;
		private TranslateListener translateListener;
		
		public TranslateTask(String target_lang, Dialog dialog,
				TranslateListener translateListener) {
			super();
			this.target_lang = target_lang;
			this.dialog = dialog;
			this.translateListener = translateListener;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(dialog !=null){
				dialog.show();
			}
			
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			content = params[0];
			try {
				return TranslateUtil.translate(params[0], target_lang);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(dialog !=null){
				dialog.dismiss();
			}
			
			if(result==null|| result.length()==0){
				if(this.translateListener!=null){
					translateListener.onError();
				}
			}else{
				translateListener.onSuccess(content, result);
			}
		}
		
	}

		

}
