package com.chinawit.scloud.dormCall;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;

public class DormCallMainActivity extends NavBarActivity{

	private TextView tv_call;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_call_main);
		initNavBar("申诉平台", "<返回", null);
	   ((TextView) findViewById(R.id.dorm_call_main_tv_pag)).setText(getTextFromLog());
	 
	}

    /**
     * 读取文件字符串
     */
    private String getTextFromLog() {
        String text = "";
        try {
            InputStream in = getResources().openRawResource(R.raw.complain);
            int length = in.available();       
            byte [] buffer = new byte[length];
            in.read(buffer);
            text = EncodingUtils.getString(buffer, "UTF-8");
            in.close();
            } catch(Exception e) {
                text +=e.toString();
            }
        return text;
    }
    
  

}
	
	

