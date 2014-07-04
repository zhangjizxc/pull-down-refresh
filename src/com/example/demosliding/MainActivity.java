package com.example.demosliding;

import com.example.demosliding.PullToRefresh.MyScrollListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements MyScrollListener {

	// EntryScrollView entryScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PullToRefresh pullToRefresh = (PullToRefresh) findViewById(R.id.main_view);
		View viewTop = LayoutInflater.from(this).inflate(R.layout.top, null);
		View viewContent = LayoutInflater.from(this).inflate(R.layout.content, null);

//		viewContent.findViewById(R.id.tv_id1).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "AAAAAAAAA", Toast.LENGTH_SHORT).show();
//			}
//		});
		pullToRefresh.setTopView(viewTop);
		pullToRefresh.setContentView(viewContent);
		pullToRefresh.setScrollViewListener(this);
		// 把这些东西都写在PullToRefresh里面
//		entryScrollView = pullToRefresh.getMyScrollView();
//		entryScrollView.setScrollViewListener(new ScrollViewListener() {
//
//			@Override
//			public void onTouchEvent(MotionEvent ev) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onScrollChanged(EntryScrollView scrollView, int x, int y, int oldx, int oldy) {
//
//			}
//
//			@Override
//			public void onInterceptTouchEvent(MotionEvent ev) {
//				// TODO Auto-generated method stub
//
//			}
//		});
	}

	@Override
	public void onScrollChanged(EntryScrollView scrollView, int x, int y, int oldx, int oldy) {
		Log.e("pengpeng", "11y = " + y);

	}

}
