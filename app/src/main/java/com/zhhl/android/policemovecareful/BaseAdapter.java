//package com.zhhl.android.policemovecareful;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 自定义万能adapater yuan 2017.5.25
// */
//public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
//	private Context context;
//	private List<T> data;
//	private LayoutInflater layoutInflater;
//
//	public BaseAdapter(Context context, List<T> data) {
//		super();
//		setContext(context);
//		setData(data);
//		setLayoutInflater();
//	}
//
//	public Context getContext() {
//		return context;
//	}
//
//	public void setContext(Context context) {
//		if (context == null) {
//			throw new IllegalArgumentException("参数Context不允许为null！！");
//		}
//		this.context = context;
//	}
//
//	public List<T> getData() {
//		return data;
//	}
//
//	public void setData(List<T> data) {
//		if (data == null) {
//			data = new ArrayList<>();
//		}
//		this.data = data;
//	}
//	public void upData(List<T> data){
//		this.data=data;
//	}
//	protected LayoutInflater getLayoutInflater() {
//		return layoutInflater;
//	}
//
//	private void setLayoutInflater() {
//		this.layoutInflater = LayoutInflater.from(context);
//	}
//
//	@Override
//	public int getCount() {
//		return data.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//
//		return data.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//
//		return position;
//	}
//
//}
