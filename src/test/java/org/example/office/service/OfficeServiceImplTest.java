package org.example.office.service;

import org.example.exception.ConflictException;
import org.example.office.dao.OfficeRepository;
import org.example.office.model.Office;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfficeServiceImplTest {
    @Mock
    private OfficeRepository officeRepository;
    @InjectMocks
    private OfficeServiceImpl officeService;
    private Office expectedOffice;
    private Office actualOffice;
    private Integer index;
    private String address;
    private String name;


    @BeforeEach
    void setUp() {
        index = 123456;
        address = "Russia, Moscow, Red Square, 1";
        name = "Center";
        expectedOffice = Office.builder()
                .index(index)
                .address(address)
                .name(name)
                .build();
        actualOffice = new Office();
    }

    @Test
    void createOffice_whenInvokeWithCorrectIndex_thenReturnSavedOffice() {
        when(officeRepository.save(expectedOffice)).thenReturn(expectedOffice);

        actualOffice = officeService.createOffice(expectedOffice);

        assertEquals(expectedOffice, actualOffice);
        verify(officeRepository).save(expectedOffice);
    }

    @Test
    void createOffice_whenInvokeWithDuplicatedIndex_thenThrowConflictException() {
        Optional<Office> office = Optional.of(expectedOffice);
        when(officeRepository.findById(expectedOffice.getIndex())).thenReturn(office);

        assertThrows(ConflictException.class, () -> officeService.createOffice(expectedOffice));

        verify(officeRepository, never()).save(expectedOffice);
    }

    @Test
    void findByIndex_whenInvokeWithCorrectIndex_thenReturnOffice() {
        Optional<Office> office = Optional.of(expectedOffice);
        when(officeRepository.findById(index)).thenReturn(office);

        actualOffice = officeService.findByIndex(index).get();

        assertEquals(expectedOffice, actualOffice);
        verify(officeRepository).findById(index);
    }
}