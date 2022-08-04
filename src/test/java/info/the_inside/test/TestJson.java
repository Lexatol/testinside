package info.the_inside.test;

import info.the_inside.test.dto.MessageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class TestJson {
    @Autowired
    private JacksonTester<MessageDto> jackson;

    @Test
    public void jsonSerializationTest() throws Exception {
        MessageDto messageDto = new MessageDto();
        messageDto.setName("Petya");
        messageDto.setDescription("Petrushka");
        messageDto.setCreateAt("2020-10-10");
        assertThat(this.jackson.write(messageDto)).extractingJsonPathStringValue("$.name");
        assertThat(this.jackson.write(messageDto)).extractingJsonPathStringValue("$.description").isEqualTo("Petrushka");
        assertThat(this.jackson.write(messageDto)).extractingJsonPathStringValue("$.createAt").isEqualTo("2020-10-10");
    }

    @Test
    public void jsonDeserializationTest() throws Exception {
        String content = "{\"name\": \"Petya\",\"description\":\"Petrushka\"}";
        MessageDto messageDto = new MessageDto();
        messageDto.setName("Petya");
        messageDto.setDescription("Petrushka");

        assertThat(this.jackson.parse(content)).isEqualTo(messageDto);
        assertThat(this.jackson.parseObject(content).getName()).isEqualTo("Petya");
        assertThat(this.jackson.parseObject(content).getDescription()).isEqualTo("Petrushka");
    }
}