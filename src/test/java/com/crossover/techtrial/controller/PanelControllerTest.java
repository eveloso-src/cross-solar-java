package com.crossover.techtrial.controller;

import java.time.Clock;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;

/**
 * PanelControllerTest class will test all APIs in PanelController.java.
 * 
 * @author Crossover
 *
 */

 @RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelControllerTest {

	MockMvc mockMvc;

	@Mock
	private PanelController panelController;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(panelController).build();
	}

	@Test
	public void testPanelShouldBeRegistered() throws Exception {

		HttpEntity<Object> panel = getHttpEntity("{\"serial\": \"232323\", \"longitude\": \"54.123232\","
				+ " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
		ResponseEntity<Panel> response = template.postForEntity("/api/register", panel, Panel.class);
		Assert.assertEquals(202, response.getStatusCode().value());
	}

	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}

	@Test
	public void testSaveHourlyElectricity() throws Exception {
		Clock clock = Clock.systemDefaultZone();
		LocalDate readingAt = LocalDate.now(clock);

		String panelSerial = "1234567890123456";
		HttpEntity<Object> hourly = getHttpEntity("{\"panel\": \"1\", \"generatedElectricity\": \"100\","
				+ " \"readingAt\": \""+ readingAt + "\" }");

		ResponseEntity<HourlyElectricity> response = template.postForEntity("/api/panels/" + panelSerial + "/hourly", hourly,
				HourlyElectricity.class);
		Assert.assertEquals(202, response.getStatusCode().value());
	}
	
	@Test
	public void testhourlyElectricity() throws Exception {

		String panelSerial = "1234567890123456";

		ResponseEntity<Page<HourlyElectricity>> response = 
				template.getForEntity("/api/panels/" + panelSerial + "/hourly", null,
						Page.class);
		Assert.assertEquals(202, response.getStatusCode().value());
	}
}
