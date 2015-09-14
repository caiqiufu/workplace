package com.unieap.base.vo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.unieap.base.SYSConfig;
import com.unieap.tools.JSONUtils;

public class PaginationSupport extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** only for extjs ********************************/
	private String page;
	private String start;
	private String limit;
	private String sort;
	private String dir;
	/***********************************************/
	private int pageSize = 15;
	private int currentPage = 1;
	private int startIndex = 1;
	@SuppressWarnings("unchecked")
	private List items;
	private int totalCount = 0;
	private String jsonString = "";
	private String dsName = null;
	/**
	 * sort ASC
	 */
	public  String ASC = "ASC";
	/**
	 * sort DESC
	 */
	public  String DESC = "DESC";

	public String getJsonString() throws Exception {
		if (StringUtils.isEmpty(jsonString) && this.items != null) {
			JSONObject data = new JSONObject();
			try {
				data.put("totalCount", this.getTotalCount());
				JSONArray ja = JSONUtils.getJSONArray(this.items);
				data.put("rows", ja);
			} catch (Exception e) {
				throw new Exception("items data conver to json error,error:"
						+ e.getMessage(), e);
			}
			jsonString = data.toString();
		}
		if (SYSConfig.isDebug) {
			log.debug("jsonString:" + jsonString);
		}
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	@SuppressWarnings("unchecked")
	public List getItems() {
		return items;
	}

	@SuppressWarnings("unchecked")
	public void setItems(List items) {
		this.items = items;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = totalCount / pageSize;
			if (totalCount % pageSize > 0) {
				count++;
			}
		} else {
			this.totalCount = 0;
		}
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getNextIndex() {
		int nextIndex = getStartIndex() + pageSize;
		if (nextIndex >= totalCount) {
			return getStartIndex();
		} else {
			return nextIndex;
		}
	}

	public int getPreviousIndex() {
		int previousIndex = getStartIndex() - pageSize;
		if (previousIndex < 0) {
			return 0;
		} else {
			return previousIndex;
		}
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
		this.currentPage = Integer.parseInt(page);
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
		this.startIndex = Integer.parseInt(start);
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
		this.pageSize = Integer.parseInt(limit);
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getSortStr() {
		return sort + " " + dir;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	/**
	 * @param sort
	 * @param dir
	 */
	public void setSort(String sort,String dir){
		this.sort = sort;
		this.dir = dir;
	}
	
	public void po2vo(Class object) throws Exception {
		List<Object> vos = new ArrayList<Object>();
		for (Object r : this.items) {
			Object vo = object.newInstance();
			BeanUtils.copyProperties(r, vo);
			vos.add(vo);
		}
		this.items = vos;
	}
}
