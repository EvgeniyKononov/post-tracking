package org.example.visit.mapper;

import org.example.office.model.Office;
import org.example.post.model.Post;
import org.example.visit.model.Visit;

import java.time.LocalDateTime;

public class VisitMapper {
    public static Visit toNewVisit(Post post, Office office) {
        return Visit.builder()
                .post(post)
                .office(office)
                .arrival(LocalDateTime.now())
                .build();
    }
}
