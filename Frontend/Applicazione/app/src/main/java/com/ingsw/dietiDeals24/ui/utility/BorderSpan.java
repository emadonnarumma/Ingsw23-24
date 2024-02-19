package com.ingsw.dietiDeals24.ui.utility;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

public class BorderSpan extends ReplacementSpan {

    private final Paint paint;

    public BorderSpan(int borderColor, int borderWidth) {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        paint.setStrokeWidth(borderWidth);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(paint.measureText(text, start, end));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top, x + this.getSize(paint, text, start, end, null), bottom);
        canvas.drawRect(rect, this.paint);
        canvas.drawText(text, start, end, x, y, paint);
    }
}