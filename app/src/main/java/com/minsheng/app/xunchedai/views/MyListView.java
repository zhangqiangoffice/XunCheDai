package com.minsheng.app.xunchedai.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义的ListView，一次性全部填充
 */
public class MyListView extends ListView {
	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthSpec, expandSpec);
	}
}
