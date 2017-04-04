package com.example.seecloud;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.scalified.fab.ActionButton;
import com.spark.submitbutton.SubmitButton;

/**
 * Created by monkeysmac on 2017/4/1.
 */

public class MainActivity extends AppCompatActivity {

    private SubmitButton submitButton;

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private ActionButton actionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

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
