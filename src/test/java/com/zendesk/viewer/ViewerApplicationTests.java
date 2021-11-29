package com.zendesk.viewer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zendesk.viewer.model.Ticket;
import com.zendesk.viewer.service.ViewerService;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ViewerApplicationTests {
	

	@Autowired
	private ViewerService viewerService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void init() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}


	@Test
	public void testListAllTickets() throws Exception {
		given().
				port(8090).
				when().
				get("/ticketViewer").
				then().
				assertThat().
				statusCode(200).
				assertThat().
				contentType(ContentType.HTML);
	}

	@Test
	public void testGetIndividualTicket() throws Exception {
		given().
				port(8090).
				when().
				get("/ticketViewer/tickets/1").
				then().
				assertThat().
				statusCode(200).assertThat()
				.contentType(ContentType.HTML);
	}

	@Test
	public void testGetTickets() throws JsonProcessingException {

		List<Ticket> ticketList = viewerService.getTicketList(1);
		assertEquals(ticketList.size(),25);

	}

	@Test
	public void testGetTotalTickets() {

		int ticketCount = viewerService.getTotalTickets(1);
		assertEquals(ticketCount,99);

	}

	@Test
	public void testGetPaginatedTicketList(){
		List<Ticket> ticketList = new ArrayList<>();
		Page<Ticket> ticketPage = viewerService.getPaginatedTicketList(PageRequest.of(0, 25), ticketList);

		assertEquals(ticketPage.getTotalPages(),0);
	}

	@Test
	public void testTicketViewer() throws Exception {

		mockMvc.perform(get("/ticketViewer")).andExpect(status().isOk()).andExpect(view().name("listTickets.html"));

	}

	@Test
	public void testIndividualTicketViewer() throws Exception {

		mockMvc.perform(get("/ticketViewer/tickets/1")).andExpect(status().isOk()).andExpect(view().name("displayIndividualTicket.html"));

	}





}
