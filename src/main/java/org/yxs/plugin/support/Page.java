package org.yxs.plugin.support;

import java.io.Serializable;
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
	
	private long pageSize = 10;//一页显示多少条
	
	private long nextPage = 1;//第几页  初始为1
	
	private long pageCount;//需要分页的总记录数
	
	private long maxPage;//最大页数
	
	private long pageNum;//从第几条开始查询分页
	
	private List<E> results;//分页返回的对象
	
	public Page() {}

	/**
	 * 计算分页逻辑(默认一页显示条数)
	 * @param pageCount 总记录数
	 * @param nextPage 第几页
	 */
	public Page(long pageCount, long nextPage) {
		this.pageCount = pageCount;
		
		//计算最大页
		this.maxPage = pageCount % this.pageSize == 0 ? pageCount / this.pageSize : pageCount / pageSize + 1;
		
		//计算当前需要从第几条开始分页
		this.nextPage = nextPage < 1 ? this.nextPage = 1 : nextPage > this.maxPage ? this.maxPage : nextPage;
		
		//得到分页开始的记录数
		this.pageNum = (this.nextPage - 1) * pageSize;
		
		//开始的记录数为负数则设为0
		this.pageNum = this.pageNum >= 0 ? this.pageNum : 0;
	}
	
	/**
	 * 计算分页逻辑(自定义一页显示条数)
	 * @param pageCount 总记录数
	 * @param nextPage 第几页
	 */
	public Page(long pageCount, long nextPage, long pageSize) {
		this.pageSize = pageSize;
		this.pageCount = pageCount;
		
		//计算最大页
		this.maxPage = pageCount % this.pageSize == 0 ? pageCount / this.pageSize : pageCount / pageSize + 1;
		
		//计算当前需要从第几条开始分页
		this.nextPage = nextPage < 1 ? this.nextPage = 1 : nextPage > this.maxPage ? this.maxPage : nextPage;
		
		//得到分页开始的记录数
		this.pageNum = (this.nextPage - 1) * pageSize;
		
		//开始的记录数为负数则设为0
		this.pageNum = this.pageNum >= 0 ? this.pageNum : 0;
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

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
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

	public List<E> getResults() {
		return results;
	}

	public void setResults(List<E> results) {
		this.results = results;
	}
}
