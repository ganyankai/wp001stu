package com.zrytech.framework.newshop.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 页面数据对象
 * 
 */
public class DataBean extends HashMap implements Map{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据map
	 */
	Map map = null;
	/**
	 * 请求对象
	 */
	HttpServletRequest request;
	
	/**
	 * 有参构造器
	 * @param request 请求对象
	 */
	public DataBean(HttpServletRequest request){
		this.request = request;
		/**
		 * 获取参数Map
		 */
		String pageSize = request.getParameter("pageSize");
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap(); 
		Iterator entries = properties.entrySet().iterator(); 
		Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			returnMap.put(name, value); 
		}
		map = returnMap;
	}
	
	/**
	 * 无参构造
	 */
	public DataBean() {
		map = new HashMap();
	}
	
	/**
	 * 获取参数值
	 */
	@Override
	public Object get(Object key) {
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getString(Object key) {
		return (String)get(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}
	
	/**
	 * 清空请求数据
	 */
	public void clear() {
		map.clear();
	}
 
	/**
	 * 判断是否存在请求参数
	 */
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
 
	/**
	 * 判断是否存在请求值
	 */
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
 
	/**
	 * 获取entry集合
	 */
	public Set entrySet() {
		return map.entrySet();
	}
 
	/**
	 * 判断参数集合是否为空
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}
 
	public Set keySet() {
		return map.keySet();
	}
 
	/**
	 * 添加集合
	 */
	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		map.putAll(t);
	}

	/**
	 * 获取请求集合的大小
	 */
	public int size() {
		return map.size();
	}
 
	/**
	 * 获取请求参数集合
	 */
	public Collection values() {
		return map.values();
	}
}
