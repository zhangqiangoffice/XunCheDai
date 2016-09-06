package com.minsheng.app.xunchedai.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 */
public class T {
	private static Toast toast;

	/**
	 * 短时间显示Toast
	 */
	public static void show(Context context, CharSequence message) {
		if (null == toast) {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/** Hide the toast, if any. */
	public static void hide() {
		if (null != toast) {
			toast.cancel();
		}
	}
}
