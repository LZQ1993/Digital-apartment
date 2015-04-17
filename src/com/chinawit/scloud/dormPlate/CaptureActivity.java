package com.chinawit.scloud.dormPlate;

import java.io.IOException;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.SettingInfo;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class CaptureActivity extends NavBarActivity implements Callback {
    
    private final static String TAG = "ZBar";

    static {
        System.loadLibrary("iconv"); //本地动态库
    }
    
    private Camera camera = null;
    private SurfaceView surfaceView;
    private ImageScanner scanner;
    
    private boolean cameraLightOn = false;
    private SoundPool soundPool;
    private int soundId;
    private Vibrator vibrator;

    private TextView tvLight;
    private ImageButton imBtnLight;

    /**
     * onCreate 
     */
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        initNavBar("二维码扫描器", "<返回", "设置");
        //布局
        surfaceView = (SurfaceView) findViewById(R.id.capture_sv_cameraPreview);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //兼容3.0以前版本
        //解码实例 
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);
        //初始化声音
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(this, R.raw.beep, 0);
        //初始化震动
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //控件
        tvLight = (TextView) findViewById(R.id.capture_tv_light);
        imBtnLight = (ImageButton) findViewById(R.id.capture_imBtn_light);
    }
    
    /**
     * 恢复
     */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            camera = Camera.open();
        } catch(Exception e) {
        }
        if (camera != null) {
            try {
                camera.setPreviewDisplay(surfaceView.getHolder());
                camera.setDisplayOrientation(90);
                camera.setPreviewCallback(previewCb);
                //camera.autoFocus(autoFocusCB); //自动调焦-部分手机不支持
                if (cameraLightOn) openCameraLight(); //开灯
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                new AlertDialog.Builder(this)    
                .setTitle("错误")  
                .setMessage("摄像机图像初始化失败。")
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
            }
        } else {
            new AlertDialog.Builder(this)    
            .setTitle("错误")  
            .setMessage("未获取到摄像头数据，请重试。")
            .setPositiveButton("确定", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .show();
        }
    }

    /**
     * 暂停
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            previewing = false;
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    /**
     * 打开摄像机灯光
     */
    private void openCameraLight() {
        if (camera != null) {
            Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
        }
    }
    
    /**
     * 关闭摄像机灯光
     */
    private void closeCameraLight() {
        if (camera != null) {
            Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
        }
    }

    /**
     * 开灯按键
     */
    public void onButtonLightClick(View view) {
        if (cameraLightOn) {
            cameraLightOn = false;
            closeCameraLight();
            tvLight.setText("开灯");
            imBtnLight.setImageResource(R.drawable.qb_scan_btn_flash_nor);
        } else {
            cameraLightOn = true;
            openCameraLight();
            tvLight.setText("关灯");
            imBtnLight.setImageResource(R.drawable.qb_scan_btn_flash_down);
        }
    }

    /**
     * 导航左键
     */
    @Override
    public void onNavBarLeftButtonClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
    
    /**
     * 导航右键-打开设置
     */
    @Override
	public void onNavBarRightButtonClick(View view) {
        String[] titles = new String[] {
        	"启用声音提示",
        	"启用震动提示"
        };
        boolean[] settings = new boolean[] {
        	SettingInfo.isQrcodePlayBeepSound(),
        	SettingInfo.isQrcodeVibrate()
        };
        new AlertDialog.Builder(this)
        .setTitle("扫描器设置")
        .setMultiChoiceItems(titles, settings, new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean bool) {
                if (position == 0) {
                	SettingInfo.setQrcodePlayBeepSound(bool);
                } 
                else if (position == 1) {
                	SettingInfo.setQrcodeVibrate(bool);
                }
            }
        })
        .setPositiveButton("确定", null)
        .show();
	}

    /**
     * 返回键
     */
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (camera != null) {
            try {
                camera.setPreviewDisplay(holder);
                camera.autoFocus(autoFocusCB); //自动调焦
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    /*
     * 模拟连续自动对焦
     */
    private Handler autoFocusHandler = new Handler();
    private boolean previewing = true; 

    private AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    private Runnable doAutoFocus = new Runnable() {
        @Override
        public void run() {
            if (previewing) camera.autoFocus(autoFocusCB);
        }
    };

    private PreviewCallback previewCb = new PreviewCallback() {
        
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Parameters parameters = camera.getParameters();
            Size size = parameters.getPreviewSize();

            Image image = new Image(size.width, size.height, "Y800");
            image.setData(data);
            int result = scanner.scanImage(image);
            //解码成功
            if (result != 0) {
            	
            	
            	//停止摄像机                
                previewing = false;
                camera.setPreviewCallback(null);
                camera.stopPreview();
                //蜂鸣
                if (SettingInfo.isQrcodePlayBeepSound()) soundPool.play(soundId, 1, 1, 0, 0, 1);
                //震动
                if (SettingInfo.isQrcodeVibrate()) vibrator.vibrate(500);
                //获取结果
                SymbolSet set = scanner.getResults();
                for (Symbol s : set) {
                    //调试
                    Log.d(TAG, s.getData());
                    Log.d(TAG, getCodeType(s.getType()));
                    //彩蛋
                    if (s.getData().equalsIgnoreCase("Hello World") || s.getData().equalsIgnoreCase("Android") || s.getData().equalsIgnoreCase("SCloud")) {
                       // startActivity(new Intent(CaptureActivity.this, AuthorInfoActivity.class));
                        setResult(RESULT_CANCELED);
                        break; //跳出循环
                    }
                    //解码
                    if (s.getData() != null && !"".equals(s.getData())) {
                        Intent intent = new Intent();
                        intent.putExtra("data", s.getData());
                        intent.putExtra("type", getCodeType(s.getType()));
                        setResult(RESULT_OK, intent);
                    } else {
                        setResult(RESULT_ERROR);
                    }
                    break; //跳出循环
                }
                finish();
            }
        }

    };
    
    /**
     * 获取数据类型
     */
    private String getCodeType(int type) {
        switch(type) {
        case Symbol.QRCODE : return "QRCODE";
        case Symbol.PDF417 : return "PDF417";
        case Symbol.CODABAR : return "CODABAR";
        case Symbol.CODE128 : return "CODE128";
        case Symbol.CODE39 : return "CODE39";
        case Symbol.CODE93 : return "CODE93";
        case Symbol.DATABAR :return "DATABAR";
        case Symbol.DATABAR_EXP : return "DATABAR_EXP";
        case Symbol.EAN13 : return "EAN13";
        case Symbol.EAN8 : return "EAN8";
        case Symbol.I25 : return "I25";
        case Symbol.ISBN10 : return "ISBN10";
        case Symbol.ISBN13 : return "ISBN13";
        case Symbol.PARTIAL : return "PARTIAL";
        case Symbol.UPCA : return "UPCA";
        case Symbol.UPCE : return "UPCE";
        case Symbol.NONE : return "NONE";
        default : return "NONE";
        }
    }

}
