package org.example.office.dao;

import org.example.office.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository  extends JpaRepository<Office, Integer> {
}
