package com.mixiaoxiao.android.util;

import java.util.ArrayList;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.mixiaoxiao.ninegag.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MxxDialogUtil {
	
	public interface MxxDialogListener{
		public void onLeftBtnClick();
		public void onRightBtnClick();
		public void onListItemClick(int position, String string);
		public void onListItemLongClick(int position, String string);
		public void onCancel();
		
	}
	public static Dialog creatListViewDialog(final Context context,
			final CharSequence title, final ArrayList<String> items ,CharSequence cancleButtonText,
			final MxxDialogListener mxxDialogListener) {
		final Dialog dlg = new Dialog(context, R.style.mxx_theme_dialog);
		LayoutInflater inflater =LayoutInflater.from(context);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.mxx_dialog_listview_layout, null);
		final ListView list = (ListView) layout
				.findViewById(R.id.mxx_dialog_listview);
		QuickAdapter<String> adapter = new QuickAdapter<String>(context, R.layout.mxx_dialog_list_item, items) {
			@Override
			protected void convert(BaseAdapterHelper helper, String string) {
				// TODO Auto-generated method stub
				helper.setText(R.id.mxx_dialog_list_item_textview, string);
			}
		};
		list.setAdapter(adapter);
		// list.setDividerHeight(0);
		TextView titlte = (TextView) layout.findViewById(R.id.mxx_dialog_title);
		titlte.setText(title);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dlg.dismiss();
				if(mxxDialogListener!=null){
					mxxDialogListener.onListItemClick(position, items.get(position));
				}
			}
		});
		if(mxxDialogListener!=null){
			list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					mxxDialogListener.onListItemLongClick(position, items.get(position));
					return true;
				}
			});
		}
		dlg.setCanceledOnTouchOutside(true);
		if (mxxDialogListener != null) {
			dlg.setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					mxxDialogListener.onCancel();
				}
			});
		}
		if(cancleButtonText!=null){
			((TextView)layout.findViewById(R.id.mxx_dialog_cancle_btn)).setText(cancleButtonText);
			layout.findViewById(R.id.mxx_dialog_cancle_btn).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dlg.dismiss();
				}
			});
		}else{
			layout.findViewById(R.id.mxx_dialog_cancle_btn).setVisibility(View.GONE);
			layout.findViewById(R.id.mxx_dialog_divider).setVisibility(View.GONE);
		}
		dlg.setContentView(layout);
		return dlg;
	}
	public static Dialog creatPorgressDialog(Context context,CharSequence title,
			CharSequence message, boolean canceledOnTouchOutside,
			boolean cancelable, OnCancelListener cancelListener) {
		Dialog dialog = null;
		TextView mMessageText = null;
		TextView mTitleView = null;
		dialog = new Dialog(context, R.style.mxx_theme_dialog);
		View view = LayoutInflater.from(context).inflate(
				R.layout.mxx_dialog_progress_layout, null);
		mMessageText = (TextView) view
				.findViewById(R.id.mxx_dialog_message);
		mTitleView= (TextView) view
				.findViewById(R.id.mxx_dialog_title);
		if(title==null){
			mTitleView.setVisibility(View.GONE);
		}else{
			mTitleView.setText(title);
		}
		dialog.setContentView(view);

		mMessageText.setText(message);
		dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		// dialog.show();
		return dialog;
	}
	public static Dialog creatConfirmDialog(Context context,
			CharSequence title, CharSequence message, CharSequence btn_right_text,
			CharSequence btn_left_text, boolean canceledOnTouchOutside,
			boolean cancelable,
			final MxxDialogListener mxxDialogListener) {
		final Dialog dialog = new Dialog(context, R.style.mxx_theme_dialog);
		TextView mMessageView = null;
		TextView mTitleView = null;
		View view = LayoutInflater.from(context).inflate(
				R.layout.mxx_dialog_confirm_layout, null);
		mTitleView = (TextView) view
				.findViewById(R.id.mxx_dialog_confirm_title);
		mTitleView.setText(title);
		mMessageView = (TextView) view
				.findViewById(R.id.mxx_dialog_confirm_message);
		if (message != null) {
			mMessageView.setText(message);
		} else {
			mMessageView.setVisibility(View.GONE);
		}
		TextView btn1 = (TextView) view
				.findViewById(R.id.mxx_dialog_confirm_btn1);
		btn1.setText(btn_right_text);
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				if (mxxDialogListener != null) {
					mxxDialogListener.onRightBtnClick();
				}
			}
		});
		if (btn_left_text != null) {
			TextView btn2 = (TextView) view
					.findViewById(R.id.mxx_dialog_confirm_btn2);
			btn2.setText(btn_left_text);
			btn2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					if (mxxDialogListener != null) {
						mxxDialogListener.onLeftBtnClick();
					}
				}
			});
		} else {
			view.findViewById(R.id.mxx_dialog_confirm_btn2).setVisibility(
					View.GONE);
			view.findViewById(R.id.mxx_dialog_btns_divider).setVisibility(
					View.GONE);
		}
		dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		dialog.setContentView(view);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				if (mxxDialogListener != null) {
					mxxDialogListener.onCancel();
				}
			}
		});
		// dialog.show();
		return dialog;
	}
}
