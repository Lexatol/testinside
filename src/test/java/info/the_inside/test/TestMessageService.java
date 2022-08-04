package info.the_inside.test;

import info.the_inside.test.dto.MessageDto;
import info.the_inside.test.entities.Message;
import info.the_inside.test.entities.User;
import info.the_inside.test.repository.MessageRepository;
import info.the_inside.test.service.MessageService;
import info.the_inside.test.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class TestMessageService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @MockBean
    private MessageRepository messageRepository;

    private int LIMIT = 5;
    private Long USERID = 1L;

    @Test
    public void testFindLimitMessage() {

        List<Message> messageList = new ArrayList<>();
        User user = userService.findByName("Petya");
        for (int i = 0; i < LIMIT + 1; i++) {
            Message message = new Message();
            message.setUser(user);
            message.setDescription("New message " + i);
            message.setCreateAt(LocalDateTime.MIN);
            messageList.add(message);
        }
        Page<Message> pages = new PageImpl(messageList);

        Mockito.doReturn(pages)
                .when(messageRepository)
                .findAllByUserId(USERID, PageRequest.of(0, LIMIT, Sort.by(Sort.Direction.DESC, "createAt")));


        List<MessageDto> messageDtoList = messageService.findLimitMessage("Petya", LIMIT);
        Assertions.assertThat(messageDtoList).isNotNull();







    }

}
