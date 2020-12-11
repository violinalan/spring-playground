package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WordCounterController {

    private WordCounter wordCounter;

    public WordCounterController(WordCounter wordCounter) {
        this.wordCounter = wordCounter;
    }

    @PostMapping(value = "/words/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public String countWords(@RequestBody String message) throws JsonProcessingException {
        HashMap<String,Integer> map = this.wordCounter.count(message);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(map);
    }
}
