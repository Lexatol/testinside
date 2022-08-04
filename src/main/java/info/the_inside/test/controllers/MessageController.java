package info.the_inside.test.controllers;
/*
MessageController оотвечает за
- сохранение сообщений от авторизованых пользователей
- за выдачу десяти или более последних сообщений авторизованной пользователя. При изменении параметра
  LIMIT_MESSAGE возможно изменить кол-во выдаваемых сообщений
 */


import info.the_inside.test.dto.MessageDto;
import info.the_inside.test.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private int LIMIT_MESSAGE = 10;
    private String MESSAGE_HISTORY = "history " + LIMIT_MESSAGE;


    @PostMapping("/add")
    public ResponseEntity<?> addMessageOrReturnMessage(@RequestBody MessageDto message, Principal principal) {
        if (!message.getName().equals(principal.getName())) {
            return new ResponseEntity<>("You is not authorization", HttpStatus.FORBIDDEN);
        }
        if (message.getDescription().equals(MESSAGE_HISTORY) && principal.getName().equals(message.getName())) {
            List<MessageDto> messages = messageService.findLimitMessage(message.getName(), LIMIT_MESSAGE);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
        return new ResponseEntity<>(messageService.save(message), HttpStatus.CREATED);
    }

}
