package com.dwarfeng.fdr.stack.bean.dto;

/**
 * 查询分页信息对象。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface LookupPagingInfo {

	/**
	 * 是否启用分页功能。
	 * 
	 * @return 是否启用。
	 */
	public boolean isEnabled();

	/**
	 * 获取待查询的页数。
	 * 
	 * @return 待查询的页数。
	 */
	public int getPage();

	/**
	 * 获取每页的数据量。
	 * 
	 * @return 每页的数据量。
	 */
	public int getRows();

}
