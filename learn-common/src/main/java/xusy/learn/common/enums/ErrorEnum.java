package xusy.learn.common.enums;

/**
 * 错误信息枚举类
 * Created by siyong.xu on 2017-07-03.
 */
public enum ErrorEnum {
    TOKEN_IS_ILLICIT(999999,"非法请求！"),//Token 验证不通过
    SESSION_IS_OUT_TIME(999998,"会话超时!"),//获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面

    DB_INSERT_RESULT_0(999001, "数据库操作,insert返回0!"),
    DB_UPDATE_RESULT_0(999002, "数据库操作,update返回0!"),
    DB_SELECTONE_IS_NULL(999003, "数据库操作,selectOne返回null!"),
    DB_LIST_IS_NULL(999004,"数据库操作,list返回null!");

    Integer code;
    String msg;

    private ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ErrorEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (ErrorEnum errorEnum : ErrorEnum.values()) {
            if (errorEnum.getCode() == code) {
                return errorEnum;
            }
        }
        return null;
    }
}
