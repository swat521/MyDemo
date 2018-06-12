package vip.pk.lib.title_bar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vip.pk.lib.R;


public class TitleBar  extends LinearLayout {

    View topView;

    public TitleBar(Context context) {
        super(context);
        topView = LayoutInflater.from(context).inflate(R.layout.title_bar, null);
        addView(topView);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        topView = LayoutInflater.from(context).inflate(R.layout.title_bar, null);
        addView(topView);
    }

    @SuppressLint("NewApi")
    public TitleBar(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        topView = LayoutInflater.from(context).inflate(R.layout.title_bar, null);
        addView(topView);
    }

    public void SetTitle(String title){
        TextView topTitle = (TextView) topView.findViewById(R.id.tv_title);
        topTitle.setText(title);
    }

    public void ShowBackBtn(){
        ImageView btnBack = (ImageView)topView.findViewById(R.id.iv_btn_back);
        btnBack.setVisibility(View.VISIBLE);


    }
    public void SetBgColor(String colorStr){
        topView.setBackgroundColor(Color.parseColor(colorStr));
    }


}