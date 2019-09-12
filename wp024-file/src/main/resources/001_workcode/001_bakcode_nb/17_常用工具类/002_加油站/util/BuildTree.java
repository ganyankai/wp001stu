package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 构建树型数据
 * 
 * @author gaotao 2010-05-16
 * 
 */
public class BuildTree {
	/**
	 * 构造树型数据,此方法根据isCopy 来判断是否复制一份原数据，在
	 * 新数据上进行树型改造 true,代表复制，这样不会改变原数据结构，fasle反之
	 * 
	 * @param lis
	 *            从数据库查出的原数据
	 * @param parentCol
	 *            父ID列名
	 * @param selfCol
	 *            主键列名
	 * @param isCopy
	 *            是否复制
	 * @return
	 */
	public static List<Map<String, Object>> createTree(List<Map> lis,
			String parentCol, String selfCol, boolean isCopy) {
		List<Map> list = isCopy ? cloneList(lis) : lis;// 将数据复制一份，以免对原数据结构改变，此步骤
		return createTree(list, parentCol, selfCol);
	}

	/**
	 * 构造树型数据，此方法会将原数据结构改变
	 * 
	 * @param list
	 *            从数据库查出的原数据
	 * @param parentCol
	 *            父ID列名
	 * @param selfCol
	 *            主键列名
	 * @return
	 */
	public static List<Map<String, Object>> createTree(List<Map> list,
			String parentCol, String selfCol) {
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		// 完全循环一次,是子节点的最终被替换为null
		for (Iterator<Map> it = list.iterator(); it.hasNext();) {
			Map mp = it.next();
			if (mp == null)
				continue;// 为空,说明已经被设为一个子节点了
			setChilds(list, parentCol, selfCol, mp);
		}

		for (Iterator<Map> it = list.iterator(); it.hasNext();) {
			Map mp = it.next();
			if (mp != null) {// 不为空则说明是一个根节点
				tree.add(mp);
			}
		}
		return tree;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, Object>> createTree(List<Map> list,
			String parentCol, String selfCol, String idCol, String textCol) {
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		// 完全循环一次,是子节点的最终被替换为null
		for (Iterator<Map> it = list.iterator(); it.hasNext();) {
			Map mp = it.next();
			if (mp == null) {
				continue;// 为空,说明已经被设为一个子节点了
			}
//			mp.put("id", mp.get(idCol));
//			mp.put("text", mp.get(textCol));
			setChilds(list, parentCol, selfCol, mp, idCol, textCol);
		}

		for (Iterator<Map> it = list.iterator(); it.hasNext();) {
			Map mp = it.next();
			if (mp != null) {// 不为空则说明是一个根节点
				tree.add(mp);
			}
		}
		return tree;
	}
	
	
	public static Map<String, Object> objToMap(Object obj ) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Map<String, Object> map = new HashMap<String, Object>();
		Class cls = obj.getClass();
		Field[] fields = obj.getClass().getDeclaredFields();
//		System.out.println(fields.length);
		for (Field f: fields) {
//			Class<?> clsType = f.getType();
			String name = f.getName();
			String method = name.substring(0, 1).toUpperCase()
					+ name.substring(1, name.length());
			String strGet = "get" + method;
			Method methodGet = cls.getDeclaredMethod(strGet);
			Object object = methodGet.invoke(obj);
			map.put(name, object);
		}
		return map;
	}
	
	private static void setChilds(List<Map> list, String parentCol,
			String selfCol, Map mp, String idCol, String textCol) {
		String sid = mp.get(selfCol).toString();// 当前节点主键
		for (int i = 0; i < list.size(); i++) {
			Map temp = list.get(i);
			if (temp == null) {
				continue;// 为空,说明已经被设为一个子节点了
			}
			temp.put("id", temp.get(idCol));
			temp.put("text", temp.get(textCol));
			String pid = temp.get(parentCol).toString(); // 循环中的节点父ID
			// 当循环节点的父ID=当前结点的主键时,说明循环节点就是一个子节点
			if (pid == sid || (sid != null && sid.equals(pid))) {
				// 递归,寻找叶节点
				setChilds(list, parentCol, selfCol, temp);
				// 获取子节点集合
				List<Map> arr = (List<Map>) mp.get("children");
				if (arr == null)// 如果还没有子节点集合,则新建一个
					arr = new ArrayList<Map>();
				arr.add(temp);// 节点集合添加此子节点
				mp.put("children", arr);// 得新赋给当前结点
				list.set(i, null);// 被设置为子节点后,进行清空标记,不能删除
			}
		}
	}

