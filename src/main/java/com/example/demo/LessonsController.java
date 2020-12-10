package com.example.demo;

import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/lessons")
public class LessonsController {
    private final LessonRepository repository;

    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @GetMapping("{id}")
    public Optional<Lesson> get(@PathVariable long id) {
        return this.repository.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        this.repository.deleteById(id);
    }

    @PatchMapping("{id}")
    public Lesson patch(@PathVariable long id, @RequestBody Lesson lesson) {
        Lesson lessonToUpdate = this.repository.findById(id).get();
        lessonToUpdate.setTitle(lesson.getTitle());
        lessonToUpdate.setDeliveredOn(lesson.getDeliveredOn());
        this.repository.save(lessonToUpdate);
        return lessonToUpdate;
    }
}
