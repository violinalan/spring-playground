package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class WordCounter {

    private final MyConfig config;

    public WordCounter(MyConfig config) {
        this.config = config;
    }

    public HashMap<String, Integer> count(String message) {
        HashMap<String, Integer> map = new HashMap<>();
        if(!config.isCaseSensitive()) message = message.toLowerCase();
        message = message.replaceAll("[,.!?]", "");
        String[] words = message.split(" ");
        for( String word : words ) {
            if(!config.getWords().getSkip().contains(word)) {
                if (map.containsKey(word)) map.put(word, map.get(word) + 1);
                else map.put(word, 1);
            }
        }
        return map;
    }
}
