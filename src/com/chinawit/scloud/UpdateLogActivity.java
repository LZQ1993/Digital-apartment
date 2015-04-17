package com.chinawit.scloud;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.os.Bundle;
import android.widget.TextView;


public class UpdateLogActivity extends NavBarActivity {

    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_log);
        initNavBar("更新日志", "<返回", null);
        ((TextView) findViewById(R.id.update_log_tv)).setText(getTextFromLog());
    }

    /**
     * 读取文件字符串
     */
    private String getTextFromLog() {
        String text = "";
        try {
            InputStream in = getResources().openRawResource(R.raw.update);
            int length = in.available();       
            byte [] buffer = new byte[length];
            in.read(buffer);
            text = EncodingUtils.getString(buffer, "UTF-8");
            in.close();
            } catch(Exception e) {
                text += "更新日志文件读取失败！\n可能的原因是日志文件不存在！\n系统错误提示：\n" + e.toString();
            }
        return text;
    }

}
