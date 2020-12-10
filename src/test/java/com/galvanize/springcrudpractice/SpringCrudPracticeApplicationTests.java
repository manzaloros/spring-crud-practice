package com.galvanize.springcrudpractice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringCrudPracticeApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	UserRepository repository;

	@Test
	@Transactional
	@Rollback
	public void testGetAll() throws Exception {
		User user = new User();
		user.setEmail("test_email@fagetaboutit.com");
		repository.save(user);

		MockHttpServletRequestBuilder request = get("/users")
				.contentType(MediaType.APPLICATION_JSON);

		this.mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id",
						equalTo(user.getId().intValue()) ));
	}

	@Test
	@Transactional
	@Rollback
	public void testAddUser() throws Exception {
		User user = new User();
		user.setEmail("test_email@thiswillhavepassword.com");
		user.setPassword("this_is_a_password");

		ObjectWriter ow =
				new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(user);

		System.out.println("JSON output" + json);

		MockHttpServletRequestBuilder request = post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);

		this.mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", instanceOf(Number.class)));
	}
}
