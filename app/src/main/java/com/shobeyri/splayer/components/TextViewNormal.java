package com.shobeyri.splayer.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
//import android.support.annotation.Nullable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.shobeyri.splayer.R;

import me.grantland.widget.AutofitTextView;
//import setare_app.ymz.yma.setareyek.R;

/**
 * Created by -YMZ- on 04/07/2018.
 */

public class TextViewNormal extends AutofitTextView {

    Context context ;
//    boolean style = false;
//    Integer font = null ;
    public TextViewNormal(Context context) {
        super(context);
        this.context = context;
        init(context,null,0);
        //init(context,null);
    }

    public TextViewNormal(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context,attrs,0);
    }


    public TextViewNormal(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    public void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewNormal ,defStyleAttr, 0);
//         style = typedArray.getBoolean(R.styleable.TextViewNormal_text_style,false);
//        font = typedArray.getInteger(R.styleable.TextViewNormal_fontType,0);

//        if(!style)
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gabriola.ttf"));
//        else
//            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/yekan_bold.ttf"));

//        if(font != 0){
//           switch (font){
//               case 1 :
//                   setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/yekan.ttf"));
//                   break;
//               case 2 :
//                   setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/yekan_bold.ttf"));
//                   break;
//               case 3 :
//                   setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/yekn_light.ttf"));
//                   break;
//               case 4 :
//                   setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/yekan_medium.ttf"));
//                   break;
//               case 5 :
//                   setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/yekan_extra.ttf"));
//                   break;
//           }

//        }


    }

//    public void setBackgroundColor(int color, Context context){
//        GradientDrawable drawable = (GradientDrawable)context.getResources().getDrawable(R.drawable.rounded);
//        drawable.setColor(color);
//        setBackground(drawable);
//    }

    public void setTextStyle(Boolean textStyle){
        if(!textStyle)
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/yekan.ttf"));
        else
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/yekan_bold.ttf"));
    }

}
