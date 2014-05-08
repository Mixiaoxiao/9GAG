package com.mixiaoxiao.android.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MxxTopCropImageView extends ImageView{
	
	private boolean topCrop = true;

	public MxxTopCropImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MxxTopCropImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MxxTopCropImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean isTopCrop() {
		return topCrop;
	}

	public void setTopCrop(boolean topCrop) {
		this.topCrop = topCrop;
		if(topCrop){
			invalidate();
		}
	}

	@Override
    protected boolean setFrame(int l, int t, int r, int b) {
//        if (PlusScaleType.TOP_CROP.equals(mScaleType)) {
		if(topCrop){
            Drawable drawable = super.getDrawable();

            if (drawable != null) {
                float floatLeft = (float) l;
                float floatRight = (float) r;

                float intrinsicWidth = drawable.getIntrinsicWidth();
                float intrinsicHeight = drawable.getIntrinsicHeight();

                int frameHeight = b - t;

                if (intrinsicWidth != -1) {
                    float scaleFactor = (floatRight - floatLeft) / intrinsicWidth;

                    if (scaleFactor * intrinsicHeight < frameHeight) {
                        setScaleType(ScaleType.FIT_CENTER);
                    } else {
                        setScaleType(ScaleType.MATRIX);
                        Matrix matrix = new Matrix();
                        // scale width
                        matrix.setScale(scaleFactor, scaleFactor);
                        setImageMatrix(matrix);
                    }

                }
            }
        }

        return super.setFrame(l, t, r, b);
    }
}
