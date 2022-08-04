package info.the_inside.test.service;
/*
 * Класс UserSevice отвечает за логику User:
 * - поиск в базе через репозиторий и дополнительной обработкой данных
 * - метод mapRolesToAuthorities преобразовывает созданные
 * нами роли в права доступа, обходимые Spring Secuirity
 * - метод loadUserByUsername - необходим для поиска Пользователя в базе, если такого нет
 * будет сформирован exception, если такой пользователь найден, то он будет преобразован до
 * UserDetails для работы Spring Security
 */

import info.the_inside.test.entities.Role;
import info.the_inside.test.entities.User;
import info.the_inside.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    /*
    инжектим Репозиторий через конструктор с помощью библиотеки lombok
     */
    private final UserRepository userRepository;

    /*
     перееопределяем единсвтенный метод от UserDetailService,
     который необходим для создания UserDetails, чтобы Spring мог произвести авторизацию
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByName(username);
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    /*
    Перегоняем список ролей в список GrantedAuthority
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getTitle())).collect(Collectors.toList());
    }

    /*
    findByUsername - поиск пользователя в базе по имени, если такого нет, то вылетает исключение
     */

    public User findByName(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with name %s not found ", name)));
    }


}
