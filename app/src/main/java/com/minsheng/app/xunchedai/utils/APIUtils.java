package com.minsheng.app.xunchedai.utils;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minsheng.app.xunchedai.home.bean.Scheme;

import java.util.List;

/**
 * 共用的API
 */
public class APIUtils {

	public static HttpUtils httpUtils = new HttpUtils();
	public static List<Scheme> list_scheme;
	public static String str;
	/**
	 * 获取分期方案
	 */
	public static void getScheme(final Activity activity) {
		if (NetWorkUtils.isNetworkAvailable(activity)) {
			httpUtils.send(HttpRequest.HttpMethod.POST, WebServiceUrl.WEBSERVICE_URL + WebServiceUrl.SCHEME_URL, null, new RequestCallBack<String>() {
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String json = responseInfo.result;
					int result = -1;
					JSONObject obj = JSON.parseObject(json);
					result = obj.getInteger("result");
					String message=obj.getString("message");

					if (result == 1) {
						JSONArray array = obj.getJSONArray("scheme");
						list_scheme = JSON.parseArray(array.toJSONString(), Scheme.class);
					} else {
						T.show(activity, message);
					}
				}

				@Override
				public void onFailure(HttpException e, String s) {
					T.show(activity, "服务器错误：" + s);
				}
			});

		} else {
			NetWorkUtils.noNetDialog(activity);
		}
	}


}
