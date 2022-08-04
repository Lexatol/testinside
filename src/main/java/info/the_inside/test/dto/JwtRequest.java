package info.the_inside.test.dto;
/*
обертка над пользователем, для json-на аутентификации
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtRequest {
    private String name;
    private String password;
}
