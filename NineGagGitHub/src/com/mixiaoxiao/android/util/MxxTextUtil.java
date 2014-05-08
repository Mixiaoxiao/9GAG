package com.mixiaoxiao.android.util;

import com.mixiaoxiao.android.view.TypefaceSpan;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.TextView;

public class MxxTextUtil {

	public static final String LockScreen_Clock = "LockScreen_Clock.ttf";
	public static final String Roboto_Regular = "Roboto-Regular.ttf";
	public static final String Roboto_Light = "Roboto-Light.ttf";
	public static SpannableString getTypefaceSpannableString(Context context,
			String string,String fontName) {
		SpannableString spannableString = new SpannableString(string);
		spannableString.setSpan(
				new TypefaceSpan(fontName, Typeface.createFromAsset(
						context.getAssets(), fontName), false), 0,
				spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}
	public static SpannableString getTypefaceSpannableString(Context context,
			String string,String fontName, boolean isBold) {
		SpannableString spannableString = new SpannableString(string);
		spannableString.setSpan(
				new TypefaceSpan(fontName, Typeface.createFromAsset(
						context.getAssets(), fontName), isBold), 0,
				spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString getTypefaceSpannableString(Context context,
			String string, boolean isBold) {
		SpannableString spannableString = new SpannableString(string);
		String font = Roboto_Light;//"LockScreen_Clock.ttf";
		spannableString.setSpan(
				new TypefaceSpan(font, Typeface.createFromAsset(
						context.getAssets(), font), isBold), 0,
				spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static void setTextViewTypeface(TextView textView) {
		String font = "LockScreen_Clock.ttf";
		textView.setTypeface(Typeface.createFromAsset(textView.getContext()
				.getAssets(), font));
	}

	public static void copyTextViewString(TextView textView) {
		String content = textView.getText().toString();
		Context context = textView.getContext();
		copyString(context, content);
	}
	
	public static void copyString(Context context ,String content) {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText("label", content);
			clipboard.setPrimaryClip(clip);
		} else {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(content);
		}
		MxxToastUtil.showToast(context, "The text has been copied to clipboard.");
	}

	public static String pasteString(Context context) {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			return clipboard.getText().toString().trim();
		} else {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			return clipboard.getText().toString();
		}
	}

}
