package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Company;
import com.nc.med.service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/company")
@Validated
@AllArgsConstructor
@Slf4j
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        if (company == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Company is not saved"), HttpStatus.NOT_FOUND);
        }
        String companyName = company.getCompanyName().toUpperCase();
        Company companyObj = companyService.findByCompanyName(companyName);
        if (companyObj != null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Company is already exist!!"),
                    HttpStatus.NOT_FOUND);
        }
        company.setCompanyName(companyName);
        return new ResponseEntity<>(companyService.saveCompany(company), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCompany(@RequestBody Company company) {
        log.info("company " + company.getCompanyName());
        String companyName = company.getCompanyName().toUpperCase();
        Company companyObj = companyService.findByCompanyName(companyName);
        if (companyObj == null || Objects.equals(companyObj.getId(), company.getId())) {
            company.setCompanyName(companyName);
            return new ResponseEntity<>(companyService.saveCompany(company), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomErrorTypeException("Company is already exist!!"),
                    HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/{companyID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCompany(@PathVariable Long companyID) {
        Company company = companyService.findByCompanyID(companyID);
        if (company == null) {
            return new ResponseEntity<>(
                    new CustomErrorTypeException("Company with companyID " + companyID + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        try {
            companyService.deleteCompany(company);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new CustomErrorTypeException("Company is already in use"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Company>> findAllCompanyList() {
        return new ResponseEntity<>(companyService.fetchAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{companyID}")
    public ResponseEntity<Company> findCompanyByID(@PathVariable Long companyID) {
        return new ResponseEntity<>(companyService.findByCompanyID(companyID), HttpStatus.OK);
    }
}
