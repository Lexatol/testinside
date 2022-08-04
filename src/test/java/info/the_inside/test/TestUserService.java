package info.the_inside.test;

import info.the_inside.test.entities.User;
import info.the_inside.test.repository.UserRepository;
import info.the_inside.test.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class TestUserService {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @Test
    public void testFindUserByName() {
        User userFromDb  = new User();
        userFromDb.setName("Bob");
        userFromDb.setEmail("bob@gmail.com");
        userFromDb.setPassword("100");

        Mockito.doReturn(Optional.of(userFromDb))
                .when(userRepository)
                .findByName("Bob");

        User user = userService.findByName("Bob");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("bob@gmail.com", user.getEmail());
        Assertions.assertEquals("100", user.getPassword());
        Mockito.verify(userRepository, Mockito.times(1)).findByName(ArgumentMatchers.eq("Bob"));


    }
}
