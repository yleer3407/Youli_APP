package com.example.health.food_grid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.health.R;
import com.example.health.bean.FoodBean;
import com.example.health.bean.foodUtils;
import com.example.health.food_list.InfoListActivity;

import java.util.List;

public class FoodGridActivity extends AppCompatActivity {
    GridView gv;
    List<FoodBean> mDatas;
    private FoodGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_grid);
        gv = findViewById(R.id.food_grid_gv);
        //数据源
        mDatas = foodUtils.gatAllFoodList();
        //创建适配器对象
        adapter = new FoodGridAdapter(this, mDatas);
        //设置适配器
        gv.setAdapter(adapter);
        setListener();
    }
    private void setListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodBean foodBean = mDatas.get(position);
                Intent intent =new Intent(FoodGridActivity.this, FoodDescActivity.class);
                //此处的putExtra需要选择带序列化的 Serializable
                intent.putExtra("food",foodBean);
                startActivity(intent);
            }
        });
    }
}
