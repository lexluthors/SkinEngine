package com.gyzq.skinengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gyzq.skin.SkinManager;

public class TwoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        findViewById(R.id.jumpToTwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoActivity.this,ThreeActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.chongzhi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().resetDefaultSkin();
            }
        });
        findViewById(R.id.ziti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().setSkinTypeface();
            }
        });
    }
}