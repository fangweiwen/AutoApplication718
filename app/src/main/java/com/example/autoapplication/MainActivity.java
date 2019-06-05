package com.example.autoapplication;

import android.Manifest;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.autoapplication.adapter.SMSAdapter;
import com.example.autoapplication.entity.SmsEntity;
import com.example.autoapplication.util.RxPermissionsUtils;
import com.example.autoapplication.util.SMSUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private boolean isFirst = true;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager manager;
    private SMSAdapter adapter;
    private List<SmsEntity> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        init();
        datas = SMSUtils.getSmsInPhone(this);
        adapter.setNewData(datas);
        ///
    }

    private void init() {
        mRecyclerView = findViewById(R.id.mRecyclerView);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new SMSAdapter();
        mRecyclerView.setAdapter(adapter);
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        adapter.setOnItemClickLisenter(new OnItemClickLisenter() {
            @Override
            public void onItemClickLisenter(SmsEntity data) {
                Toast.makeText(MainActivity.this, "发件人 : " + data.getStrAddress(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestPermission() {
        RxPermissionsUtils.requestPermission(this, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "授权失败,请重新授权", Toast.LENGTH_SHORT).show();
                    if (isFirst) {
                        isFirst = false;
                        requestPermission();
                    } else {
                        onBackPressed();
                    }
                }
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        datas.clear();
        datas = SMSUtils.getSmsInPhone(this);
        adapter.setNewData(datas);
    }


}
