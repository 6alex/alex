package com.wen.utils;

import java.util.Map;

public class MyPage {
	// ----------分页-----------
	private int pageSize;// 每页显示条数
	private int pageCurrentPage;// 第几页
	private int pageBegin;// 开始位置
	private int numCount;// 总条数
	private int pageTotal;// 总条数

	private String orderField = "";// 控制排序页面显示的
	private String orderDirection = "";

	public MyPage() {

	}

	public MyPage(int pageSize, int pageCurrentPage) {
		super();
		this.pageSize = pageSize;
		this.pageCurrentPage = pageCurrentPage;
	}

	public MyPage(Map<String, String> map) {
		if (map.get("pageNum") != null) {
			this.setPageCurrentPage(this.pageCurrentPage = Integer.parseInt(map.get("pageNum")));// 要查询的页数
		} else {
			this.setPageCurrentPage(1);// 设置初始值
		}

		if (map.get("numPerPage") != null) {
			this.setPageSize(Integer.parseInt(map.get("numPerPage")));// 每页显示条数
		} else {
			this.setPageSize(5);// 设置初始值
		}

		if (map.get("orderField") != null) {
			this.setOrderField(map.get("orderField"));
		}

		if (map.get("orderDirection") != null) {
			this.setOrderDirection(map.get("orderDirection"));
		}

	}

	public int getPageCurrentPage() {
		return pageCurrentPage;
	}

	public void setPageCurrentPage(int pageCurrentPage) {
		this.pageCurrentPage = pageCurrentPage;
	}

	public int getNumCount() {
		return numCount;
	}

	public void setNumCount(int numCount) {
		this.numCount = numCount;
	}

	public int getPageTotal() {
		return (numCount % pageSize > 0) ? (numCount / pageSize + 1) : (numCount / pageSize);
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageBegin() {
		return pageSize * (pageCurrentPage - 1);
	}

	public void setPageBegin(int pageBegin) {
		this.pageBegin = pageBegin;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public static MyPage getPage(int pageSize, int pageCurrentPage) {
		return new MyPage(pageSize, pageCurrentPage);
	}

	public static MyPage getPage(Map map) {
		return new MyPage(map);
	}

}
