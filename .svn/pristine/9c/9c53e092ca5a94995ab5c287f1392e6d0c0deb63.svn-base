package com.minsheng.app.xunchedai.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//网络管理类
public class NetWorkUtils {

	public NetWorkUtils(){}

	/**
	 * 网络是否可用
	 * @param
	 * @return
	 */
	public static boolean isNetworkAvailable(Activity activity)
	{
		Context context = activity.getApplicationContext();
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null)
		{
			return false;
		}
		else
		{
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0)
			{
				for (int i = 0; i < networkInfo.length; i++)
				{
					System.out.println(i + "===状态===" + networkInfo[i].getState());
					System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}
				}
			}
		}
		return false;
	}


	/**
	 *无网的情况下的提示对话框
	 * @param context
	 */
	public static void noNetDialog(final Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("温馨提示");
		builder.setMessage("没有可用的网络.");
		builder.setPositiveButton("设置网络", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which){
				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
				context.startActivity(intent);
				return;
			}
		});
		builder.create().show();
	}

	/**
	 *服务器后台错误提示框
	 * @param context
	 */
	public static void MessageDialog(final Context context,String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("温馨提示");
		builder.setMessage(message);
		builder.create().show();
	}

}
