package cn.kli.weather.theme;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import android.content.Context;
import android.graphics.drawable.Drawable;
import cn.kli.weather.R;
import cn.kli.weather.engine.Weather;

public class WeatherIconUtils {
    
    public static Drawable getDrawable(Context context, int weather) {
        int resId = R.raw.w_qing;
        int res = R.raw.w_qing;
        switch(weather){
        case Weather.W_QING:
            res = R.drawable.a_0;
            break;
        case Weather.W_DUOYUN:
            res = R.drawable.a_1;
            break;
        case Weather.W_MAI:
        case Weather.W_YIN:
            res = R.drawable.a_2;
            break;
        case Weather.W_ZHENYU:
            res = R.drawable.a_3;
            break;
        case Weather.W_LEIZHENYU:
            res = R.drawable.a_4;
            break;
        case Weather.W_LEIZHENYUBINGBANYOUBINGBAO:
            res = R.drawable.a_5;
            break;
        case Weather.W_YUJIAXUE:
            res = R.drawable.a_6;
            break;
        case Weather.W_XIAOYU:
            res = R.drawable.a_7;
            break;
        case Weather.W_ZHONGYU:
            res = R.drawable.a_8;
            break;
        case Weather.W_DAYU:
            res = R.drawable.a_9;
            break;
        case Weather.W_BAOYU:
            res = R.drawable.a_10;
            break;
        case Weather.W_DABAOYU:
            res = R.drawable.a_11;
            break;
        case Weather.W_TEDABAOYU:
            res = R.drawable.a_12;
            break;
        case Weather.W_ZHENXUE:
            res = R.drawable.a_13;
            break;
        case Weather.W_XIAOXUE:
            res = R.drawable.a_14;
            break;
        case Weather.W_ZHONGXUE:
            res = R.drawable.a_15;
            break;
        case Weather.W_DAXUE:
            res = R.drawable.a_16;
            break;
        case Weather.W_BAOXUE:
            res = R.drawable.a_17;
            break;
        case Weather.W_WU:
            res = R.drawable.a_18;
            break;
        case Weather.W_DONGYU:
            res = R.drawable.a_19;
            break;
        case Weather.W_SHACHENBAO:
            res = R.drawable.a_20;
            break;
        case Weather.W_XIAOYUZHONGYU:
            res = R.drawable.a_21;
            break;
        case Weather.W_ZHONGYUDAYU:
            res = R.drawable.a_22;
            break;
        case Weather.W_DAYUBAOYU:
            res = R.drawable.a_23;
            break;
        case Weather.W_BAOYUDABAOYU:
            res = R.drawable.a_24;
            break;
        case Weather.W_DABAOYUTEDABAOYU:
            res = R.drawable.a_25;
            break;
        case Weather.W_XIAOXUEZHONGXUE:
            res = R.drawable.a_26;
            break;
        case Weather.W_ZHONGXUEDAXUE:
            res = R.drawable.a_27;
            break;
        case Weather.W_DAXUEBAOXUE:
            res = R.drawable.a_28;
            break;
        case Weather.W_FUCHEN:
            res = R.drawable.a_29;
            break;
        case Weather.W_YANGSHA:
            res = R.drawable.a_30;
            break;
        case Weather.W_QIANGSHACHENBAO:
            res = R.drawable.a_31;
            break;
        }
        SVG svg = SVGParser.getSVGFromResource(context.getResources(), resId);
        Drawable drawable = svg.createPictureDrawable();
        return drawable;
    }
}
