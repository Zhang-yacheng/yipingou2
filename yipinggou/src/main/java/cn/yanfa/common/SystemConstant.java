package cn.yanfa.common;

/**
 * 系统静态变量
 */
public class SystemConstant {

    /**
     * 接口处理成功返回值
     */
    public static final String RESULT_SUCCESS = "success";

    /**
     * 接口处理失败返回值
     */
    public static final String RESULT_FAIL = "fail";

    public final static String VERTICAL = " | ";

    /**
     * Token秘钥存储
     */
    public static final String TOKEN_SECRET = "token:secret:userId";

    /**
     * 用户信息 包含登录token、
     */
    public static final String HASH_KEY_USERINFO = "user:%s";

    /**
     * 登录token
     */
    public static final String LOGIN_TOKEN = "token";
    /**
     * 文件上传虚拟路径
     */
    public static final String URL_ROOT = "/upload";

}
