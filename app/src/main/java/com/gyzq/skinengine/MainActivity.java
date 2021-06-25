package com.gyzq.skinengine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyzq.skin.SkinManager;
import com.gyzq.skin.language.LanguageConvert;
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


        TextView jar_st_text = findViewById(R.id.jar_st_text);

        jar_st_text.setText("加强课堂教学，提高课堂效率。本学期共开校公开课2节，家长开放日1节，执教者认真备课、上课、反思，听课教师认真听课，集体评议，相互研讨，共同促进，形成良好的教研氛围");

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
            }
        });
        findViewById(R.id.jumpToStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TwoActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.jar_to_st).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jar转换简繁体
                jar_st_text.setText(LanguageConvert.s2t("加强课堂教学，提高课堂效率。本学ZHConverter converter = ZHConverter.getInstance(ZHConverter.TRADITIONAL);\n" +
                        "                changeText = converter.convert(changeText);\n" +
                        "            } catch (Exception e1) {\n" +
                        "                e1.printStackTrace()期共开校公开课2节，家长开放日1节，执教者认真备课、上课、反思，听课教师认真听课，集体评议，相互研讨，共同促进，形成良好的教研氛围"));
            }
        });
    }
}