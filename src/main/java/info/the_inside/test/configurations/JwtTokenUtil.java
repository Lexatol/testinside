package info.the_inside.test.configurations;
/*
JwtTokenUtil - отвечает за генерацию, распарсивание токена, проверка токена на валидность
 */

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    /*
    Секретный код объявлен в application.yaml
     */
    @Value("${jwt.secret}")
    private String secret;

    /*
    Получение всей информации о клиенте
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /*
    Получение отдельныех полей
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /*
        собирает claims - права пользователя на этапе аутентификации
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList); // Кладем роли в claims в виде листа строк
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /*
    Cброка токена на основе полученной информации о клиенте от сервера
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + 24 * 60 * 60 * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /*
    Метод получает полезную информацию о клиенте из токена
    */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> getRoles(String token) {
        /*
        Достаются Claims (полезная информация о клиенте)
        отдаем функцию объясняющую как получить из Claims - список строк (ролей)
        у claims запросили поле roles являющееся листом строк
         */
        return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
    }

}
