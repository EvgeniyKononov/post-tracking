package org.example.visit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.office.model.Office;
import org.example.post.model.Post;
import org.example.visit.model.Visit;
import org.example.visit.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitController.class)
class VisitControllerMockMvcIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VisitService visitService;
    private Visit visit;
    LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        visit = Visit.builder()
                .post(new Post())
                .office(new Office())
                .build();
    }

    @SneakyThrows
    @Test
    void createVisit_whenInvokeWithCorrectParams_thenStatusCreatedAndReturnModel() {
        Long postId = 1L;
        Integer officeIndex = 123456;
        when(visitService.createVisit(postId, officeIndex)).thenReturn(visit);

        String result = mockMvc.perform(post("/visits/{index}/{postId}", officeIndex, postId)
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(visitService).createVisit(postId, officeIndex);
        assertEquals(objectMapper.writeValueAsString(visit), result);
    }

    @SneakyThrows
    @Test
    void createVisit_whenInvokeWithoutPathVariables_thenStatusNotFound() {
        Long postId = 1L;
        Integer officeIndex = 123456;
        mockMvc.perform(post("/visits/{postId}", postId)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(visitService, never()).createVisit(postId, officeIndex);
    }

    @SneakyThrows
    @Test
    void amendVisit_whenInvokeWithCorrectParams_thenStatusCreatedAndReturnModel() {
        Long postId = 1L;
        Integer officeIndex = 123456;
        when(visitService.amendVisit(postId, officeIndex)).thenReturn(visit);

        String result = mockMvc.perform(patch("/visits/{officeIndex}/{postId}", officeIndex, postId)
                        .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(visitService).amendVisit(postId, officeIndex);
        assertEquals(objectMapper.writeValueAsString(visit), result);
    }

    @SneakyThrows
    @Test
    void amendVisit_whenInvokeWithoutPathVariables_thenStatusNotFound() {
        Long postId = 1L;
        Integer officeIndex = 123456;
        mockMvc.perform(patch("/visits")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(visitService, never()).amendVisit(postId, officeIndex);
    }
}