package top.xiaohao.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    private static final long JWT_TTL = 60 * 60 * 1000L; //1h 单位ms

    private static final String JWT_KEY = "Mick233";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成token
     * @param subject 主题
     * @return token
     */
    public static String createToken(String subject) {
        JwtBuilder jwtBuilder = getJwtBuilder(subject, null, getUUID());
        return jwtBuilder.compact();
    }

    public static String createToken(String subject, Long ttlMills) {
        JwtBuilder jwtBuilder = getJwtBuilder(subject, ttlMills, getUUID());
        return jwtBuilder.compact();
    }

    public static String createToken(String subject, Long ttlMills, String uuid) {
        JwtBuilder jwtBuilder = getJwtBuilder(subject, ttlMills, uuid);
        return jwtBuilder.compact();
    }

    public static Claims parseToken(String token) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * 获取jwt生成器
     * @param subject 主题
     * @param ttlMills 过期时间(ms)
     * @param uuid 随机id
     * @return jwtBuilder
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMills, String uuid) {
        SecretKey secretKey = generalKey();
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);
        if (ttlMills == null) {
            ttlMills = JWT_TTL;
        }
        long expMills = nowMills + ttlMills;
        Date expDate = new Date(expMills);
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setExpiration(expDate)
                .setIssuer("twac")
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey);
    }

    /**
     * 对jwt的key进行加密后使用
     * @return SecretKey
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwYTg2YWFkNjRiY2I0MjdjYjQ0ZDZlMjZhMGVkZGYyZiIsInN1YiI6IjIiLCJleHAiOjE2NDc0MTMxOTIsImlzcyI6InR3YWMiLCJpYXQiOjE2NDc0MDk1OTJ9.KhkNHlj5Uuc2omdQeBXc9s_8DDlXyfc00yKoNVLGshA";
        Claims parseToken = parseToken(token);
        System.out.println(parseToken);
    }

}
