package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndexEndpoint() throws Exception {
        this.mvc.perform(get("/").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from Spring!"));
    }

    @Test
    public void testGetPi() throws Exception {
        this.mvc.perform(get("/math/pi").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("3.141592653589793"));
    }

    @Test
    public void testVolume() throws Exception {
        this.mvc.perform(post("/math/volume/3/4/5").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("The volume of a 3x4x5 rectangle is 60"));
    }

    @Test
    public void testAreaCircle() throws Exception {
        MockHttpServletRequestBuilder request = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "circle")
                .param("radius", "4");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Area of a circle with a radius of 4 is 50.26544"));
    }

    @Test
    public void testAreaRectangle() throws Exception {
        MockHttpServletRequestBuilder request = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "rectangle")
                .param("width", "4")
                .param("height", "7");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Area of a 4x7 rectangle is 28"));
    }

    @Test
    public void testAreaInvalid() throws Exception {
        MockHttpServletRequestBuilder request = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "rectangle")
                .param("radius", "5");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid"));
    }

    @Test
    public void testGetFlight() throws Exception {
        this.mvc.perform(
                get("/flights/flight")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Departs", is("2017-04-21 07:04")))
                .andExpect(jsonPath("$.Tickets[0].Passenger.FirstName", is("Some name")))
                .andExpect(jsonPath("$.Tickets[0].Passenger.LastName", is("Some other name")))
                .andExpect(jsonPath("$.Tickets[0].Price", is(200)));
    }

    @Test
    public void testGetListOfFlights() throws Exception {
        this.mvc.perform(
                get("/flights")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].Departs", is("2017-04-21 07:04")))
                .andExpect(jsonPath("$[0].Tickets[0].Passenger.FirstName", is("Some name")))
                .andExpect(jsonPath("$[0].Tickets[0].Passenger.LastName", is("Some other name")))
                .andExpect(jsonPath("$[0].Tickets[0].Price", is(200)))

                .andExpect(jsonPath("$[1].Departs", is("2017-04-21 07:04")))
                .andExpect(jsonPath("$[1].Tickets[0].Passenger.FirstName", is("Some other name")))
                .andExpect(jsonPath("$[1].Tickets[0].Price", is(400)));
    }

    @Test
    public void testFlightsTicketsTotalAsStringLiteral() throws Exception {
        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"Departs\":\"2017-04-21 07:04\",\"Tickets\":[{\"Passenger\":{\"FirstName\":\"Some name\",\"LastName\":\"Some other name\"},\"Price\":200}]},{\"Departs\":\"2017-04-21 07:04\",\"Tickets\":[{\"Passenger\":{\"FirstName\":\"Some other name\"},\"Price\":400}]}]");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("600"));
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFlightsTicketsTotalWithJackson() throws Exception {
        HashMap<String, Object> person1 = new HashMap<>() {
            {
                put("FirstName", "Some name");
                put("LastName", "Some other name");
            }
        };

        HashMap<String, Object> ticket1 = new HashMap<>() {
            {
                put("Passenger", person1);
                put("Price", 200);
            }
        };

        HashMap<String, Object> flight1 = new HashMap<>() {
            {
                put("Departs", "2017-04-21 07:04");
                put("Tickets", ticket1);
            }
        };

        HashMap<String, Object> person2 = new HashMap<>() {
            {
                put("FirstName", "Some name");
                put("LastName", "Some other name");
            }
        };

        HashMap<String, Object> ticket2 = new HashMap<>() {
            {
                put("Passenger", person2);
                put("Price", 400);
            }
        };

        HashMap<String, Object> flight2 = new HashMap<>() {
            {
                put("Departs", "2017-04-21 07:04");
                put("Tickets", ticket1);
            }
        };

        List<HashMap> data = new ArrayList<HashMap>(Arrays.asList(flight1, flight2));

        String json = objectMapper.writeValueAsString(data);

        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("600"));
    }

    @Test
    public void testFlightsTicketsTotalFromFile() throws Exception {
        String json = getJSON("/data.json");

        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("600"));
    }

    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }
}