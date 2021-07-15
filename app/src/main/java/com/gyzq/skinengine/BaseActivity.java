package com.gyzq.skinengine;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;


/**
 * @author: liujie
 * @date: 2021/6/21
 * @description:
 */
public abstract class BaseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void toActivity(@NonNull Class cl) {
        startActivity(new Intent(this, cl));
    }

    protected void toActivity(@NonNull Class cl, Bundle bundle) {
        Intent intent = new Intent(this, cl);
        intent.putExtra(cl.getSimpleName(), bundle);
        startActivity(intent);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(LanguageManager.getInstance().attachBaseContext(newBase));
//    }


    public Context getActivity() {
        return BaseActivity.this;
    }

    public Activity getActivityThis() {
        return BaseActivity.this;
    }


}
