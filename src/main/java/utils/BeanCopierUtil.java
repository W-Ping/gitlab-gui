package utils;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liu_wp
 * @date 2020/11/6
 * @see
 */
public class BeanCopierUtil {
    private BeanCopierUtil() {
    }

    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    private static final Map<String, ConstructorAccess> CONSTRUCTOR_ACCESS_CACHE = new ConcurrentHashMap<>();

    /**
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, new DateConvert("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * @param sourceClass
     * @param targetClass
     * @return
     */
    private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier = null;
        if (!BEAN_COPIER_CACHE.containsKey(beanKey)) {
            copier = BeanCopier.create(sourceClass, targetClass, true);
            BEAN_COPIER_CACHE.put(beanKey, copier);
        } else {
            copier = BEAN_COPIER_CACHE.get(beanKey);
        }
        return copier;
    }

    /**
     * 两个类的全限定名拼接起来构成Key
     *
     * @param sourceClass
     * @param targetClass
     * @return
     */
    private static String generateKey(Class<?> sourceClass, Class<?> targetClass) {
        return sourceClass.getName() + targetClass.getName();
    }

    /**
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public final static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }
        T t = null;
        try {
            t = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        copyProperties(source, t);
        return t;
    }

    /**
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public final static <T> List<T> copyPropertiesOfList(List<?> sourceList, Class<T> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) {
            return null;
        }
        ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
        List<T> resultList = new ArrayList<>(sourceList.size());
        T t = null;
        for (Object o : sourceList) {
            try {
                t = constructorAccess.newInstance();
                copyProperties(o, t);
                resultList.add(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     * @param targetClass
     * @param <T>
     * @return
     */
    private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> targetClass) {
        ConstructorAccess<T> constructorAccess = CONSTRUCTOR_ACCESS_CACHE.get(targetClass.getName());
        if (constructorAccess != null) {
            return constructorAccess;
        }
        try {
            constructorAccess = ConstructorAccess.get(targetClass);
            constructorAccess.newInstance();
            CONSTRUCTOR_ACCESS_CACHE.put(targetClass.toString(), constructorAccess);
        } catch (Exception e) {

        }
        return constructorAccess;
    }

    public static class DateConvert implements Converter {
        private String dateFormatter;

        DateConvert(String dateFormatter) {
            this.dateFormatter = dateFormatter;
        }

        @Override
        public Object convert(final Object arg1, final Class arg0, final Object context) {
            if (arg1 == null) {
                return null;
            }
            if (arg1 instanceof String && arg0 == String.class) {
                //输入string ,输入string
                return arg1;
            } else if (arg1 instanceof Date && arg0 == Date.class) {
                //输入Date ,输出Date
                return arg1;
            } else if (arg1 instanceof String && arg0 != String.class) {
                //输入string ,输出Date
                try {
                    String dateStr = (String) arg1;
                    if (dateStr == null || dateStr.trim().length() == 0) {
                        return null;
                    }
                    LocalDateTime localDateTime = DateUtil.date2LocalDateTime(dateStr, dateFormatter);
                    return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
                } catch (Exception e) {
                    return arg1;
                }
            } else if (arg1 instanceof Date) {
                //输入Date ,输出String
                Date date = (Date) arg1;
                return DateUtil.dateFormatter(date, dateFormatter);
            }
            return arg1;
        }
    }
}
