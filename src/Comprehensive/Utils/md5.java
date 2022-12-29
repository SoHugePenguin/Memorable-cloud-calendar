package Comprehensive.Utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Title md5
 * @Author SoHugePenguin
 * @date 2022/12/14 23:31
 */
public class md5 {
    public static String transform(String password, String salt) {
        //MD5加密+加盐二次加密
        password += salt;
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        assert digest != null;
        return new BigInteger(1, digest).toString(16);
    }
}
