package com.example.seecloud;

import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.seecloud.database.Attribute;
import com.example.seecloud.database.CloudServer;
import com.example.seecloud.database.InitCloudServer;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private Attribute attributes[];

    private List<Attribute> attributeList = new ArrayList<>();

    private AttributeAdapter adapter;

    private List<CloudServer> cloudServers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.rank_toolbar);
        setSupportActionBar(toolbar);

        initAttributes();

        InitCloudServer initCloudServer = new InitCloudServer();
        cloudServers.addAll(initCloudServer.getClouds());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AttributeAdapter(attributeList);
        adapter.setOnItemClickListener(new AttributeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = attributeList.get(position).getName();
                double[] numbers = new double[13];
                Intent intent;
                switch (name) {
                    case "建联时间":
                        int k = 0;
                        for (CloudServer cloud:cloudServers) {
                            numbers[k++] = cloud.getAttributes().get(0).getNumber();
                        }
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "建联时间");
                        startActivity(intent);
                        break;
                    case "首包时间":
                        numbers = function(cloudServers, 1);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "首包时间");
                        startActivity(intent);
                        break;
                    case "首屏时间":
                        numbers = function(cloudServers, 2);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "首屏时间");
                        startActivity(intent);
                        break;
                    case "总下载时间":
                        numbers = function(cloudServers, 3);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "总下载时间");
                        startActivity(intent);
                        break;
                    case "应用服务器响应时间":
                        numbers = function(cloudServers, 4);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "应用服务器响应时间");
                        startActivity(intent);
                        break;
                    case "CPU使用率":
                        numbers = function(cloudServers, 5);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "CPU使用率");
                        startActivity(intent);
                        break;
                    case "数据库调用时间":
                        numbers = function(cloudServers, 6);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "数据库调用时间");
                        startActivity(intent);
                        break;
                    case "Apdex指标":
                        numbers = function(cloudServers, 7);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "Apdex指标");
                        startActivity(intent);
                        break;
                    case "服务器内存占用":
                        numbers = function(cloudServers, 8);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "服务器内存占用");
                        startActivity(intent);
                        break;
                    case "听云综合排行":
                        numbers = function(cloudServers, 9);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "听云综合排行");
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        adapter.setOnItemLongClickListener(new AttributeAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                String name = attributeList.get(position).getName();
                double[] numbers = new double[13];
                Intent intent;
                switch (name) {
                    case "建联时间":
                        int k = 0;
                        for (CloudServer cloud:cloudServers) {
                            numbers[k++] = cloud.getAttributes().get(0).getNumber();
                        }
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "建联时间");
                        startActivity(intent);
                        break;
                    case "首包时间":
                        numbers = function(cloudServers, 1);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "首包时间");
                        startActivity(intent);
                        break;
                    case "首屏时间":
                        numbers = function(cloudServers, 2);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "首屏时间");
                        startActivity(intent);
                        break;
                    case "总下载时间":
                        numbers = function(cloudServers, 3);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "总下载时间");
                        startActivity(intent);
                        break;
                    case "应用服务器响应时间":
                        numbers = function(cloudServers, 4);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "应用服务器响应时间");
                        startActivity(intent);
                        break;
                    case "CPU使用率":
                        numbers = function(cloudServers, 5);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "CPU使用率");
                        startActivity(intent);
                        break;
                    case "数据库调用时间":
                        numbers = function(cloudServers, 6);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "数据库调用时间");
                        startActivity(intent);
                        break;
                    case "Apdex指标":
                        numbers = function(cloudServers, 7);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "Apdex指标");
                        startActivity(intent);
                        break;
                    case "服务器内存占用":
                        numbers = function(cloudServers, 8);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "服务器内存占用");
                        startActivity(intent);
                        break;
                    case "听云综合排行":
                        numbers = function(cloudServers, 9);
                        intent = new Intent(RankActivity.this, ChartActivity.class);
                        intent.putExtra("index", numbers);
                        intent.putExtra("name", "听云综合排行");
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private double[] function(List<CloudServer> cloudservers, int k) {
        double[] numbers = new double[13];
        int m = 0;
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = cloudServers.get(i).getAttributes().get(k).getNumber();
        }
        /*for (CloudServer cloud:cloudServers) {
            numbers[m] = cloud.getAttributes().get(k).getNumber();
            m++;
        }*/
        return numbers;
    }

    private void initAttributes() {
        //数据初始化
        Attribute attributes[] = {
                new Attribute("建联时间", R.drawable.connectingtime),
                new Attribute("首包时间", R.drawable.firstpackagetime),
                new Attribute("首屏时间", R.drawable.firstscreentime),
                new Attribute("总下载时间", R.drawable.totaldownloadtime),
                new Attribute("应用服务器响应时间", R.drawable.applicationserverresponsetime),
                new Attribute("CPU使用率", R.drawable.cpuusage),
                new Attribute("数据库调用时间", R.drawable.databasecallingtime),
                new Attribute("Apdex指标", R.drawable.apdex),
                new Attribute("服务器内存占用", R.drawable.servermemorytakeup),
                new Attribute("听云综合排行", R.drawable.tingyuncomprehensiveranking)
        };
        for (Attribute attr: attributes) {
            attributeList.add(attr);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.id_back) {
            Intent intent = new Intent(RankActivity.this, MainActivity.class);
            startActivity(intent);
            RankActivity.this.finish();
            return true;
        }
        return false;
    }
}
