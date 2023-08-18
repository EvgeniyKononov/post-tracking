package org.example.office.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.ConflictException;
import org.example.office.dao.OfficeRepository;
import org.example.office.model.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.constant.Constant.DUPLICATE_INDEX_MSG;

@Service
@Slf4j
public class OfficeServiceImpl implements OfficeService{
    private final OfficeRepository officeRepository;

    @Autowired
    public OfficeServiceImpl (OfficeRepository officeRepository){
        this.officeRepository = officeRepository;
    }
    @Override
    public Office createOffice(Office office) {
        checkIndex(office.getIndex());
        Office savedOffice = officeRepository.save(office);
        log.info("Office saved  = {}", savedOffice);
        return savedOffice;
    }

    @Override
    public Optional<Office> findByIndex(Integer index) {
        log.info("Searching office with index  = {}", index);
        return officeRepository.findById(index);
    }

    private void checkIndex(Integer index) {
        Optional<Office> office = officeRepository.findById(index);
        if (office.isPresent()) {
            throw new ConflictException(DUPLICATE_INDEX_MSG);
        }
    }
}
