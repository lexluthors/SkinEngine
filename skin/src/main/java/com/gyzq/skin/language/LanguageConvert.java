package com.gyzq.skin.language;

import taobe.tec.jcc.JChineseConvertor;

/**
 * @author: liujie
 * @date: 2021/6/24
 * @description:
 */
public class LanguageConvert {
    /**
     * 简体转成繁体  s2t
     * @param changeText
     * @return
     */
    public static String s2t(String changeText) {
        try {
            changeText = JChineseConvertor.getInstance().s2t(changeText);
        } catch (Exception e) {
            e.printStackTrace();
            return changeText;
        }
        return changeText;
    }

    /**
     * 繁体转成简体
     * @param changeText
     * @return
     */
    public static String t2s(String changeText) {
        try {
            changeText = JChineseConvertor.getInstance().t2s(changeText);
        } catch (Exception e) {
            e.printStackTrace();
            return changeText;
        }
        return changeText;
    }
}
