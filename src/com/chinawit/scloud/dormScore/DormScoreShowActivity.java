package com.chinawit.scloud.dormScore;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chinawit.scloud.MainActivity;
import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.bean.DormScore;

public class DormScoreShowActivity extends NavBarActivity{

	private TextView tv_term;
	private TextView tv_week;
	private TextView tv_checkType;
	private TextView tv_college;
	private TextView tv_dormNum;
	private TextView tv_checkClass;
	private TextView tv_score;
	private TextView tv_level;
	private TextView tv_ground;
	private TextView tv_window;
	private TextView tv_bed;
	private TextView tv_smoker;
	private TextView tv_whole;
	private TextView tv_smell;
	private TextView tv_goods;
	private TextView tv_toilet;
	private TextView tv_garbage;
	private TextView tv_attitude;
	private TextView tv_iIIegal;
	private TextView tv_remark;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_scoreshow);
		initNavBar("详细评比结果","<返回", "首页");
		tv_term= (TextView) findViewById(R.id.dorm_score_show_tv_term);
		tv_week= (TextView) findViewById(R.id.dorm_score_show_tv_week);
		tv_checkType= (TextView) findViewById(R.id.dorm_score_show_tv_checkType);
		tv_college= (TextView) findViewById(R.id.dorm_score_show_tv_college);
		tv_dormNum= (TextView) findViewById(R.id.dorm_score_show_tv_dormNum);
		tv_checkClass= (TextView) findViewById(R.id.dorm_score_show_tv_checkClass);
		tv_score= (TextView) findViewById(R.id.dorm_score_show_tv_score);
		tv_level= (TextView) findViewById(R.id.dorm_score_show_tv_level);
		tv_ground= (TextView) findViewById(R.id.dorm_score_show_tv_ground);
		tv_window= (TextView) findViewById(R.id.dorm_score_show_tv_window);
		tv_bed= (TextView) findViewById(R.id.dorm_score_show_tv_bed);
		tv_smoker= (TextView) findViewById(R.id.dorm_score_show_tv_smoker);
		tv_whole= (TextView) findViewById(R.id.dorm_score_show_tv_whole);
		tv_smell= (TextView) findViewById(R.id.dorm_score_show_tv_smell);
		tv_goods= (TextView) findViewById(R.id.dorm_score_show_tv_goods);
		tv_toilet= (TextView) findViewById(R.id.dorm_score_show_tv_toilet);
		tv_garbage= (TextView) findViewById(R.id.dorm_score_show_tv_garbage);
		tv_attitude= (TextView) findViewById(R.id.dorm_score_show_tv_attitude);
		tv_iIIegal= (TextView) findViewById(R.id.dorm_score_show_tv_iIIegal);
		tv_remark= (TextView) findViewById(R.id.dorm_score_show_tv_remark);
		ScoreResultShow();
	}

	public void ScoreResultShow() {
		DormScore dormScore  = null;
		try {
			dormScore = DormScore.fromJson(getIntent().getStringExtra("scoreResult"));
			if (dormScore==null) { //无人
				new AlertDialog.Builder(this)    
		        .setTitle("提示")
		        .setMessage("该寝室暂时无考核数据。")  
		        .setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
		        .setCancelable(false)
		        .show();
				return; //跳出
			}
		} catch(Exception e) {
			new AlertDialog.Builder(this)    
	        .setTitle("数据错误")
	        .setMessage("数据解析异常")  
	        .setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
	        .setCancelable(false)
	        .show();
			return; //跳出
		}
	   //填充
	    StringBuffer sbterm = new StringBuffer();
	    sbterm.append("学期：" + dormScore.getYear());
	    tv_term.setText(sbterm.toString());
	    
	    StringBuffer sbweek = new StringBuffer();
	    sbweek.append("周次：" + dormScore.getWeek());
	    tv_week.setText(sbweek.toString());
	   
	    
	    StringBuffer sbcheckClass = new StringBuffer();   
	    sbcheckClass.append("检查级别：" +dormScore.getCheckClass());
	    tv_checkClass.setText(sbcheckClass.toString());
	    
	    StringBuffer sbcollege = new StringBuffer();
	    sbcollege.append("学院：" + dormScore.getCollege());
	    tv_college.setText(sbcollege.toString());
	    
	    StringBuffer sbdormNum = new StringBuffer();
	    sbdormNum.append("寝室号：" + dormScore.getDormNum());
	    tv_dormNum.setText(sbdormNum.toString());
	    
	    
	    StringBuffer sbcheckType = new StringBuffer();
	    if(dormScore.getCheckType().equals("0")){
	    	sbcheckType.append("类别：" + "普查");
	        }
	    if(dormScore.getCheckType().equals("1")){
	    	sbcheckType.append("类别：" +"复查");
	        }
	     if(dormScore.getCheckType().equals("2")){
	    	 sbcheckType.append("类别：" + "抽查");
	        }
	    tv_checkType.setText(sbcheckType.toString());
	    
	    StringBuffer sbscore = new StringBuffer();
	    sbscore.append("总分：" + dormScore.getScore());
	        tv_score.setText(sbscore.toString());
	 
	        StringBuffer sblevel = new StringBuffer();
	        sblevel.append("等级：" + dormScore.getScoreLevel());
	    tv_level.setText(sblevel.toString());
	    
	    StringBuffer sbground = new StringBuffer();
	    sbground.append("地面：" + dormScore.getGround());
	    tv_ground.setText(sbground.toString());
	    
	    StringBuffer sbsmell = new StringBuffer();
	    sbsmell.append("气味：" + dormScore.getSmell());
	    tv_smell.setText(sbsmell.toString());
	    
	    StringBuffer sbwindow = new StringBuffer();
	    sbwindow.append("门窗：" + dormScore.getWindow());
	    tv_window.setText(sbwindow.toString());
	    
	    StringBuffer sbgoods = new StringBuffer();
	    sbgoods.append("物品：" + dormScore.getGoods());
	    tv_goods.setText(sbgoods.toString());
	    
	    StringBuffer sbbed = new StringBuffer();
	    sbbed.append("床铺：" + dormScore.getBed());
	    tv_bed.setText(sbbed.toString());
	    
	    StringBuffer sbtoilet = new StringBuffer();
	    sbtoilet.append("厕所：" + dormScore.getToilet());
	    tv_toilet.setText(sbtoilet.toString());
	    
	    StringBuffer sbgarbage = new StringBuffer();
	    sbgarbage.append("垃圾：" + dormScore.getGarbage());
	    tv_garbage.setText(sbgarbage.toString());
	    
	    StringBuffer sbattitude = new StringBuffer();
	    sbattitude.append("态度：" +dormScore.getAttitude());
	    tv_attitude.setText(sbattitude.toString());
	    
	    StringBuffer sbsmoker = new StringBuffer();
	    sbsmoker.append("吸烟：" + dormScore.getSmoker());
	    tv_smoker.setText(sbsmoker.toString());
	    
	    StringBuffer sbwhole = new StringBuffer();
	    sbwhole.append("整体：" +dormScore.getWhole());
	    tv_whole.setText(sbwhole.toString());
	    
	    StringBuffer sbiIIegal = new StringBuffer();
	    sbiIIegal.append("违规情况：" +dormScore.getIIIegal());
	    tv_iIIegal.setText(sbiIIegal.toString());
	    
	    StringBuffer sbremark = new StringBuffer();
        
	   sbremark.append("备注：");
	   if(!(dormScore.getIIIegalContext().equals("无")) ||dormScore.getOtherContext()!=null||dormScore.getReMark()!=null)
	   {
		   if(!(dormScore.getIIIegalContext().equals("无"))) sbremark.append(dormScore.getIIIegalContext());
    	   if(dormScore.getOtherContext()!=null) sbremark.append(dormScore.getOtherContext());
    	   if(dormScore.getReMark()!=null) sbremark.append(dormScore.getReMark());
	   }
	   else
	   {
		   sbremark.append("无");
	   }

        tv_remark.setText(sbremark.toString());

    }

    /**
     * 导航右键
     */
	
	
    @Override
    public void onNavBarRightButtonClick(View view) {
    	Intent intent = new Intent();
        intent.setClass(DormScoreShowActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
  
}
	
