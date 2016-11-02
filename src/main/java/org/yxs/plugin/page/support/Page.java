package org.yxs.plugin.page.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 功能描述：分页工具类
 * author 湛智
 * 时间：2014年9月16日
 */
public class Page<E> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long DEFAULT_PAGE_SIZE = 10L;
	
	private long pageSize = DEFAULT_PAGE_SIZE;//一页显示多少条
	
	private long nextPage = 1;//第几页  初始为1

	private long backPage = this.nextPage - 1;//上一页 初始为
	
	private long rowCount;//需要分页的总记录数

	private long homePage = 1;//首页 初始为1

	private long maxPage;//最大页数
	
	private long pageNum;//从第几条开始查询分页

	private boolean isHome;//是否首页

	private boolean isLast;//是否尾页

	private List<E> list;//分页内容
	
	public Page() {}

	/**
	 * 计算分页逻辑(默认一页显示条数)
	 * @param rowCount 总记录数
	 * @param nextPage 第几页
	 */
	public Page(long rowCount, long nextPage) {
		this(rowCount, nextPage, DEFAULT_PAGE_SIZE);
	}

	public Page(RowBounds rowBounds) {
		this(rowBounds.getRowCount(), rowBounds.getNextPage(), rowBounds.getPageSize());
	}

	/**
	 * 计算分页逻辑(自定义一页显示条数)
	 * @param rowCount 总记录数
	 * @param nextPage 第几页
	 */
	public Page(long rowCount, long nextPage, long pageSize) {
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.list = new ArrayList<>();
		
		//计算最大页
		this.maxPage = rowCount % this.pageSize == 0 ? rowCount / this.pageSize : rowCount / pageSize + 1;
		
		//计算当前需要从第几条开始分页
		this.nextPage = nextPage < 1 ? this.nextPage = 1 : nextPage > this.maxPage ? this.maxPage : nextPage;

		//上一页
		this.backPage = this.nextPage - 1;

		//首页
		this.homePage = 1;

		//得到分页开始的记录数
		this.pageNum = (this.nextPage - 1) * pageSize;
		
		//开始的记录数为负数则设为0
		this.pageNum = this.pageNum >= 0 ? this.pageNum : 0;

		//判断是否为首页
		this.isHome = this.nextPage == 1;

		//判断是否为尾页
		this.isLast = this.nextPage >= this.maxPage;
	}

	public static RowBounds rowBounds(long nextPage, long pageSize, Object object) {
		return new RowBounds(nextPage, pageSize, object);
	}

	public static RowBounds rowBounds(long nextPage, Object object) {
		return rowBounds(nextPage, DEFAULT_PAGE_SIZE, object);
	}

	public static RowBounds rowBounds(long nextPage) {
		return rowBounds(nextPage, DEFAULT_PAGE_SIZE, null);
	}

	public static <E> Page<E> asPage(RowBounds rowBounds) {
		return new Page<>(rowBounds);
	}

	public static <E> Page<E> asPage(RowBounds rowBounds, Collection<? extends E> collection) {
		Page<E> page = new Page<>(rowBounds);
		page.adds(null == collection ? Collections.<E>emptyList() : collection);
		return page;
	}

	public Page<E> adds(Collection<? extends E> collection) {
		this.getList().addAll(collection);
		return this;
	}

	public Page<E> add(E e) {
		this.getList().add(e);
		return this;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getNextPage() {
		return nextPage;
	}

	public void setNextPage(long nextPage) {
		this.nextPage = nextPage;
	}

	public long getBackPage() {
		return backPage;
	}

	public void setBackPage(long backPage) {
		this.backPage = backPage;
	}

	public long getHomePage() {
		return homePage;
	}

	public void setHomePage(long homePage) {
		this.homePage = homePage;
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public long getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(long maxPage) {
		this.maxPage = maxPage;
	}

	public long getPageNum() {
		return pageNum;
	}

	public void setPageNum(long pageNum) {
		this.pageNum = pageNum;
	}

	public boolean isHome() {
		return isHome;
	}

	public void setHome(boolean home) {
		isHome = home;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean last) {
		isLast = last;
	}

	public List<E> getList() {
		return list;
	}
}
