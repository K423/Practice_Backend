package com.lzh.forum.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Data
@Component
/**
 * 标注在类上,将这个类交给Spring容器管理,相当于在xml中配置一个bean。
 * 让这个类成为Spring管理的bean,可以通过依赖注入使用。
 */
public class JwtUtils {

    public static final long EXPIRATION_TIME = 43200000L; //12hours期限

    public static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512); //秘钥

    /**
     * 生成JWT
     * @param name
     * @return
     */
    public static String generateToken(String name) {

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setHeaderParam("type", "JWT")  //指定类型为jwt
                .setSubject(name)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 通过Jwts构建一个解析器JwtParser
     * 设置签名密钥为之前生成JWT时使用的相同密钥
     * 构建解析器并使用它解析传入的JWT字符串
     * 如果解析成功,从Jwt中取出Claims body体返回
     * 如果解析失败,会抛异常并返回null
     * @param jwt
     * @return
     */
    public Claims paresJwt(String jwt){
        try {
            return Jwts.parserBuilder() //构建JWT解析器
                    .setSigningKey(key)
                    .build() //最终构建
                    .parseClaimsJws(jwt) //解析JWT令牌 并获取其中的声明（claims）
                    .getBody(); //获取JWT的主体部分（payload），即包含声明的JSON对象
        }catch (ExpiredJwtException e){
            log.info("token失效......");
            return null;
        }
    }

    //判断jwt是否过期
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
    /**
     * 从Claims中取出过期时间getExpiration()。
     * 获取当前时间new Date()。
     * 比较过期时间是否在当前时间之前。
     * 如果过期时间早于当前时间,表示已过期,返回true。
     * 否则未过期,返回false。
     * 因为生成JWT时已经设置了expiration时间,所以这里只需要取出该过期时间字段,和当前时间比对即可判断是否过期。
     */

}
