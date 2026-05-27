package com.example.supermarket.techtest.service;

import com.example.supermarket.techtest.dto.OfficeDTO;
import com.example.supermarket.techtest.model.Office;
import com.example.supermarket.techtest.repository.OfficeRepository;
import com.example.supermarket.techtest.service.OfficeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Office Service Test")
class OfficeServiceTest {

    @Mock
    private OfficeRepository officeRepository;

    @InjectMocks
    private OfficeService officeService;

    @Test
    void testGetOffices() {
        // Arrange
        Office office1 = Office.builder().id(1L).name("Office 1").direction("Street 1").build();
        Office office2 = Office.builder().id(2L).name("Office 2").direction("Street 2").build();
        when(officeRepository.findAll()).thenReturn(List.of(office1, office2));

        // Act
        List<OfficeDTO> result = officeService.getOffices();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Office 1", result.get(0).getName());
        assertEquals("Office 2", result.get(1).getName());
    }
}