package com.will.loans.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class RotationTextView extends TextView {

	public RotationTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RotationTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RotationTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.rotate(45, getMeasuredWidth()/2, getMeasuredHeight()/2); 
		super.onDraw(canvas);
	}

}
