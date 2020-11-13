package utils;

import java.util.UUID;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class GenerateIdUtil {
    /**
     * @return
     */
    public static final String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
