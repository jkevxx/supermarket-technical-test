package com.example.supermarket.techtest.service;

import com.example.supermarket.techtest.dto.OfficeDTO;
import com.example.supermarket.techtest.exception.NotFoundException;
import com.example.supermarket.techtest.mapper.Mapper;
import com.example.supermarket.techtest.model.Office;
import com.example.supermarket.techtest.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService implements IOfficeService{

    @Autowired
    private OfficeRepository officeRepository;

    @Override
    public List<OfficeDTO> getOffices() {
        return officeRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public OfficeDTO createOffice(OfficeDTO officeDTO) {

        Office office = Office.builder()
                .name(officeDTO.getName())
                .direction(officeDTO.getDirection())
                .build();

        return Mapper.toDTO(officeRepository.save(office));
    }

    @Override
    public OfficeDTO updateOffice(Long id, OfficeDTO officeDTO) {
        Office office = officeRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Office not found"));

        office.setName(office.getName());
        office.setDirection(office.getDirection());

        return Mapper.toDTO(officeRepository.save(office));
    }

    @Override
    public void deleteOffice(Long id) {
        if(!officeRepository.existsById(id)){
            throw new NotFoundException("Office not found");
        }

        officeRepository.deleteById(id);
    }
}
