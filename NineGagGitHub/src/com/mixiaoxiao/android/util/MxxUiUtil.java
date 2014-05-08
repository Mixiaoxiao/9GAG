package com.mixiaoxiao.android.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MxxUiUtil {
	
	/**
	 * dip-->px sualizerView.setLayoutParams(new ViewGroup.LayoutParams(
	 * ViewGroup.LayoutParams.FILL_PARENT, (int) (VISUALIZER_HEIGHT_DIP *
	 * getResources() .getDisplayMetrics().density)));
	 * 
	 * @param context
	 * @param dip
	 * @return
	 */
	public static int dip2px(Context context, float dip) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}
	
	/**
	 * 
	 * 关闭键盘事件
	 * 
	 * @author shimiso
	 * @update 2012-7-4 下午2:34:34
	 */
	public static void closeInput(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null && activity.getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(activity
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	public static void showInput(EditText editText){
		InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	}

}
