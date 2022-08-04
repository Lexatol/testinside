package info.the_inside.test;

import info.the_inside.test.service.MessageService;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class TestMessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void TestAddMessageOrReturnMessage() throws Exception {
        MvcResult result = mockMvc.perform(post("/auth")
                .content("{\"name\": \"Petya\", \"password\": 100}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String token = result.getResponse().getContentAsString();
        token = token.replace("{\"token\":\"", "").replace("\"}", "");

        mockMvc.perform(put("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer_" + token)
                        .content("{\"name\": \"Petya\", \"description\": \"New message\"}")
                )
                .andExpect(jsonPath("$.name").value("Petya"))
                .andExpect(jsonPath("$.description").value("New message"))
                .andExpect(status().isCreated());

    }
}