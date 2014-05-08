package com.mixiaoxiao.android.util;

import com.mixiaoxiao.ninegag.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class MxxToastUtil {
	
	public static void showToast(Context context ,CharSequence text){
		TextView textView = new TextView(context);
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundResource(R.drawable.mxx_toast_frame);
		textView.setTextColor(0xffffffff);
		textView.setTextSize(14);
		textView.setText(text);
//		int padding = MxxUiUtil.dip2px(context, 4);
//		textView.setPadding(padding, padding, padding, padding);
		String font = "Roboto-Light.ttf";
		textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), font));
		Toast toast = new Toast(context);
		toast.setView(textView);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
//		Toast.makeText(context, MxxTextUtil.getTypefaceSpannableString(context, text.toString(), false), Toast.LENGTH_SHORT).show();
	}

}
