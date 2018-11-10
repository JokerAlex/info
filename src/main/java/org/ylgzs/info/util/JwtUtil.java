package org.ylgzs.info.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


/**
 * @ClassName JwtUtil
 * @Description token
 * @Author alex
 * @Date 2018/10/14
 **/
public class JwtUtil {

    private static String HEADER = "Authorization";
    private static String TOKEN_HEAD = "Bearer ";
    private static Long EXPIRATION = 30L;
    private static SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * 生成 token
     * @param claims
     * @return
     */
    public static String createJwt(Map<String, Object> claims) {
        return createJwt(claims, EXPIRATION);
    }

    /**
     * 生成 token
     * @param claims 变量参数
     * @param expiration 时间长
     * @return
     */
    public static String createJwt(Map<String, Object> claims, Long expiration) {
        LocalDateTime time = LocalDateTime.now().plusMinutes(expiration);

        Date expire = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());

        JwtBuilder builder = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeaderParam("alg", "HS512")
                .setClaims(claims)
                .setIssuer("info")
                .setExpiration(expire)
                .signWith(KEY, SignatureAlgorithm.HS256);
        return builder.compact();

    }

    /**
     * 解码
     * @param token
     * @return
     * @throws ExpiredJwtException
     */
    public static Claims parseJwt(String token) throws ExpiredJwtException {
        Claims claims = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();
        return Optional
                .ofNullable(claims)
                .filter(claim -> claim.getExpiration() == null || !new Date().after(claim.getExpiration()))
                .orElse(null);
    }

    public static Integer getUserIdFromToken(HttpServletRequest request) throws Exception {
        String header = request.getHeader(HEADER);

        Integer userId = -1;
        if (header != null && header.startsWith(TOKEN_HEAD)) {
            String token = header.substring(TOKEN_HEAD.length());
            Claims claims;
            try {
                claims = JwtUtil.parseJwt(token);
                userId = claims.get("userId", Integer.class);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
        return userId;
    }
}
