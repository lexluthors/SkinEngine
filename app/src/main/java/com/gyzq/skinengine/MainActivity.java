package com.gyzq.skinengine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gyzq.skin.SkinManager;
import com.gyzq.skin.language.Language;
import com.gyzq.skin.language.LanguageManager;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String[] permissionStrs = {Permission.WRITE_EXTERNAL_STORAGE,Permission.READ_EXTERNAL_STORAGE};
        XXPermissions.with(this)
                .permission(permissionStrs)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {

                    }
                });





        //app-debug.apk
        findViewById(R.id.huanfu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().loadSkin("/sdcard/app-debug.apk");
            }
        });
        findViewById(R.id.chongzhi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().resetDefaultSkin();
                LanguageManager.getInstance().applyLanguage(new Language(Language.MODE.CUSTOM,LanguageManager.getEnLanguage()));
            }
        });
        findViewById(R.id.jumpToStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TwoActivity.class);
                startActivity(intent);
            }
        });
    }
}