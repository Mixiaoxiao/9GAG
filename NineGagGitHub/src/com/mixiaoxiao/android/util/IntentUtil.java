package com.mixiaoxiao.android.util;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

public class IntentUtil {

	public static void sendText(Context context, String shareString,
			String chooserDialogTitleString) {
		Intent i = new Intent(Intent.ACTION_SEND);
		// Uri uri = Uri.parse("share app");
		i.putExtra(Intent.EXTRA_TEXT, shareString);// +
													// URLEncoder.encode(getString(R.string.app_name)));
													// //如果是图片 则是 extra_stream
		i.setType("text/plain");// 如果是图片 则是"image/jpeg"
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(i, chooserDialogTitleString));

	}

	public static void sendImage(Context context, String imagePath,String chooserDialogTitleString) {
		Intent intent = new Intent(Intent.ACTION_SEND);
//		if (imagePath == null || imagePath.equals("")) {
//			intent.setType("text/plain"); // 纯文本
//		} else {
			File f = new File(imagePath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/png");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(Intent.createChooser(intent, chooserDialogTitleString));
			}
//		}
//		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
//		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		

	}
	public static void shareBitmapWithText(final Activity context ,final Bitmap bitmap , String text , final String chooserDialogTitleString){
//		chooserDialogTitleString = "share";
		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		intent.setType("image/png");
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if(bitmap!=null){
//			bitmap.compress(CompressFormat.PNG, 100, bos);
//			intent.putExtra(Intent.EXTRA_STREAM, bos.toByteArray());
//			final ProgressDialog dialog = ProgressDialog.show(context, null, MxxTextUtil.getTypefaceSpannableString(context, "Just wait a moment...", true));
			final Dialog dialog =  MxxDialogUtil.creatPorgressDialog(context, null, MxxTextUtil.getTypefaceSpannableString(context, "Just wait a moment...", MxxTextUtil.Roboto_Light, false), false, true,null);
			dialog.show();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					final String imagePath = MxxFileUtil.getCachePath(context) +  "/9gag_image_share.png";
					final boolean saved = BitmapUtil.saveBitmapFile(bitmap, imagePath);
					context.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							dialog.dismiss();
							if(saved){
								File f = new File(imagePath);
								if (f != null && f.exists() && f.isFile()) {
									Uri u = Uri.fromFile(f);
									intent.putExtra(Intent.EXTRA_STREAM, u);
								}
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								try {
									context.startActivity(Intent.createChooser(intent, MxxTextUtil.getTypefaceSpannableString(context, chooserDialogTitleString, MxxTextUtil.Roboto_Light, false)));
								} catch (ActivityNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
					});
					
				}
			}).start();
			
		}
		
	}
	public static void shareBitmap(final Activity context ,final Bitmap bitmap, final String chooserDialogTitleString){
//		chooserDialogTitleString = "share";
		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/png");
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if(bitmap!=null){
//			bitmap.compress(CompressFormat.PNG, 100, bos);
//			intent.putExtra(Intent.EXTRA_STREAM, bos.toByteArray());
//			final ProgressDialog dialog = ProgressDialog.show(context, null, MxxTextUtil.getTypefaceSpannableString(context, "Just wait a moment...",  MxxTextUtil.Roboto_Light, false));
			final Dialog dialog =  MxxDialogUtil.creatPorgressDialog(context, null, MxxTextUtil.getTypefaceSpannableString(context, "Just wait a moment...", MxxTextUtil.Roboto_Light, false), false, true,null);
			dialog.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					final String imagePath = MxxFileUtil.getCachePath(context) +  "/9gag_image_share.png";
					final boolean saved = BitmapUtil.saveBitmapFile(bitmap, imagePath);
					context.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							dialog.dismiss();
							if(saved){
								File f = new File(imagePath);
								if (f != null && f.exists() && f.isFile()) {
									Uri u = Uri.fromFile(f);
									intent.putExtra(Intent.EXTRA_STREAM, u);
								}
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								try {
									context.startActivity(Intent.createChooser(intent, MxxTextUtil.getTypefaceSpannableString(context, chooserDialogTitleString,true)));
								} catch (ActivityNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
					});
					
				}
			}).start();
			
		}
		
	}
	public static void shareText(final Activity context  , String text , final String chooserDialogTitleString){
//		chooserDialogTitleString = "share";
		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		intent.setType("text/plain");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			context.startActivity(Intent.createChooser(intent, MxxTextUtil.getTypefaceSpannableString(context, chooserDialogTitleString,true)));
		} catch (ActivityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
