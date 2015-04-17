package com.chinawit.scloud;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.dormCall.DormCallMainActivity;
import com.chinawit.scloud.dormDanger.DormDangerMainActivity;
import com.chinawit.scloud.dormNotice.DormNoticeMainActivity;
import com.chinawit.scloud.dormPlate.DormPlateMainActivity;
import com.chinawit.scloud.dormScore.DormScoreMainActivity;
import com.chinawit.scloud.dormSearch.DormSearchMainActivity;
import com.chinawit.scloud.dromCheck.DormCheckBeginActivity;
import com.chinawit.scloud.more.More;

public class MainActivity extends NavBarActivity {
	private final int[] iconResIds = new int[] {
	        R.drawable.main_item_dormplate,
	        R.drawable.main_item_dormsearch,
	        R.drawable.main_item_dormcheck,
	        R.drawable.main_item_dormscore,
	        R.drawable.main_item_dormcall,
	        R.drawable.main_item_dangerwarning
	    	};
	private Class<?>[] gotoClzs = new Class[] {
	        DormPlateMainActivity.class,
	        DormSearchMainActivity.class,
	        DormCheckBeginActivity.class,
	        DormScoreMainActivity.class,
	        DormCallMainActivity.class,	        
	        DormDangerMainActivity.class
	    };
	    
	    private boolean[] needLoginFlags = new boolean[] {
	        false,
	        true,
	        true,
	        false,
	        false,
	        false
	    };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        initNavBar("首页", null, null);
        initGridView();

	}

	 /**
     * 初始化GridView
     */
    private void initGridView() {
    	GridView gridView = (GridView) findViewById(R.id.main_gridView);
    	ArrayList<HashMap<String, Object>>lstImageItem = new ArrayList<HashMap<String,Object>>();
    	for (int i = 0;i<this.iconResIds.length;i++) {        
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("itemImage", iconResIds[i]);  
            lstImageItem.add(map);
        }
    	SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem, R.layout.activity_main_item, new String[] {"itemImage"}, new int[] {R.id.main_itemImage});
        gridView.setAdapter(saImageItems);
        gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id ) {
				if (gotoClzs[position] != null) { //模块存在
                    if (gotoClzs[position].equals(More.class)) {
                        Toast.makeText(getApplicationContext(),"更多功能，敬请关注应用动态！",Toast.LENGTH_SHORT).show();
                    }
                    else if (needLoginFlags[position] == false || UserInfo.getUser() != null) { //不需要登录
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, gotoClzs[position]);
                        startActivity(intent);
                    } else { //需要登录
                        new AlertDialog.Builder(MainActivity.this)    
                        .setTitle("提示")  
                        .setMessage("该功能模块，仅特定权限账户才能使用。您未登录账户。\n您确定要登录账户？")  
                        .setPositiveButton("确定", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("goto", gotoClzs[position].getName());
                                intent.setClass(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"该功能暂未开放，敬请关注！",Toast.LENGTH_SHORT).show();
                }
            }
        	
        });
    	
    }
  
  
    /**
     * 返回键
     */
    @Override
    public void onBackPressed() {
        onExit();
    }
    
    /**
     * 退出
     */
    public void onExit() {
        new AlertDialog.Builder(this)
        .setTitle("确定退出程序？")
        .setIcon(R.drawable.exit)
        .setPositiveButton("确定",new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	UserInfo.signOut();
                finish();
            }
        })
        .setNegativeButton("取消", null)
        .show();
    }
    /**
     * 菜单键
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE,1,1,"账户").setIcon(R.drawable.menu_login);
        menu.add(Menu.NONE,2,2,"设置").setIcon(R.drawable.menu_setting);
        menu.add(Menu.NONE,3,3,"关于").setIcon(R.drawable.menu_about);
        menu.add(Menu.NONE,4,4,"问题反馈").setIcon(R.drawable.menu_bugfeedfack);
        menu.add(Menu.NONE,5,5,"访问官网").setIcon(R.drawable.menu_web);
        menu.add(Menu.NONE,6,6,"退出").setIcon(R.drawable.menu_quit);
        return true;
    }

    /**
     * 菜单选项
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case 1: { //账户
                Intent intent = new Intent();
               if (UserInfo.getUser() != null) { //如果在线
                   intent.setClass(MainActivity.this,UserInfoActivity.class);
               } else {//如果不在线
                    intent.putExtra("goto", UserInfoActivity.class.getName());
                    intent.setClass(MainActivity.this,LoginActivity.class);
                }
                startActivity(intent);
                break;
            }
            case 2: { //设置
                Intent intent = new Intent();
                intent.setClass(this,SettingActivity.class);
                startActivityForResult(intent,100);
                break;
            }
            case 3: { //关于
                Intent intent = new Intent();
                intent.setClass(this,AboutActivity.class);
                startActivity(intent);
                break;
            }
            case 4: { //问题反馈
                Intent intent = new Intent();
                intent.setClass(this,BugFeedbackActivity.class);
                startActivity(intent);
                break;
            }
            case 5: { //访问官网
                Uri uri = Uri.parse("http://192.168.202.108d");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);  
                break;
            }
            case 6: { //退出
                new AlertDialog.Builder(this) 
                .setTitle("确定退出程序？")
                .setIcon(R.drawable.exit)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    	UserInfo.signOut();
                    	 finish();  
                    }
                })
                .setNegativeButton("取消", null)
                .show();
                break;
            }
        }
        return true;
    }
}
