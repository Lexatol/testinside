package info.the_inside.test;

import info.the_inside.test.dto.MessageDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestServerRuN {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @WithMockUser(username = "Bob", roles = "user", password = "100")
    public void RestTest() {

        MessageDto newMessage = new MessageDto();
        newMessage.setName("Bob");
        newMessage.setDescription("l;sdkfj;sdlkfas;dlgvk");

        ResponseEntity messageInDb = restTemplate.postForEntity("/api/v1/messages/add", newMessage, ResponseEntity.class);

        Assertions.assertThat(messageInDb).isNotNull();
        Assertions.assertThat(messageInDb.getStatusCode()).isNotNull();
        Assertions.assertThat(messageInDb.getHeaders()).isNotNull();


    }
}
