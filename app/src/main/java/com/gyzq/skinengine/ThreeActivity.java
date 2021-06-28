package com.gyzq.skinengine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyzq.skin.SkinManager;
import com.gyzq.skin.font.DisplayUtil;
import com.gyzq.skin.language.Language;
import com.gyzq.skin.language.LanguageManager;

public class ThreeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        findViewById(R.id.chongzhi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().resetDefaultSkin();
            }
        });
        findViewById(R.id.huanfu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().loadSkin("/sdcard/app-debug.apk");
            }
        });
        findViewById(R.id.jumpList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreeActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });
        //繁体
        findViewById(R.id.jianfanti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageManager.getInstance().applyLanguage(new Language(Language.MODE.CUSTOM, LanguageManager.getTWLanguage()));
            }
        });
        //简体
        findViewById(R.id.jianti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageManager.getInstance().applyLanguage(new Language(Language.MODE.CUSTOM, LanguageManager.getZhLanguage()));
            }
        });
        //跟随系统
        findViewById(R.id.gensuixitong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageManager.getInstance().applyLanguage(new Language(Language.MODE.AUTO, LanguageManager.getTWLanguage()));
            }
        });
        //切换英文
        findViewById(R.id.english).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageManager.getInstance().applyLanguage(new Language(Language.MODE.CUSTOM, LanguageManager.getEnLanguage()));
            }
        });
       TextView changeFontSize =  findViewById(R.id.changeFontSize);
       changeFontSize.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               System.out.println("字体大小>>>>"+changeFontSize.getTextSize());
//               SkinResUtils.getInstance().changeFontScale(2.0f);
//               System.out.println("字体大小>>>>"+ DisplayUtil.px2sp(getActivity(),changeFontSize.getTextSize()));
               System.out.println("字体大小>>>>"+ DisplayUtil.px2sp(getActivity(),changeFontSize.getTextSize()));
               SkinManager.getInstance().setFontScale();
               System.out.println("字体大小>>>>"+ DisplayUtil.px2sp(getActivity(),changeFontSize.getTextSize()));
           }
       });
    }
}