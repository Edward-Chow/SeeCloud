package com.example.seecloud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.seecloud.algrithom.CredibilityCalculate;
import com.example.seecloud.database.CloudServer;
import com.scalified.fab.ActionButton;
import com.spark.submitbutton.SubmitButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK;

/**
 * Created by monkeysmac on 2017/4/1.
 */

public class MainActivity extends AppCompatActivity {

    private SubmitButton submitButton;

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private TextView text;

    private ActionButton actionButton;

    private SharedPreferences sharedPreferences;

    private int weight[] = new int[9];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT > 23) {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
            }
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        text = (TextView) findViewById(R.id.mainText);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, 0, 0);
        drawerToggle.syncState();

        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setCheckedItem(R.id.nav_calculate);
        //navagationbar监听事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_calculate:
                        break;
                    case R.id.nav_rank:
                        Intent intent = new Intent(MainActivity.this, RankActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_about:
                        Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }

                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        submitButton = (SubmitButton) findViewById(R.id.submitButton);
        //submit按钮监听事件
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //计算信誉度并跳转图表界面
                for (int i = 0; i < weight.length; i++) {
                    weight[i] = sharedPreferences.getInt(Integer.toString(i), 0);
                    Log.e("weight", Double.toString(weight[i]));
                }

                //插入算法直接计算出信任度值。
                CredibilityCalculate credibilityCalculate = new CredibilityCalculate(weight);


                List<CloudServer> cloudServerList = new ArrayList<CloudServer>(credibilityCalculate.getCloudServerList());

                //Log.e("第一手", cloudServerList.toString());


                for (CloudServer c:cloudServerList) {
                    switch (c.getName()) {
                        case "金山云":
                            c.setImageID(R.drawable.jinshan);
                            break;
                        case "青云":
                            c.setImageID(R.drawable.qingyun);
                            break;
                        case "腾讯云":
                            c.setImageID(R.drawable.tengxun);
                            break;
                        case "UnitedStack":
                            c.setImageID(R.drawable.unitedstack);
                            break;
                        case "UCloud":
                            c.setImageID(R.drawable.ucloud);
                            break;
                        case "华为云":
                            c.setImageID(R.drawable.huawei);
                            break;
                        case "百度云":
                            c.setImageID(R.drawable.baidu);
                            break;
                        case "微软云":
                            c.setImageID(R.drawable.azure);
                            break;
                        case "AWS-China":
                            c.setImageID(R.drawable.awschina);
                            break;
                        case "阿里云":
                            c.setImageID(R.drawable.ali);
                            break;
                        case "移动云":
                            c.setImageID(R.drawable.yidong);
                            break;
                        case "联通沃云":
                            c.setImageID(R.drawable.liantong);
                            break;
                        case "天翼云":
                            c.setImageID(R.drawable.tianyi);
                            break;
                        default:
                            break;
                    }
                }
                Intent intent = new Intent(MainActivity.this, CredibilityRankActivity.class);
                intent.putExtra("cloudServerList", (Serializable)cloudServerList);
                startActivity(intent);
            }
        });

        actionButton = (ActionButton) findViewById(R.id.action_button);
        //权重设置按钮监听事件
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转权重设置界面
                Intent intent = new Intent(MainActivity.this, WeightSettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
