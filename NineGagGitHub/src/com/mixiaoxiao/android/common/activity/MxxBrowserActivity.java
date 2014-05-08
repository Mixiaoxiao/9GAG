package com.mixiaoxiao.android.common.activity;


import com.mixiaoxiao.android.util.MxxSystemBarTintUtil;
import com.mixiaoxiao.android.util.MxxToastUtil;
import com.mixiaoxiao.android.util.SystemBarTintManager;
import com.mixiaoxiao.android.view.TypefaceSpan;
import com.mixiaoxiao.ninegag.R;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

/**
 * 
 * @author Mixiaoxiao
 * 
 */
public class MxxBrowserActivity extends FragmentActivity {

//	ActionBar mActionBar;
	WebView webView;
	ProgressBar mProgressBar;
	private ImageButton mBtnBack, mBtnForward, mBtnMenu, mBtnClose,
	mBtnRefresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setmProgressBarIndeterminateVisibility(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
//		MxxSystemBarTintUtil.setSystemBarTintColor(this);
		 setContentView(R.layout.mxx_common_activity_browser);
		 initPadding();
//		mActionBar = getActionBar();
//		mActionBar.setTitle("");
//		mActionBar.setDisplayHomeAsUpEnabled(true);
		setActionBarTitle("");
		View.OnClickListener toolbar_listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.mxx_common_activity_browser_toolbar_btn_back:
					if (webView.canGoBack()) {
						webView.goBack();
					}
					break;
				case R.id.mxx_common_activity_browser_toolbar_btn_forward:
					if (webView.canGoForward()) {
						webView.goForward();
					}
					break;
				case R.id.mxx_common_activity_browser_toolbar_btn_refresh:
					webView.reload();
					break;
				
				default:
					break;
				}
			}
		};

		webView =  (WebView) this.findViewById(R.id.mxx_common_activity_browser_webview);
		mProgressBar = (ProgressBar) findViewById(R.id.mxx_common_activity_browser_progressbar);
		mBtnBack = (ImageButton) this
				.findViewById(R.id.mxx_common_activity_browser_toolbar_btn_back);
		mBtnForward = (ImageButton) this
				.findViewById(R.id.mxx_common_activity_browser_toolbar_btn_forward);
		mBtnRefresh = (ImageButton) this
				.findViewById(R.id.mxx_common_activity_browser_toolbar_btn_refresh);
		
		mBtnBack.setEnabled(false);
		 mBtnForward.setEnabled(false);
//		mBtnMenu = (ImageButton) this
//				.findViewById(R.id.mxx_common_activity_browser_toolbar_btn_menu);
//		mBtnClose = (ImageButton) this
//				.findViewById(R.id.mxx_common_activity_browser_toolbar_btn_close);
		mBtnBack.setOnClickListener(toolbar_listener);
		mBtnForward.setOnClickListener(toolbar_listener);
//		mBtnMenu.setOnClickListener(toolbar_listener);
//		mBtnClose.setOnClickListener(toolbar_listener);
		mBtnRefresh.setOnClickListener(toolbar_listener);
		
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportMultipleWindows(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);
		// webView.getSettings().setUseWideViewPort(true);
		// webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NORMAL);
		webView.setClipToPadding(false);
		// webView.setFitsSystemWindows(true);

		webView.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				try {
//					mActionBar.setTitle("下载");
					setActionBarTitle("download");
					Uri uri = Uri.parse(url);
					Intent it = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(Intent.createChooser(it, "下载"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MxxToastUtil.showToast(MxxBrowserActivity.this, "没有应用可执行此操作");
				}
			}
		});
		// webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// mUrlTitle.setText("鍔犺浇涓?..");
//				mActionBar.setTitle("loading...");
				setActionBarTitle("loading...");
				 mProgressBar.setVisibility(View.VISIBLE);
//				 mPage_title.setVisibility(View.INVISIBLE);
			}

			public void onPageFinished(WebView view, String url) {
				 mProgressBar.setVisibility(View.INVISIBLE);
				 mBtnBack.setEnabled(view.canGoBack());
				 mBtnForward.setEnabled(view.canGoForward());
//				 mActionBar.setTitle(url);
				// mUrlTitle.setText(url);
			}
		});

		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				// mmProgressBar.setProgress(newProgress);
				// 璁剧疆杩涘害鐨勯暱搴︼紝0 <= progress <= 10000.
//				setProgress(newProgress * 100);
				mProgressBar.setProgress(newProgress);
			}

			public void onReceivedIcon(WebView view, Bitmap icon) {
				// mFavor_icon.setImageBitmap(icon);
//				mActionBar.setLogo(new BitmapDrawable(getResources(),icon));
			}

			public void onReceivedTitle(WebView view, String title) {
				// mUrlTitle.setText(title);
//				mActionBar.setTitle(title);
				setActionBarTitle(title);
			}
		});

		Intent intent = getIntent();
		if (intent.hasExtra(extra_string_name)) {
			String urlString = intent.getStringExtra(extra_string_name);
			// webView.loadDataWithBaseURL(null, htmlString, "text/html",
			// "utf-8", null);
			webView.loadUrl(urlString);
		} else {
			// UiUtils.showToast(this, "锟斤拷莶锟斤拷锟饺笔?);
			finish();
		}

	}
	
	private void initPadding(){
		SystemBarTintManager tintManager = new SystemBarTintManager(this);  
		SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
		View rootView = findViewById(R.id.mxx_common_activity_browser_webview_rootlayout);
		rootView.setPadding(0, config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintDrawable(new ColorDrawable(getResources().getColor(R.color.mxx_item_theme_color_alpha)));
		tintManager.setNavigationBarTintEnabled(true);
		tintManager.setNavigationBarTintDrawable(new ColorDrawable(0xff393d43));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return true;
	}

	public static final String extra_string_name = "url_string";

	public static void startWithExtra(Context context, String urlString) {
		Intent intent = new Intent(context, MxxBrowserActivity.class);
		intent.putExtra(extra_string_name, urlString);
		context.startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		if (webView.canGoBack()) {
			webView.goBack();
		} else
			super.onBackPressed();
	}
	
	private void setActionBarTitle(String title){
		SpannableString spannableString = new SpannableString(title);
		String font = "LockScreen_Clock.ttf";
		spannableString.setSpan(new TypefaceSpan(font, Typeface.createFromAsset(getAssets(), font)), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(spannableString);
	}
	
	@Override
	protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			webView.onResume();
		}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		webView.onPause();
	}
	@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			webView.stopLoading();
			webView.destroy();
		}
}
