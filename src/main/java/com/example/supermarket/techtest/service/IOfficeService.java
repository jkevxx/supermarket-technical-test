package com.example.supermarket.techtest.service;

import com.example.supermarket.techtest.dto.OfficeDTO;

import java.util.List;

public interface IOfficeService {

    List<OfficeDTO> getOffices();
    OfficeDTO createOffice(OfficeDTO officeDTO);
    OfficeDTO updateOffice(Long id, OfficeDTO officeDTO);
    void deleteOffice(Long id);

}
