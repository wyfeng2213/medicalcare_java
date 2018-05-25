package com.cmcc.medicalcare.inter;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * @author mikel
 * @date 2013-7-16 上午9:15:43
 * @description mybatis分页辅助类
 */
public class Pager<T> extends RowBounds{
	/**
	 * DEFAULT_PAGE_SIZE
	 */
	public static final int DEFAULT_PAGE_SIZE = 10; // 每页显示行数
	/**
	 * DEFAULT_PAGE_INDEX
	 */
	public static final int DEFAULT_PAGE_INDEX = 1; // 默认显示第1页
	/**
	 * totalCount
	 */
	private int totalCount;//总记录数
	/**
	 * pageIndex
	 */
	private int pageIndex = DEFAULT_PAGE_INDEX;//当前页码
	/**
	 * pageSize
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;//每页记录数
	/**
	 * totalPage
	 */
	private int totalPage;//总页数
	/**
	 * list
	 */
	private List<T> list ;//查询结果集

	//是否进行分页，底层使用，业务层请勿设置
	private boolean queryAll = false;
	/**
	 * getTotalCount
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * setTotalCount
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if(totalCount == 0) pageIndex = 1;
		totalPage = (int)Math.ceil((totalCount * 1.0f / pageSize));
	}
	/**
	 * getPageIndex
	 * @return
	 */
	public int getPageIndex() {
		return pageIndex;
	}
	/**
	 * setPageIndex
	 * @param pageIndex
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	/**
	 * getPageSize
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * setPageSize
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * getTotalPage
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}
	@Override
	public int getLimit() {
		if(queryAll)
			return super.getLimit();
		return pageSize;
	}
	@Override
	public int getOffset() {
		if(queryAll){
			return super.getOffset();
		}
		return (pageIndex - 1) * pageSize;
	}
	
	/**
	 * @return the queryAll
	 */
	public boolean isQueryAll() {
		return queryAll;
	}
	
	/**
	 * @param queryAll the queryAll to set
	 */
	public void setQueryAll(boolean queryAll) {
		this.queryAll = queryAll;
	}
	/**
	 * getList
	 * @return
	 */
	public List<T> getList() {
		return list;
	}
	/**
	 * setList
	 * @param list
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
	/**
	 * 该页是否有下一页.
	 */
	public boolean getHasNextPage() {
		return pageIndex < totalPage;
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean getHasPreviousPage() {
		return pageIndex > 1;
	}
	
}
