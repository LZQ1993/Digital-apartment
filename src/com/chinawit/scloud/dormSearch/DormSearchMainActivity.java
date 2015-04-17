package com.chinawit.scloud.dormSearch;


import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.bean.DormInfo;
import com.chinawit.scloud.bean.DormList;
import com.http.HttpUtil;
import com.json.JsonUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DormSearchMainActivity extends NavBarActivity{

	/**
	 * 广泛控件
	 */
	private Spinner spnWideDepartment;
	private Spinner spnWideMajor;
	private Spinner spnWideGrade;
	private Spinner spnWideClassAbb;
	
	/**
	 * 精确控件
	 */
	private EditText edtAimDormNum;
	private Spinner spnAimDormNum;
	private EditText edtAimStudentName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_search_main);
		initNavBar("寝室查询", "<返回", null);

		initViewPager();
	}
	
	/**
     * 初始化ViewPager
     */
    private void initViewPager() {
		 //选项卡
  		final Button btnTab01 = (Button) findViewById(R.id.dorm_search_main_btn_tab01);
  		final Button btnTab02 = (Button) findViewById(R.id.dorm_search_main_btn_tab02);
			btnTab01.setBackgroundColor(0xFF2784ea);
			btnTab01.setTextColor(0xFFFFFFFF);
			btnTab02.setBackgroundColor(0xFFFFFFFF);
			btnTab02.setTextColor(0xFF2784ea);  
		//获取两个view
		View view01 = getLayoutInflater().inflate(R.layout.activity_dorm_search_wide, null);
		View view02 = getLayoutInflater().inflate(R.layout.activity_dorm_search_aim, null);
		final List<View> views = new ArrayList<View>();
		views.add(view01);
		views.add(view02);
		//初始化vp
    	final ViewPager vp = (ViewPager) findViewById(R.id.dorm_search_main_vPager);
        vp.setAdapter(new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View v, Object o) {
				return v == o;
			}
			@Override
			public int getCount() {
				return views.size();
			}
	        @Override
	        public void destroyItem(View collection, int position, Object view) {
	        	((ViewPager) collection).removeView(views.get(position));
	        }
	        @Override
	        public Object instantiateItem(View collection, int position) {    	
	            ((ViewPager) collection).addView(views.get(position), 0);
	            return views.get(position);
	        }
		});
        vp.setCurrentItem(0);
      //初始化views
      		initWidget01(view01);
      		initWidget02(view02);
	
      		btnTab01.setOnClickListener(new View.OnClickListener() {
      			@Override
      			public void onClick(View v) {
      					btnTab01.setBackgroundColor(0xFF2784ea);
      					btnTab01.setTextColor(0xFFFFFFFF);
          				btnTab02.setBackgroundColor(0xFFFFFFFF);
          				btnTab02.setTextColor(0xFF2784ea);      				
      				vp.setCurrentItem(0, true);
      			}
      		});
      		btnTab02.setOnClickListener(new View.OnClickListener() {
      			@Override
      			public void onClick(View arg0) {
      				btnTab01.setBackgroundColor(0xFFFFFFFF);
      				btnTab01.setTextColor(0xFF2784ea);
      				btnTab02.setBackgroundColor(0xFF2784ea);
      				btnTab02.setTextColor(0xFFFFFFFF);
      				vp.setCurrentItem(1, true);
      			}
      		});
      	//滑动监听
    		vp.setOnPageChangeListener(new OnPageChangeListener() {
    			@Override
                public void onPageSelected(int position) {
    				if (position == 0) {
    					btnTab01.setBackgroundColor(0xFF2784ea);
      					btnTab01.setTextColor(0xFFFFFFFF);
          				btnTab02.setBackgroundColor(0xFFFFFFFF);
          				btnTab02.setTextColor(0xFF2784ea);
    				} 
    				else if (position == 1) {
    					btnTab01.setBackgroundColor(0xFFFFFFFF);
          				btnTab01.setTextColor(0xFF2784ea);
          				btnTab02.setBackgroundColor(0xFF2784ea);
          				btnTab02.setTextColor(0xFFFFFFFF);
    				}
                }
    	 		@Override
        		public void onPageScrollStateChanged(int index) {
        		}
        		@Override
        		public void onPageScrolled(int arg0, float arg1, int arg2) {  
        		}
    		});
        }
    /**
	 * 初始化控件
	 */
	private void initWidget01(View view) {

		spnWideDepartment = (Spinner) view.findViewById(R.id.dorm_search_wide_spn_department);
		spnWideMajor = (Spinner) view.findViewById(R.id.dorm_search_wide_spn_major);
		spnWideGrade = (Spinner) view.findViewById(R.id.dorm_search_wide_spn_grade);
		spnWideClassAbb = (Spinner) view.findViewById(R.id.dorm_search_wide_spn_classAbb);
		String[] strsDepartment = null;
		String[] strsMajor = null;
		String[] strsGrade = null;
		String[] strsClassAbb = null;
		if (Integer.valueOf(UserInfo.getUser().getPower())==1||(2<Integer.valueOf(UserInfo.getUser().getPower())&&Integer.valueOf(UserInfo.getUser().getPower())<6)) {
			strsDepartment = new String[] { //限制为本院
				"未指定",
				UserInfo.getUser().getCollege()
			};
		} else { //全校
			strsDepartment = new String[] {
				"未指定",		
				"电子与信息工程学院",
				"安全科学与工程学院",
				"电气与控制工程学院",
				"工商管理学院",
				"矿业技术学院",
				"营销学院",
				"软件学院"
			};
		}
		
		strsMajor = new String[] {"全部"};
		Time t = new Time();
		t.setToNow();
		strsGrade = new String[6];
		strsGrade[0] = "全部";
		for (int n = 1; n < strsGrade.length; n++) {
			strsGrade[n] = (t.year - n + 1) + "";
		}
		strsClassAbb = new String[] {"全部"};
		ArrayAdapter<String> adpDepartment = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strsDepartment);  
		ArrayAdapter<String> adpMajor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strsMajor);
		ArrayAdapter<String> adpGrade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strsGrade);  
		ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strsClassAbb);  
		
		
		adpDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adpMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adpGrade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spnWideDepartment.setAdapter(adpDepartment);
		spnWideMajor.setAdapter(adpMajor);
		spnWideGrade.setAdapter(adpGrade);
		spnWideClassAbb.setAdapter(adpClassAbb);

		spnWideDepartment.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position != 0) {
					getMajorByDepartmentTask();
				} else {
	            	//清除专业
	        		String[] strsMajor = new String[] {"全部"};
	        		ArrayAdapter<String> adpMajor = new ArrayAdapter<String>(DormSearchMainActivity.this, android.R.layout.simple_spinner_item, strsMajor);
	        		adpMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        		spnWideMajor.setAdapter(adpMajor);
	        		//清除班级
	        		String[] strsClassAbb = new String[] {"全部"};
	        		ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(DormSearchMainActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);
	        		adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        		spnWideClassAbb.setAdapter(adpClassAbb);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		spnWideMajor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (spnWideDepartment.getSelectedItemPosition() != 0) {
					getClassByConditionTask();
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		spnWideGrade.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (spnWideDepartment.getSelectedItemPosition() != 0) {
					getClassByConditionTask();
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	

	/**
	 * 通过学院获取专业
	 */
	public void getMajorByDepartmentTask() {	
		//showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "GetProfessionalList.ashx";
		RequestParams params = new RequestParams();
		params.put("College", spnWideDepartment.getSelectedItem().toString());		
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
                dismissProgressDialog();
            	new AlertDialog.Builder(DormSearchMainActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
            	//清除学院
            	spnWideDepartment.setSelection(0);
			}

			@Override
			public void onSuccess(String content) {
                //dismissProgressDialog();
                String[] tempsMajor = null;
                try {
                	tempsMajor =JsonUtil.fromJson(content, String[].class);
                } catch (Exception e) {
	            	new AlertDialog.Builder(DormSearchMainActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
	                .setPositiveButton("确定", null)
	                .show();
	            	//清除学院
	            	spnWideDepartment.setSelection(0);
                	return;
                }
    			//访问成功
                
                String[] strsMajor = new String[tempsMajor.length + 1];
                strsMajor[0] = "全部";
                for (int n=0; n < tempsMajor.length; n++) {
                	strsMajor[n+1] = tempsMajor[n];
                }
        		ArrayAdapter<String> adpMajor = new ArrayAdapter<String>(DormSearchMainActivity.this, android.R.layout.simple_spinner_item, strsMajor);
        		adpMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		spnWideMajor.setAdapter(adpMajor);
        		//获取班级
        		getClassByConditionTask();
			}

		});
	}

	/**
	 * 通过条件获取班级
	 */
	public void getClassByConditionTask() {		
		//showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "GetClassList.ashx";
		RequestParams params = new RequestParams();
		if (spnWideDepartment.getSelectedItemPosition() == 0) params.put("College", "");
		else params.put("department", spnWideDepartment.getSelectedItem().toString());
		if (spnWideMajor.getSelectedItemPosition() == 0) params.put("Professional", "");
		else params.put("Professional", spnWideMajor.getSelectedItem().toString());
		if(spnWideGrade.getSelectedItemPosition() == 0) params.put("AcademicYear", "");
		else params.put("AcademicYear", spnWideGrade.getSelectedItem().toString());
		//params.put("IsStop", UserInfo.getUser().getIsStop()); //权限
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
              //  dismissProgressDialog();
            	new AlertDialog.Builder(DormSearchMainActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！"+error.toString())  
                .setPositiveButton("确定",null)
                .show();
        		//清除班级
        		String[] strsClassAbb = new String[] {"无数据"};
        		ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(DormSearchMainActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);
        		adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		spnWideClassAbb.setAdapter(adpClassAbb);
			}

			@Override
			public void onSuccess(String content) {
                dismissProgressDialog();
                String[] tempsClassAbb = null;
                try {
                	tempsClassAbb = JsonUtil.fromJson(content, String[].class);
                } catch (Exception e) {
	            	new AlertDialog.Builder(DormSearchMainActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
	                .setPositiveButton("确定", null)
	                .show();
	        		//清除班级
	        		String[] strsClassAbb = new String[] {"无数据"};
	        		ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(DormSearchMainActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);
	        		adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        		spnWideClassAbb.setAdapter(adpClassAbb);
                	return;
                }
    			//访问成功
                String[] strsClassAbb = new String[tempsClassAbb.length + 1];
                strsClassAbb[0] = "全部";
                for (int n=0; n < tempsClassAbb.length; n++) {
                	strsClassAbb[n+1] = tempsClassAbb[n];
                }
        		ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(DormSearchMainActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);  
        		adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		spnWideClassAbb.setAdapter(adpClassAbb);
			}
		});
	}


	/**
	 * 提交-条件搜索
	 */
	public void onSubmitWide(View view) {
		if (spnWideDepartment.getSelectedItemPosition() == 0) {
        	new AlertDialog.Builder(DormSearchMainActivity.this)    
            .setTitle("提示")  
            .setMessage("学院条件未指定！\n您必须指定一个学院条件")  
            .setPositiveButton("确定", null)
            .show(); 
		} else {
			getDormByConditionTask();
		}
	}

	/**
	 * 通过条件获取寝室列表
	 */
	public void getDormByConditionTask() {
		showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "GetDormNumByCollegeAndAll.ashx";
		RequestParams params = new RequestParams();
		if (Integer.valueOf(UserInfo.getUser().getPower())>5&&spnWideDepartment.getSelectedItemPosition() == 0) params.put("College", "");
		else params.put("College", spnWideDepartment.getSelectedItem().toString());
		if (spnWideMajor.getSelectedItemPosition() == 0) params.put("Professional", "");
		else params.put("Professional", spnWideMajor.getSelectedItem().toString());
		if (spnWideGrade.getSelectedItemPosition() == 0) params.put("AcademicYear", "");
		else params.put("AcademicYear", spnWideGrade.getSelectedItem().toString());
		if (spnWideClassAbb.getSelectedItemPosition() == 0) params.put("Class", "");
		else params.put("Class", spnWideClassAbb.getSelectedItem().toString());
		//params.put("IsStop", UserInfo.getUser().getIsStop());
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
                dismissProgressDialog();
            	new AlertDialog.Builder(DormSearchMainActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
			}

			@Override
			public void onSuccess(String content) {
                dismissProgressDialog();
                //解析数据
                String[] strsDorm = null;
                try {
                	strsDorm = JsonUtil.fromJson(content, String[].class);
                } catch (Exception e) {
	            	new AlertDialog.Builder(DormSearchMainActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
	                .setPositiveButton("确定", null)
	                .show();
                	return;
                }
                //如果为空
    			if(strsDorm == null || strsDorm.length == 0) {
	            	new AlertDialog.Builder(DormSearchMainActivity.this)    
	                .setTitle("提示")  
	                .setMessage("没有相关信息！")  
	                .setPositiveButton("确定",null)
	                .show();
	            	return;
    			}
                //访问成功
                Intent intent = new Intent();
                intent.putExtra("strsDorm", strsDorm);
                intent.setClass(DormSearchMainActivity.this, DormGridActivity.class);
                startActivity(intent);
			}
		});
	}

    /**
	 * 初始化控件
	 */
	private void initWidget02(View view) {
		edtAimDormNum = (EditText) view.findViewById(R.id.dorm_search_aim_edt_dormNum);
		edtAimStudentName = (EditText) view.findViewById(R.id.dorm_search_aim_edt_studentName);
		spnAimDormNum = (Spinner) view.findViewById(R.id.dorm_search_aim_spn_dormNum);
	}
	/**
	 * 提交-寝室号码
	 */
	public void onSubmitAimDormNum(View view) {
		if (edtAimDormNum.getText().toString().equals("")) {
        	new AlertDialog.Builder(DormSearchMainActivity.this)    
            .setTitle("提示")  
            .setMessage("您未填写寝室号码！\n您必须填写一个寝室号码\n例如：A1-101")  
            .setPositiveButton("确定",null)
            .show(); 
		} else {
			getDormPlateByDormNumTask(spnAimDormNum.getSelectedItem().toString() + "-" + edtAimDormNum.getText().toString());
		}
	}
	/**
	 * 通过寝室号获取门牌
	 */
	public void getDormPlateByDormNumTask(String dormNum) {
		final String DormNum = spnAimDormNum.getSelectedItem().toString().trim() + "-" + edtAimDormNum.getText().toString().trim();
		showProgressDialog();		
		String url = NetworkInfo.getServiceUrl() + "GetDormPlateByDormNum.ashx";
		RequestParams params = new RequestParams();
		params.put("DormNum",DormNum);
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
                dismissProgressDialog();
            	new AlertDialog.Builder(DormSearchMainActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
			}

			@Override
			public void onSuccess(String content) {
                dismissProgressDialog();
    			//解析数据
    			DormInfo dormInfo = null;
                try {
                	
                	dormInfo = DormInfo.fromJson(content);

                } catch (Exception e) {
	            	new AlertDialog.Builder(DormSearchMainActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
	                .setPositiveButton("确定", null)
	                .show();
                	return;
                }
                //数据为空
    			if (dormInfo == null ||dormInfo.getDormNum() == null || dormInfo.getDormNum().equals("")) {
	            	new AlertDialog.Builder(DormSearchMainActivity.this)    
	                .setTitle("提示")  
	                .setMessage("未获取到寝室信息\n或该寝室不存在！")  
	                .setPositiveButton("确定",null)
	                .show();
	            	return;
    			}		
    			//权限充足
                Intent intent = new Intent();
                intent.putExtra("jsonDormPlate",dormInfo.toJson());
                intent.setClass(DormSearchMainActivity.this, DormInfoActivity.class);
                startActivity(intent);
			}
		});
	}
	   
	
	/**
	 * 提交-姓名搜索
	 */
	public void onSubmitAimStudentName(View view) {
		if (edtAimStudentName.getText().toString().equals("")) {
        	new AlertDialog.Builder(DormSearchMainActivity.this)    
            .setTitle("提示")  
            .setMessage("您未填写学生姓名！\n您必须填写一个学生姓名\n例如：张三")  
            .setPositiveButton("确定",null)
            .show();
		} else {
			getDormByStudentNameTask(edtAimStudentName.getText().toString());
		}
	}
	/**
	 * 通过姓名获取寝室列表
	 */
	public void getDormByStudentNameTask(String name) {
		showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "GetDormNumsByStudentName.ashx";
		RequestParams params = new RequestParams();		
		params.put("StudentName",edtAimStudentName.getText().toString());
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
                dismissProgressDialog();
            	new AlertDialog.Builder(DormSearchMainActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
			}

			@Override
			public void onSuccess(String content) {
                dismissProgressDialog();
                //解析数据
                DormList Dormlist = null;
                try { 
                	Dormlist = DormList.fromJson(content);
                } catch (Exception e) {
	            	new AlertDialog.Builder(DormSearchMainActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
	                .setPositiveButton("确定", null)
	                .show();
                	return;
                }                
                //无数据
                if (Dormlist == null || Dormlist.getDormlistCount() == 0) {
	            	new AlertDialog.Builder(DormSearchMainActivity.this)    
	                .setTitle("提示")  
	                .setMessage("没有与该学生相关的寝室信息！")  
	                .setPositiveButton("确定",null)
	                .show(); 
    				return;
                }
                //访问成功
                Intent intent = new Intent();
                intent.putExtra("JsonDormlist", Dormlist.toJson());
                intent.setClass(DormSearchMainActivity.this,DormNameActivity.class);
                startActivity(intent);
			}
		});
	}
	/* *//**
     * 导航右键
     *//*
    @Override
    public void onNavBarLeftButtonClick(View view) {
    	 onExit();
    }
    
    *//**
     * 返回键
     *//*
    @Override
    public void onBackPressed() {
        onExit();
    }
    
    *//**
     * 退出
     *//*
    public void onExit() {
        new AlertDialog.Builder(this)
        .setTitle("提示：退出寝室考核")
        .setMessage("您确定要退出寝室查询模块吗？")
        .setPositiveButton("确定",new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .setNegativeButton("取消", null)
        .show();
    }
	*/

	

}
