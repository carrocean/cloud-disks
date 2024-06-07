package com.example.util;

import io.jsonwebtoken.*;
import lombok.val;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    private static long time = 1000 * 60 * 60 * 24;
    private static String signature = "admin";

    public static String createToken(Integer userId, String userName) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder.setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("userName", userName)
                .claim("userId", userId)
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return jwtToken;
    }

    //校验token
    public static boolean checkToken(String token) {
        if (token == null) return false;
        try {
            Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param jwtToken
     * @return
     */
    public static String getUserIdByToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return String.valueOf(claims.get("userId"));
    }
}

































