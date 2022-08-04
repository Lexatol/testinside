package info.the_inside.test.service;

import info.the_inside.test.dto.MessageDto;
import info.the_inside.test.entities.Message;
import info.the_inside.test.entities.User;
import info.the_inside.test.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    /*
    Сохранение в базу новых сообщение после конвертации из ДТО
     */
    public MessageDto save(MessageDto messageDto) {
        Message message = new Message();
        User user = userService.findByName(messageDto.getName());
        message.setUser(user);
        message.setDescription(messageDto.getDescription());
        return toDto(messageRepository.save(message));
    }

    /*
    Метод ищет заданное количество последних сообщений в базе от авторизированного пользователя
     */
    public List<MessageDto> findLimitMessage(String username, int limit) {
        User user = userService.findByName(username);
        Page<Message> messages = messageRepository.findAllByUserId(user.getId(),
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createAt")));
        return messages.getContent().stream().map(this::toDto).collect(Collectors.toList());
    }

    /*
    Переганяем сущность в ДТО
     */
    private MessageDto toDto(Message message) {
        return MessageDto.builder()
                .name(message.getUser().getName())
                .description(message.getDescription())
                .createAt(message.getCreateAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                .build();
    }
}
