package com.example.seecloud;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.example.seecloud.database.CloudServer;

import java.util.ArrayList;
import java.util.List;

public class CredibilityRankActivity extends AppCompatActivity {

    private Button rank_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credibility_rank);

        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        }

        rank_back_button = (Button) findViewById(R.id.rank_back_button);
        rank_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CredibilityRankActivity.this, MainActivity.class);
                startActivity(intent);
                CredibilityRankActivity.this.finish();
            }
        });

        //从MainActivity中接收排序后的cloudserverlist，初始化
        List<CloudServer> cloudServerList = (List<CloudServer>) getIntent().getSerializableExtra("cloudServerList");
        //Log.e("list", cloudServerList.toString());
        CloudsAdapter adapter = new CloudsAdapter(CredibilityRankActivity.this, R.layout.clouds_rank_item, cloudServerList);
        ListView listView = (ListView) findViewById(R.id.clouds_list_view);
        listView.setAdapter(adapter);
    }
}
