package com.yidao.module_lib.widget.shape;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import com.yidao.module_lib.utils.DensityUtil;

public class ShapeDrawable extends GradientDrawable {


    public static GradientDrawable getGradientHalfRadius(Context context,int[] colors){
        GradientDrawable gradientDrawable = new GradientDrawable(Orientation.LEFT_RIGHT,colors);
        float radii = DensityUtil.dip2px(context,8);
        gradientDrawable.setCornerRadii(new float[]{radii,radii,0,0,radii,radii,0,0});
        return gradientDrawable;
    }
}
