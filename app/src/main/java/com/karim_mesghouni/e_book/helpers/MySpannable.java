package com.karim_mesghouni.e_book.helpers;

import android.graphics.Color;
import android.text.TextPaint;
import android.view.View;

public class MySpannable {
    private boolean isUnderline = true;

    /**
     * Constructor
     */
    public MySpannable(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }


    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(isUnderline);
        ds.setColor(Color.parseColor("#1b76d3"));
    }


    public void onClick(View widget) {


    }
}
