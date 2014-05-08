package com.mixiaoxiao.android.blur;

import com.mixiaoxiao.android.util.MxxUiUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class MxxBlurView extends View{
	
	int radius = 30;
    private  Canvas mCanvas;
    private Bitmap mBitmap;
    private Rect mRectVisibleGlobal;
    private  Matrix mMatrixScale;
    private  Matrix mMatrixScaleInv;
    float BITMAP_SCALE_FACTOR = 0.2f;

	public MxxBlurView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public MxxBlurView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public MxxBlurView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void drawBlurOnce(){
		drawOffscreenBitmap(this);
		this.setBackgroundDrawable(new BitmapDrawable(FastBlur.doBlur(mBitmap,radius , true)));
	}
	
	private void init(){
		mMatrixScale = new Matrix();
        mMatrixScaleInv = new Matrix();
        mCanvas = new Canvas();
        mRectVisibleGlobal = new Rect();
        radius = MxxUiUtil.dip2px(getContext(), radius);
        radius = Math.round(radius * BITMAP_SCALE_FACTOR);
	}
	public Bitmap drawOffscreenBitmap(View mView) {
        // Grab global visible rect for later use
        mView.getGlobalVisibleRect(mRectVisibleGlobal);

        // Calculate scaled off-screen bitmap width and height
        int width = Math.round(mView.getWidth() * BITMAP_SCALE_FACTOR);
        int height = Math.round(mView.getHeight() * BITMAP_SCALE_FACTOR);

        // This is added due to RenderScript limitations I faced.
        // If bitmap width is not multiple of 4 - in RenderScript
        // index = y * width
        // does not calculate correct index for line start index.
        width = width & ~0x03;

        // Width and height must be > 0
        width = Math.max(width, 1);
        height = Math.max(height, 1);

        // Allocate new off-screen bitmap only when needed
        if (mBitmap == null || mBitmap.getWidth() != width || mBitmap.getHeight() != height) {
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            mAllocationBitmap = Allocation.createFromBitmap(mRS, mBitmap);
//            mAllocationRgb = Allocation.createSized(mRS, Element.U8_3(mRS), width * height);
//            mSizeStruct.width = width;
//            mSizeStruct.height = height;
//            // Due to adjusting width into multiple of 4 calculate scale matrix only here
            mMatrixScale.setScale((float)width / mView.getWidth(), (float)height / mView.getHeight());
            mMatrixScale.invert(mMatrixScaleInv);
        }

        // Translate values for off-screen drawing
        int dx = -(Math.min(0, mView.getLeft()) + mRectVisibleGlobal.left);
        int dy = -(Math.min(0, mView.getTop()) + mRectVisibleGlobal.top);

        // Restore canvas to its original state
        mCanvas.restoreToCount(1);
        mCanvas.setBitmap(mBitmap);
        // Using scale matrix will make draw call to match
        // resized off-screen bitmap size
        mCanvas.setMatrix(mMatrixScale);
        // Off-screen bitmap does not cover the whole screen
        // Use canvas translate to match its position on screen
        mCanvas.translate(dx, dy);
        // Clip rect is the same as we have
        // TODO: Why does this not work on API 18?
        // mCanvas.clipRect(mRectVisibleGlobal);
        // Save current canvas state
        mCanvas.save();
        // Start drawing from the root view
        mView.getRootView().draw(mCanvas);
		return mBitmap;
    }

}
