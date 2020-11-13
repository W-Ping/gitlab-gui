package pojo;

/**
 * @author liu_wp
 * @date 2020/11/10
 * @see
 */
public class ResponseResult<T> {
    public final static String FAIL_CODE = "fail";
    public final static String SUCCESS_CODE = "success";
    private String code;
    private String message;
    private T object;

    public final static ResponseResult success() {
        return success(null, null);
    }

    /**
     * @param object
     * @param <T>
     * @return
     */
    public final static <T> ResponseResult success(T object) {
        return success(null, object);
    }


    /**
     * @param message
     * @return
     */
    public final static ResponseResult success(String message) {
        return success(message, null);
    }

    /**
     * @param message
     * @param object
     * @param <T>
     * @return
     */
    public final static <T> ResponseResult<T> success(String message, T object) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(SUCCESS_CODE);
        responseResult.setMessage(message);
        responseResult.setObject(object);
        return responseResult;
    }

    /**
     * @param message
     * @return
     */
    public final static ResponseResult fail(String message) {
        return fail(message, null);
    }


    public final static boolean isSuccess(ResponseResult responseResult) {
        return responseResult != null && SUCCESS_CODE.equals(responseResult.getCode());
    }

    /**
     * @param message
     * @param object
     * @param <T>
     * @return
     */
    public final static <T> ResponseResult<T> fail(String message, T object) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(FAIL_CODE);
        responseResult.setMessage(message);
        responseResult.setObject(object);
        return responseResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(final T object) {
        this.object = object;
    }
}
