package info.the_inside.test.configurations;

/*
Фильтр для аутентификации, принимает от пользователя токен и проверяет его
 */


import info.the_inside.test.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.JsonUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.message.AuthException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;


    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        /*
        Проверка токена (наличие и корректность его в header)
         */
        if (authHeader != null && authHeader.startsWith("Bearer_")) {
            jwt = authHeader.substring(7);
            try {
                jwtTokenUtil.validateAccessToken(jwt);
                username = jwtTokenUtil.getUsernameFromToken(jwt);
            } catch (Exception e) {
                throw new AuthException("Not valid token");
            }
        }


        /*
        выполниться если контекст пустой и пользователь существует
         */
        if (username != null && SecurityContextHolder.getContext().

                getAuthentication() == null) {
            /*
            По данным пришедши в токене, кладем их в контект чтобы не обращаться к базе см. код ниже
            Из токена достали имя пользователя и его роль
            (токен нельзя подменить и он является достоверным)
             */
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, jwtTokenUtil.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        /* Говорим продолжать обработку
         */
        filterChain.doFilter(request, response);
    }
}
