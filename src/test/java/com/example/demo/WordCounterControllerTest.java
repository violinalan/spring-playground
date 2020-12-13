package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(WordCounterController.class)
@SpringBootTest
public class WordCounterControllerTest {

    @Autowired
    WordCounter wordCounter;

//    Comment IN following section for 3rd (MockBean) test

//    @MockBean
//    WordCounter wordCounter2;

//    @BeforeEach
//    public void setup() throws JsonProcessingException {
//        HashMap<String,Integer> map = new HashMap<>();
//        map.put("word", 2);
//        when(wordCounter2.count("text")).thenReturn(map);
//    }

    @Test
    public void unitTestWordCounter() {
        WordCounter wordCounter1 = new WordCounter();

        assertEquals(2, wordCounter1.count("A brown cow jumps over a brown fox").get("brown"));
        assertEquals(1, wordCounter1.count("A brown cow jumps over a brown fox").get("cow"));
    }

    @Test
    public void testWordCounterAutoWired() {
        assertEquals(2, wordCounter.count("A brown cow jumps over a brown fox").get("brown"));
        assertEquals(1, wordCounter.count("A brown cow jumps over a brown fox").get("cow"));
    }

//    @Test
//    public void testWordCounterMockBean() {
//        assertEquals(2, wordCounter2.count("text").get("word"));
//    }
}
