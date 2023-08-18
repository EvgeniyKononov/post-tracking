package org.example.office.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.office.model.Office;
import org.example.office.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "/offices")
public class OfficeController {
    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Office createOffice(@Valid @RequestBody Office office) {
        log.info("Creating office = {}", office);
        return officeService.createOffice(office);
    }
}
