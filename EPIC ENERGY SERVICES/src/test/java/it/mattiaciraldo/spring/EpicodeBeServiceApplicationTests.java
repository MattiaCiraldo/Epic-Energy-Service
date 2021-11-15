package it.mattiaciraldo.spring;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EpicodeBeServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetAllComune() throws Exception {
		this.mockMvc.perform(get("/comunecontroller/getall?pageNumber=0&pageSize=10000")).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void testGetComuneByNome() throws Exception {
		this.mockMvc.perform(get("/comunecontroller/getall?pageNumber=0&pageSize=10000")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().string(containsString("Bronte")));
	}

	@Test
	void testGetByNomeContatto() throws Exception {
		this.mockMvc.perform(get("/clientecontroller/getall")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Aldo")));
	}
}
