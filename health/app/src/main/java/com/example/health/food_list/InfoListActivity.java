package com.example.health.food_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.health.R;
import com.example.health.bean.FoodBean;
import com.example.health.bean.foodUtils;
import com.example.health.food_grid.FoodDescActivity;

import java.util.ArrayList;
import java.util.List;

public class InfoListActivity extends AppCompatActivity implements View.OnClickListener {
    EditText searchEt;
    ImageView searchIv,flushIv;
    ListView showLv;
    //ListView 内部数据源
    List<FoodBean>mDatas;
    List<FoodBean>allFoodList;
    private InfoListActivity activity;
    private InfoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_list);
        initView();
        //找到对应数据源
        mDatas = new ArrayList<>();
        allFoodList = foodUtils.gatAllFoodList();
        mDatas.addAll(allFoodList);
        //创建适配器 BaseAdapter的子类
        adapter = new InfoListAdapter(this, mDatas);
        //设置适配器
        showLv.setAdapter(adapter);
        //单项点击监听功能
        setListener();
        
    }

    private void setListener() {
        showLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodBean foodBean = mDatas.get(position);
                Intent intent =new Intent(InfoListActivity.this, FoodDescActivity.class);
                //此处的putExtra需要选择带序列化的 Serializable
                intent.putExtra("food",foodBean);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        searchEt = findViewById(R.id.info_et_search);
        searchIv = findViewById(R.id.info_iv_search);
        flushIv = findViewById(R.id.info_iv_flush);
        showLv = findViewById(R.id.infolist_lv);
        searchIv.setOnClickListener(this);//添加点击事件的监听器
        flushIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_iv_flush://刷新点击
                searchEt.setText("");
                mDatas.clear();
                mDatas.addAll(allFoodList);
                adapter.notifyDataSetChanged();
                break;
            case R.id.info_iv_search://搜索点击
                //1.获取输入内容 判断不为空
                String msg = searchEt.getText().toString().trim();//获取输入信息
                if (TextUtils.isEmpty(msg)){
                    Toast.makeText(this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //2.判断所有食物列表标题是否包含输入内容，如果包含，就添加到小集合中
                List<FoodBean>list = new ArrayList<>();
                for (int i=0;i<allFoodList.size();i++){
                    String title = allFoodList.get(i).getTitle();
                    if (title.contains(msg)){
                        list.add(allFoodList.get(i));
                    }
                    //清空listview的适配器数据内容
                    mDatas.clear();
                    mDatas.addAll(list);
                    adapter.notifyDataSetChanged();//提示适配器更新

                }
                break;


        }
    }
}
