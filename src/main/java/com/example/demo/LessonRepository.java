package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
    Lesson findByTitle(String title);

    @Query(value = "SELECT * FROM lessons WHERE (delivered_on BETWEEN :startDate AND :endDate)", nativeQuery = true)
    Iterable<Lesson> findLessonsBetween(@Param("startDate") String date1, @Param("endDate") String date2);
}
