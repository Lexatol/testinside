package info.the_inside.test;

import info.the_inside.test.controllers.AuthController;
import info.the_inside.test.controllers.MessageController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestinsideApplicationTests {

	@Autowired
	private MessageController messageController;

	@Autowired
	private AuthController authController;

	@Test
	void testMessageController() {
		Assertions.assertThat(messageController).isNotNull();
	}

	@Test
	void testAuthController() {
		Assertions.assertThat(authController).isNotNull();
	}

}
