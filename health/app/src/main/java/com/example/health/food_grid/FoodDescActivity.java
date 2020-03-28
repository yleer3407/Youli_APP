package com.example.health.food_grid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.health.HomeMenuActivity;
import com.example.health.R;
import com.example.health.bean.FoodBean;

public class FoodDescActivity extends AppCompatActivity {
    TextView titleTv1,titleTv2,descTv,notTv;
    ImageView backIv,bigPicIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_desc);
        initView();
        //接受上一级传来数据
        final Intent intent = getIntent();
        FoodBean foodBean = (FoodBean) intent.getSerializableExtra("food");
        //设置显示控件
        titleTv1.setText(foodBean.getTitle());
        titleTv2.setText(foodBean.getTitle());
        descTv.setText(foodBean.getDesc());
        notTv.setText(foodBean.getNotmatch());
        bigPicIv.setImageResource(foodBean.getPicId());
        Button button01 = findViewById(R.id.btn_cooking);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(FoodDescActivity.this, HomeMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        titleTv1=findViewById(R.id.fooddesc_tv_title1);
        titleTv2=findViewById(R.id.fooddesc_tv_title2);
        descTv=findViewById(R.id.fooddesc_tv_desc);
        notTv=findViewById(R.id.fooddesc_tv_not);
        backIv=findViewById(R.id.fooddesc_iv_back);
        bigPicIv=findViewById(R.id.fooddesc_iv_bigpic);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//销毁当前的activity
            }
        });

    }

}
