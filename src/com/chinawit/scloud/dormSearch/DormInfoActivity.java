package com.chinawit.scloud.dormSearch;


import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chinawit.scloud.LoginActivity;
import com.chinawit.scloud.MainActivity;
import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.bean.DormInfo;
import com.chinawit.scloud.bean.RoomMate;
import com.chinawit.scloud.bean.Student;
import com.chinawit.scloud.bean.TeacherCode;
import com.chinawit.scloud.dormScore.DormScoreShowActivity;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DormInfoActivity extends NavBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_info);
		initNavBar("寝室信息", "<返回","首页");
	
		//获取信息
				DormInfo dormInfo = null;
				try {
					dormInfo = DormInfo.fromJson(getIntent().getStringExtra("jsonDormPlate"));
					if (dormInfo.getRoomMateCount() == 0) { //无人
						new AlertDialog.Builder(this)    
				        .setTitle("提示")
				        .setMessage("该寝室暂时无人居住。")  
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
				//初始化ListView
				ListView lvMates = (ListView) findViewById(R.id.dorm_info_lv_mates);
				lvMates.setAdapter(new ListViewAdapter(this, dormInfo));
				lvMates.setOnItemClickListener(new ListViewItemClickListener(dormInfo));	
			}
			
			/**
		     * 适配器内部类
		     */
		    private class ListViewAdapter extends BaseAdapter {

		        private List<View> itemViews;

		        public ListViewAdapter(Context context, DormInfo dormInfo) {
		            //初始化一个布局加载器
		            LayoutInflater inflater = LayoutInflater.from(context);
		            //创建一个自相布局数组
		            itemViews = new ArrayList<View>();
		            for (int n=0; n<dormInfo.getRoomMateCount(); n++) {
		            	RoomMate mate = dormInfo.getRoomMate().get(n);
		                View itemView = inflater.inflate(R.layout.activity_dorm_info_lv_mates_item, null);
		                //初始化子布局
		              /*  TextView tvBed = (TextView) itemView.findViewById(R.id.dorm_info_lv_mates_item_tv_bed);
		                tvBed.setText(mate.getStudentBedNum());*/
		                TextView tvName = (TextView) itemView.findViewById(R.id.dorm_info_lv_mates_item_tv_name);
		                tvName.setText(mate.getStudentName());      
		                TextView tvPost = (TextView) itemView.findViewById(R.id.dorm_info_lv_mates_item_tv_post);
		                tvPost.setText(mate.getPost()); 
		                TextView tvClassInfo = (TextView) itemView.findViewById(R.id.dorm_info_lv_mates_item_tv_class_info);
		                tvClassInfo.setText(mate.getAbbreviation());
		                TextView tvFace = (TextView) itemView.findViewById(R.id.dorm_info_lv_mates_item_tv_face);
		                if(mate.getFace()==null||mate.getFace().equals("")){
		                	tvFace.setText("无");
		                }else{
		                tvFace.setText(mate.getFace());
		                }
		                TextView tvTeacher = (TextView) itemView.findViewById(R.id.dorm_info_lv_mates_item_tv_teacher);
		                tvTeacher.setText(mate.getTeacherName());
		                
		                //添加到布局中
		                itemViews.add(itemView);
		            }
		            
		        }

		        @Override
		        public int getCount() {
		            return itemViews.size();
		        }

		        @Override
		        public Object getItem(int position) {
		            return itemViews.get(position);
		        }

		        @Override
		        public long getItemId(int position) {
		            return itemViews.get(position).getId();
		        }

		        @Override
		        public View getView(int position, View convertView, ViewGroup parent) {
		            return itemViews.get(position);
		        }

		    }

		    /**
		     * 监听器内部类
		     */
		    private class ListViewItemClickListener implements OnItemClickListener {

		    	private DormInfo dormInfo;
		    	
				public ListViewItemClickListener(DormInfo dormInfo) {
					this.dormInfo = dormInfo;
				}

				@Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//用户未登录
					if (UserInfo.getUser() == null) {
						new AlertDialog.Builder(DormInfoActivity.this)    
		                .setTitle("提示")  
		                .setMessage("该功能模块，仅特定权限账户才能使用。您未登录账户。\n您确定要登录账户？")  
		                .setPositiveButton("确定",new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent();
								intent.setClass(DormInfoActivity.this, LoginActivity.class);
								startActivity(intent);
							}
						})
		                .setNegativeButton("取消",null)
		                .show();
						return;
					}
					//权限不足
					if (Integer.valueOf(UserInfo.getUser().getPower())<=2) {
							new AlertDialog.Builder(DormInfoActivity.this)    
			                .setTitle("提示")  
			                .setMessage("查看权限不足！\n该寝室不属于您所在的查看权限范围内，您可以尝试与管理员联系，以便获得更多的授权。")  
			                .setPositiveButton("确定",null)
			                .show();
			            	return;
					}
					if (2<Integer.valueOf(UserInfo.getUser().getPower())&&Integer.valueOf(UserInfo.getUser().getPower())<6&&!(dormInfo.getCollege().equals(UserInfo.getUser().getCollege()))) {
		            	new AlertDialog.Builder(DormInfoActivity.this)    
		                .setTitle("提示")  
		                .setMessage("查看权限不足！\n您当前的查看权限为：\n【"+UserInfo.getUser().getCollege()+"】\n该寝室不属于您所在的查看权限范围内，您可以尝试与管理员联系，以便获得更多的授权。")  
		                .setPositiveButton("确定",null)
		                .show();
		            	return;
					}
					//权限充足
				   final RoomMate mate = dormInfo.getRoomMate().get(position);
				   new  AlertDialog.Builder(DormInfoActivity.this)  
					.setTitle("请选择" )   
					.setIcon(android.R.drawable.ic_dialog_info)                  
					.setSingleChoiceItems(new  String[] {"学生基本信息", "老师基本信息" },0,  
				    
					 new DialogInterface.OnClickListener() {                                
					 public   void  onClick(DialogInterface dialog, int  which) {  
						 switch(which)
						 {
						 case 0: getStudentByNameAndBedNumTask(mate.getStudentNum(),mate.getStudentName());
						 		 dialog.dismiss();
						 		 break;
						 case 1: 
						 		getTeacherByNameAndAbbreviation(mate.getTeacherName(),mate.getAbbreviation());
								 dialog.dismiss();
								 break;
						 }
						 
					     }  
					  }   
					)   
					.setNegativeButton("取消" ,  null )  
					.show();  
			
		        }

				private void setNegativeButton() {
					
					finish();
					
				}

}
  
   
   /**
	 * 根据学号查学生详细信息
	 */
	public void getStudentByNameAndBedNumTask(String StudentNum,String StudentName) {	
		showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "GetStudentInfo.ashx";
		RequestParams params = new RequestParams();
		params.put("StudentNum",StudentNum);
		//params.put("power", power + "");
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
                dismissProgressDialog();
            	new AlertDialog.Builder(DormInfoActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
			}

			@Override
			public void onSuccess(String content) {
                dismissProgressDialog();
                //解析数据
                Student student = null;
                try {
                	student = Student.fromJson(content);
                } catch (Exception e) {
	            	new AlertDialog.Builder(DormInfoActivity.this)    
	                .setTitle("错误")  
	                .setMessage("服务器返回：" + content)  
	                .setPositiveButton("确定", null)
	                .show();
                	return;
                }
                //数据为空
    			if (student == null) {
	            	new AlertDialog.Builder(DormInfoActivity.this)    
	                .setTitle("提示")  
	                .setMessage("暂无该学生详细信息。")  
	                .setPositiveButton("确定",null)
	                .show();
	            	return;
    			}
				//取回成功
				Intent intent = new Intent();
				intent.setClass(DormInfoActivity.this, StudentInfoActivity.class);
				intent.putExtra("jsonStudent", student.toJson());
				startActivity(intent);
			}
		});
		
	}
	/*
	 * 根据姓名专业查看老师详细信息
	 */
	public void getTeacherByNameAndAbbreviation(String teacherName,String Abbreviation) {
		
		showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "GetTeacherInfoByNameAndClassAbb.ashx";
		RequestParams params = new RequestParams();
		params.put("TeacherName", teacherName);
		params.put("ClassAbb",Abbreviation);
		//params.put("power", power + "");
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
                dismissProgressDialog();
            	new AlertDialog.Builder(DormInfoActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
			}

			@Override
			public void onSuccess(String content) {
                dismissProgressDialog();
                //解析数据
                TeacherCode TeacherCode = null;
                try {
                	TeacherCode = TeacherCode.fromJson(content);
                } catch (Exception e) {
	            	new AlertDialog.Builder(DormInfoActivity.this)    
	                .setTitle("错误")  
	                .setMessage("服务器返回：" + content)  
	                .setPositiveButton("确定", null)
	                .show();
                	return;
                }
                //数据为空
    			if (TeacherCode == null) {
	            	new AlertDialog.Builder(DormInfoActivity.this)    
	                .setTitle("提示")  
	                .setMessage("暂无该老师详细信息。")  
	                .setPositiveButton("确定",null)
	                .show();
	            	return;
    			}
				//取回成功
				Intent intent = new Intent();
				intent.setClass(DormInfoActivity.this, TeacherInfoActivity.class);
				intent.putExtra("jsonTeacher", TeacherCode.toJson());
				startActivity(intent);
			}
		});
		
	} 
	   
    /**
     * 导航右键
     */
	
	
    @Override
    public void onNavBarRightButtonClick(View view) {
    	Intent intent = new Intent();
        intent.setClass(DormInfoActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    
  

}
