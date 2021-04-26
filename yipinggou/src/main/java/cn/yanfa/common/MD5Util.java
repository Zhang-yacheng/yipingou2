package cn.yanfa.common;


import org.springframework.util.DigestUtils;

public class MD5Util {
    private static final String SALT = "123456";
    public static String encode(String password) {
       // return DigestUtils.md5Hex(password);
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
       return s;
    }
    public static String encode(byte[] password) {
        // return DigestUtils.md5Hex(password);
        String s = DigestUtils.md5DigestAsHex(password);
        return s;
    }
    public static void main(String[] args) {
        System.out.println(encode("123456"));
    }
}
