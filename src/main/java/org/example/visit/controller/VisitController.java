package org.example.visit.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.visit.model.Visit;
import org.example.visit.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "/visits")
public class VisitController {
    private final VisitService visitService;

    @Autowired
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping(value = "/{index}/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Visit createVisit(@PathVariable Long postId, @PathVariable Integer index) {
        log.info("Register arriving post = {}, at office = {}", postId, index);
        return visitService.createVisit(postId, index);
    }

    @PatchMapping(value = "/{index}/{postId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Visit amendVisit(@PathVariable Long postId, @PathVariable Integer index) {
        log.info("Register departure post = {}, from office = {}", postId, index);
        return visitService.amendVisit(postId, index);
    }
}
