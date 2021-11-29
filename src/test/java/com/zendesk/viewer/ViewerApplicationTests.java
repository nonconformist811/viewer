package com.zendesk.viewer;

import com.zendesk.viewer.model.Ticket;
import com.zendesk.viewer.service.ViewerService;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.*;


import javax.servlet.Filter;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ViewerApplicationTests {
	

	@Autowired
	private ViewerService viewerService;

	

	@Test
	public void testListAllTickets() throws Exception {
		given().
				port(8090).
				when().
				get("/ticketViewer").
				then().
				assertThat().
				statusCode(200).assertThat()
				.contentType(ContentType.HTML);
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
	public void testGetTickets() {

		List<Ticket> ticketList = viewerService.getTicketList(1);
		assertEquals(ticketList.size(),25);

	}

	@Test
	public void testGetTotalTickets() {

		int ticketCount = viewerService.getTotalTickets(1);
		assertEquals(ticketCount,102);

	}

	@Test
	public void testGetPaginatedTicketList(){
		List<Ticket> ticketList = new ArrayList<>();
		Page<Ticket> ticketPage = viewerService.getPaginatedTicketList(PageRequest.of(0, 25), ticketList);

		assertEquals(ticketPage.getTotalPages(),0);
	}








}
