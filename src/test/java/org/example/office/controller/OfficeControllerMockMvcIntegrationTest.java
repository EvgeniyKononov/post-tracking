package org.example.office.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.office.model.Office;
import org.example.office.service.OfficeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OfficeController.class)
class OfficeControllerMockMvcIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OfficeService officeService;
    private Office office;

    @BeforeEach
    void setUp() {
        office = Office.builder()
                .index(123456)
                .address("Russia, Moscow, Red Square, 1")
                .name("Center")
                .build();
    }

    @SneakyThrows
    @Test
    void create_whenInvokeWithModel_thenStatusCreatedAndReturnModel() {
        when(officeService.createOffice(office)).thenReturn(office);

        String result = mockMvc.perform(post("/offices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(office)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(officeService).createOffice(office);
        assertEquals(objectMapper.writeValueAsString(office), result);
    }

    @SneakyThrows
    @Test
    void create_whenInvokeNullIndex_thenStatusBadRequest() {
        office.setIndex(null);

        mockMvc.perform(post("/offices")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(office)))
                .andExpect(status().isBadRequest());

        verify(officeService, never()).createOffice(office);
    }
}