package xusy.learn.common.exceptions;

/**
 * 业务异常基类，所以业务异常均需继承此异常
 * Created by siyong.xu on 2017-06-29.
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 2461955237602618850L;

    /**
     * 具体异常码
     */
    protected int code;
    /**
     * 异常信息
     */
    protected String msg;

    public BizException() {
        super();
    }

    public BizException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
    public BizException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
