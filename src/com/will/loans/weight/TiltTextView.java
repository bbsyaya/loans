
package com.will.loans.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class TiltTextView extends TextView {
    public TiltTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public TiltTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public TiltTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(45, 0, 0);
        super.onDraw(canvas);
    }

}