	/**
	 * 根据当前节点找子节点
	 * 
	 * @param list
	 *            数据库查出的原数据
	 * @param parentCol
	 *            父ID列名
	 * @param selfCol
	 *            主键列名
	 * @param mp
	 *            当前节点
	 */
	private static void setChilds(List<Map> list, String parentCol,
			String selfCol, Map mp) {
		String sid = mp.get(selfCol).toString();// 当前节点主键
		for (int i = 0; i < list.size(); i++) {
			Map temp = list.get(i);
			if (temp == null)
				continue;// 为空,说明已经被设为一个子节点了
			String pid = temp.get(parentCol).toString();// 循环中的节点父ID
			// 当循环节点的父ID=当前结点的主键时,说明循环节点就是一个子节点
			if (pid == sid || (sid != null && sid.equals(pid))) {
				// 递归,寻找叶节点
				setChilds(list, parentCol, selfCol, temp);
				// 获取子节点集合
				List<Map> arr = (List<Map>) mp.get("children");
				if (arr == null)// 如果还没有子节点集合,则新建一个
					arr = new ArrayList<Map>();
				arr.add(temp);// 节点集合添加此子节点
				mp.put("children", arr);// 得新赋给当前结点
				list.set(i, null);// 被设置为子节点后,进行清空标记,不能删除
			}
		}
	}

	// 克隆，以免对原来list数据结构改变
	private static List<Map> cloneList(List<Map> lis) {
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < lis.size(); i++) {
			list.add(new HashMap(lis.get(i)));
		}
		return list;
	}
	
	public static <T> List<Map<String, Object>> createTreeMap(List<T> list, 
			String parentCol, String selfCol, String idCol, 
			String textCol) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<Map> listmap = new ArrayList<Map>();
		for (T t : list) {
			Map<String, Object> map = objToMap(t);
			listmap.add(map);
		}
		
		return createTree(listmap, parentCol, selfCol, idCol, textCol);
	}

	// 测试用数据
	private static List getData() {
		Map<String, String> m1 = new HashMap<String, String>();
		m1.put("id-1", "1");
		m1.put("pid-1", "");
		m1.put("cPowerSysName", "1");
		Map<String, String> m2 = new HashMap<String, String>();
		m2.put("id-1", "2");
		m2.put("pid-1", "1");
		m2.put("cPowerSysName", "2");
		Map<String, String> m3 = new HashMap<String, String>();
		m3.put("id-1", "3");
		m3.put("pid-1", "2");
		m3.put("cPowerSysName", "3");
		Map<String, String> m4 = new HashMap<String, String>();
		m4.put("id-1", "4");
		m4.put("pid-1", "");
		m4.put("cPowerSysName", "4");
		Map<String, String> m5 = new HashMap<String, String>();
		m5.put("id-1", "5");
		m5.put("pid-1", "4");
		m5.put("cPowerSysName", "5");

		Map<String, String> m6 = new HashMap<String, String>();
		m6.put("id-1", "6");
		m6.put("pid-1", "1");
		m6.put("cPowerSysName", "6");
		List list = new ArrayList<Map>();
		list.add(m5);
		list.add(m2);
		list.add(m3);
		list.add(m4);
		list.add(m1);
		list.add(m6);
		return list;
	}
	
	private static List getListBean() {
		List list = new ArrayList();
		TUser u = new TUser();
		u.setId(1);
		u.setName("1");
		
		TUser u2 = new TUser();
		u2.setId(2);
		u2.setPid(1);
		u2.setName("2");
		
		TUser u3 = new TUser();
		u3.setId(3);
		u3.setPid(2);
		u3.setName("3");
		
		TUser u4 = new TUser();
		u4.setId(4);
		u4.setName("4");
		
		TUser u5 = new TUser();
		u5.setId(5);
		u5.setPid(4);
		u5.setName("5");
		
		TUser u6 = new TUser();
		u6.setId(6);
		u6.setPid(1);
		u6.setName("6");
		
		list.add(u);
		list.add(u2);
		list.add(u3);
		list.add(u4);
		list.add(u5);
		list.add(u6);
		
		return list;
	}

	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//		BuildTree bt = new BuildTree();
//		List list = bt.getData();
//		System.out.println(list);
//		List<Map<String, Object>> ls = bt.createTree(list, "pid-1", "id-1", "id-1", "cPowerSysName");
//		System.out.println(ls);
//		String json = JackJson.getBasetJsonData(ls);
//		System.out.println(json);
//		TUser u = new TUser();
//		u.setId(1);
//		u.setPid(0);
//		u.setName("haha");
//		Map<String, Object> map = objToMap(u);
//		System.out.println(map);
		
//		List<Map<String, Object>> ls = BuildTree.createTreeMap(getListBean(), 
//				"pid", "id", "id", "name");
//		System.out.println(ls);
//		String json = JackJson.getBasetJsonData(ls);
//		System.out.println(json);
	}
}

class TUser {
	private int id;
	private String name;
	private int pid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	@Override
	public String toString() {
		return "TUser [id=" + id + ", name=" + name + ", pid=" + pid + "]";
	}
	
}