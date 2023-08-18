package org.example.visit.dao;

import org.example.office.model.Office;
import org.example.post.model.Post;
import org.example.visit.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findFirstByPostAndOfficeOrderByArrivalDesc(Post post, Office office);
}
