package util;

import com.catt.common.util.lang.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** List转Map工具类（list、map各项转换都可以放在此类中）
 * @version   1.0
 * @author    houhuateng
 * @since     2015-1-22 11:00:00 PM  jdk版本：jdk1.8
 *
 */
public class ListToMapUtil {

    /**
     * 将list转换成map
     * @param list 查询的多行数据
     * @param sPKName 在list中代表数据的主键
     * @return 以主键值作为key的map,map的value为map格式的一行数据
     */
    public static Map<String, Map<String,String>> convertListToMap(List<Map<String, String>> list, String sPKName){
        Map<String, Map<String,String>> map = new HashMap<String, Map<String,String>>();
        for (Map<String, String> m : list) {
            map.put(StringUtil.toString(m.get(sPKName)), m);
        }
        return map;
    }
    /**
     * 将list里的主键值拼接成逗号隔开的数据格式,如1111,2222,3333
     * @param list 查询的多行数据
     * @param sPKName 在list中代表数据的主键
     * @return
     */
    public static String getPKIdsFromList(List<Map<String, String>> list,String sPKName){
        StringBuffer s = new StringBuffer();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if(i!=0){
                s.append(",");
            }
            s.append(StringUtil.toString(list.get(i).get(sPKName)));
        }
        return s.toString();
    }
    /**
     * 将按主键作为map中key的多行数据并到List中
     * @param list 查询的多行数据
     * @param sPKName 在list中代表数据的主键
     * @param map 要拼接的数据
     * 数据样例 list值(id:1,name:'hhh';id:2,name:'kkk') sPKName值"id" map为多个map，值为 (2,(id:2,birth:'2014-2-2')
     * 最终结果 list值(id:1,name:'hhh';id:2,name:'kkk',birth:'2014-2-2')
     */
    public static void megerMapToList(List<Map<String, String>> list, String sPKName, Map<String, Map<String,String>>... map){
        for (int i = 0, len=map.length; i < len; i++) {
            if(!map[i].isEmpty()){
                for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                    Map m = (Map) iterator.next();
                    String sPKNameVal = StringUtil.toString(m.get(sPKName));//主键值
                    Map t = map[i].get(sPKNameVal);//获取主键对应的map
                    if(t != null){
                        m.putAll(t);//不为空，则设置到原list的map中去
                    }
                }
            }
        }
    }
    /**
     * 查询的多行Map数据
     * @param sPKName	  枚举名
     * @param sValueName  枚举值
     * @return
     */
    public static Map<String,String> convertListToMap(List<Map<String, String>> list, String sPKName, String sValueName){
        Map<String,String> map = new HashMap<String,String>();
        for (Map<String, String> m : list) {
            map.put(m.get(sPKName), m.get(sValueName));
        }
        return map;
    }

    /**
     * 从结果集中提取主键字段，并设置为List<String>格式
     *
     * @param ls
     *            待处理的数据
     * @param key
     *            主键
     * @return 待查询的主键清单
     */
    public static List<String> listMapToSring(List<Map<String, String>> ls, String key) {
        List<String> result = new ArrayList<String>();
        for (Map<String, String> map : ls) {
            result.add(map.get(key));
        }
        return result;
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
        Map m1 = new HashMap();Map m2 = new HashMap();Map m3 = new HashMap();
        m1.put("Id", "2");m1.put("Name", "aaaaa");m2.put("Id", "2");m2.put("Name", "bbbbb");m3.put("Id", 2);m3.put("Name", "ccccc");
        m1.put("key", "3");m2.put("key", "3");m3.put("key", "2");
        list.add(m1);list.add(m2);list.add(m3);
        String sPKName = "Id";

        Map<String, Map<String,String>> map  = new HashMap<String, Map<String,String>>();
        Map<String,String> mm = new HashMap<String, String>();
        mm.put("key", "3");
        mm.put("bir", "2013-2-3");
        map.put("3", mm);
        megerMapToList(list,"key",map);
        System.out.println(list);
        System.out.println(list.get(1).get("bir"));
        for (int i = 0; i < list.size(); i++) {
            Map map2 = list.get(i);
            System.out.println(map2.get("Id")+" "+map2.get("key")
                    +"  " + map2.get("Name")
                    +"  "+ map2.get("bir"));
        }
    }

}
