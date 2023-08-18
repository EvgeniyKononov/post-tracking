package org.example.visit.service;

import org.example.visit.model.Visit;

public interface VisitService {
    Visit createVisit(Long postId, Integer index);

    Visit amendVisit(Long postId, Integer index);
}
