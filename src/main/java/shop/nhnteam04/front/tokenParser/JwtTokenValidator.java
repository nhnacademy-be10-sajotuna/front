package shop.nhnteam04.front.tokenParser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JwtTokenValidator {
    private final RedisTemplate<String, Object> redisTemplate;
    private final Environment env;
    private byte[] secretKey;

    public JwtTokenValidator(Environment env, RedisTemplate<String, Object> redisTemplate) {
        this.env = env;
        this.redisTemplate = redisTemplate;
        secretKey = env.getProperty("token.secret").getBytes(StandardCharsets.UTF_8);
    }

    public boolean validateRefreshToken(String refreshToken) {
        if (refreshToken == null) {
            return false;
        }
        if (!validate(refreshToken)) {
            return false;
        }
        String id = getIdFromToken(refreshToken);
        String savedRefreshToken = (String) redisTemplate.opsForValue().get("refresh_token:" + id);
        if (!refreshToken.equals(savedRefreshToken)) {
            return false;
        }
        return true;
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public String getIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}