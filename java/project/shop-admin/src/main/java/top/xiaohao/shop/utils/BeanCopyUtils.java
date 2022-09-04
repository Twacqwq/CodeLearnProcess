package top.xiaohao.shop.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 拷贝Bean工具类
 * @author Twac
 */
public class BeanCopyUtils {

    private BeanCopyUtils() {}

    /**
     * 拷贝Bean
     * @param source 原始对象
     * @param clazz 拷贝对象
     * @param <V> 泛型
     * @return result
     */
    public static <V> V copyBean(Object source, Class<V> clazz) {
        V result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 拷贝Bean并添加VO列表
     * @param list 原始bean列表
     * @param clazz 拷贝vo
     * @param <O> 原始对象泛型
     * @param <V> 拷贝对象泛型
     * @return list<V>
     */
    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
