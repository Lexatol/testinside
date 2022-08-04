package info.the_inside.test;


import info.the_inside.test.entities.Message;
import info.the_inside.test.entities.User;
import info.the_inside.test.repository.MessageRepository;
import info.the_inside.test.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class TestMessageRepository {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private int LIMIT = 5;
    private Long USERID = 1L;

    @Test
    public void testSaveMessageInDb() {
        User user = userRepository.findByName("Petya").get();
        Message newMessage = new Message();
        newMessage.setUser(user);
        newMessage.setDescription("Это новое сообщение для теста");
        entityManager.persist(newMessage);
        entityManager.flush();

        Page<Message> pageMessages = messageRepository.findAllByUserId(USERID,
                PageRequest.of(0, LIMIT + 1, Sort.by(Sort.Direction.DESC, "createAt")));
        List<Message> messages = pageMessages.getContent();

        Assertions.assertEquals(6, messages.size());
        Assertions.assertEquals("Это новое сообщение для теста", messages.get(0).getDescription());
    }


    @Test
    public void testFindAllMessageByUserIdAndLimitMessage() {
        Page<Message> pageMessages = messageRepository.findAllByUserId(USERID,
                PageRequest.of(0, LIMIT, Sort.by(Sort.Direction.DESC, "createAt")));
        Assertions.assertEquals(LIMIT, pageMessages.getContent().size());

        LocalDateTime min = pageMessages.getContent().get(0).getCreateAt();
        LocalDateTime max = pageMessages.getContent().get(1).getCreateAt();
        System.out.println(min);
        System.out.println(max);

        int greate = 0;
        if (min.isAfter(max)) {
            greate = 1;
        }
        Assertions.assertEquals(1, greate);
    }


}
