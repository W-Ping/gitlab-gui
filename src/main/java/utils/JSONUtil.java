package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class JSONUtil {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JSONUtil.class);
    /**
     * @param object
     * @return
     */
    public final static String Object2JSON(Object object) {

        return JSON.toJSONString(object);
    }
    public final static JSONObject Str2JSONObject(String str) {
        try {
            return JSON.parseObject(str);
        } catch (Exception e) {
            log.error("【Str2JSONObject】 error : {}", ExceptionUtil.getErrorMessage(e));
            return null;
        }
    }
    public final static <T> List<T> JSON2List(String jsonStr, Class<T> cls) {
        if (jsonStr == null || jsonStr.length() <= 0) {
            return null;
        }
        try {
            return JSON.parseArray(jsonStr, cls);
        } catch (Exception e) {
            log.error("【JSON2List】 error : {}", ExceptionUtil.getErrorMessage(e));
            return null;
        }
    }
    public final static <T> T JSON2Object(String jsonStr, Class<T> cls) {
        if (jsonStr == null || jsonStr.length() <= 0) {
            return null;
        }
        try {
            if (String.class.equals(cls)) {
                return (T) jsonStr;
            }
            return JSON.parseObject(jsonStr, cls);
        } catch (Exception e) {
            log.error("【JSON2Object】 error : {}", ExceptionUtil.getErrorMessage(e));
            return null;
        }
    }
}
