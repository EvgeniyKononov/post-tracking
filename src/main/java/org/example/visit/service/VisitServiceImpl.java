package org.example.visit.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.NotFoundException;
import org.example.office.model.Office;
import org.example.office.service.OfficeService;
import org.example.post.model.Post;
import org.example.post.model.Status;
import org.example.post.service.PostService;
import org.example.visit.dao.VisitRepository;
import org.example.visit.mapper.VisitMapper;
import org.example.visit.model.Visit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.example.constant.Constant.*;

@Service
@Slf4j
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;
    private final PostService postService;
    private final OfficeService officeService;

    public VisitServiceImpl(VisitRepository visitRepository, PostService postService, OfficeService officeService) {
        this.visitRepository = visitRepository;
        this.postService = postService;
        this.officeService = officeService;
    }

    @Override
    @Transactional
    public Visit createVisit(Long postId, Integer index) {
        Post post = postService.findById(postId).orElseThrow(() -> new NotFoundException(NOT_FOUND_POST_ID_MSG));
        Office office = officeService.findByIndex(index).orElseThrow(() -> new NotFoundException(NOT_FOUND_INDEX_MSG));
        Visit visit = VisitMapper.toNewVisit(post, office);
        Visit savedVisit = visitRepository.save(visit);
        postService.changePostStatus(postId, Status.IN_TRANSIT);
        log.info("Saved visit  = {}", savedVisit);
        return savedVisit;
    }

    @Override
    @Transactional
    public Visit amendVisit(Long postId, Integer index) {
        Post post = postService.findById(postId).orElseThrow(() -> new NotFoundException(NOT_FOUND_POST_ID_MSG));
        Office office = officeService.findByIndex(index).orElseThrow(() -> new NotFoundException(NOT_FOUND_INDEX_MSG));
        Visit visit = visitRepository.findFirstByPostAndOfficeOrderByArrivalDesc(post, office)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_VISIT_MSG));
        visit.setDeparture(LocalDateTime.now());
        Visit savedVisit = visitRepository.save(visit);
        postService.changePostStatus(postId, Status.IN_PATH_TO_RECEIVER);
        log.info("Saved visit  = {}", savedVisit);
        return savedVisit;
    }
}
