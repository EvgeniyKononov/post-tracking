package org.example.office.service;

import org.example.office.model.Office;

import java.util.Optional;

public interface OfficeService {
    Office createOffice(Office office);

    Optional<Office> findByIndex(Integer index);
}
