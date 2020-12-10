package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LessonsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    LessonRepository repository;

    @Test
    @Transactional
    @Rollback
    public void testCreateLesson() throws Exception {
        MockHttpServletRequestBuilder request = post("/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Alan_test\", \"deliveredOn\": \"2020-12-12\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class) ));
    }

    @Test
    @Transactional
    @Rollback
    public void testLessons() throws Exception {
        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Test1");
        repository.save(lesson1);

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Test2");
        repository.save(lesson2);

        MockHttpServletRequestBuilder request = get("/lessons")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(lesson1.getId().intValue()) ))
                .andExpect(jsonPath("$[1].id", equalTo(lesson2.getId().intValue()) ));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetLessonById() throws Exception {
        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Test1");
        repository.save(lesson1);

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Test2");
        repository.save(lesson2);

        Lesson lesson3 = new Lesson();
        lesson3.setTitle("Test3");
        repository.save(lesson3);

        MockHttpServletRequestBuilder request = get("/lessons/" + lesson2.getId().intValue())
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(lesson2.getId().intValue()) ));
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateLesson() throws Exception {
        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Test1");
        repository.save(lesson1);

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Test2");
        repository.save(lesson2);

        Lesson lesson3 = new Lesson();
        lesson3.setTitle("Test3");
        repository.save(lesson3);

        MockHttpServletRequestBuilder request = patch("/lessons/" + lesson2.getId().intValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Title\",\"deliveredOn\":\"2021-12-31\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Updated Title")));
    }
}