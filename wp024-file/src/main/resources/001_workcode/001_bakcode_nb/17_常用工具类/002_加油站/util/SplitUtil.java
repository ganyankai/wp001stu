package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houhuateng on 2016/4/12.
 */
public class SplitUtil {
    /**
     * 拆分集合
     *
     * @param <T>
     * @param list  要拆分的集合
     * @param count 每个集合的元素个数
     * @return 返回拆分后的各个集合
     */
    public static <T> List<List<T>> split(List<T> list, int count) {
        if (list == null || count < 1) {
            return null;
        }

        List<List<T>> resultList = new ArrayList<List<T>>();
        int size = list.size();
        if (size <= count) { //数据量不足count指定的大小
            resultList.add(list);
        } else {
            int pre = size / count;
            int last = size % count;
            //前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < count; j++) {
                    itemList.add(list.get(i * count + j));
                }
                resultList.add(itemList);
            }
            //last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(list.get(pre * count + i));
                }
                resultList.add(itemList);
            }
        }

        return resultList;
    }
}
