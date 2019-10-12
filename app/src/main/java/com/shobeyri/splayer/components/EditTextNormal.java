package com.shobeyri.splayer.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.shobeyri.splayer.R;

public class EditTextNormal extends MaterialEditText {
    public EditTextNormal(Context context) {
        super(context);
        init(context, null, 0);
    }

    public EditTextNormal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public EditTextNormal(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        init(context, attrs, style);
    }

    public void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewNormal ,defStyleAttr, 0);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gabriola.ttf"));
    }

}
