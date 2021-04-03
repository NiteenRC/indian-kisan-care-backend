package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Company;
import com.nc.med.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@Validated
public class CompanyController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    public CompanyService companyService;

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        if (company == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Company is not saved"), HttpStatus.NOT_FOUND);
        }

        Company company1 = companyService.findByCompanyName(company.getCompanyName());
        if (company1 != null) {
            return new ResponseEntity<>(new CustomErrorTypeException("Company name already exist!!"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companyService.saveCompany(company), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCompany(@RequestBody Company company) {
        LOGGER.info("company " + company.getCompanyName());
        return new ResponseEntity<>(companyService.saveCompany(company), HttpStatus.CREATED);
    }

    @DeleteMapping("/{companyID}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long companyID) {
        Company company = companyService.findByCompanyID(companyID);
        if (company == null) {
            return new ResponseEntity<>(
                    new CustomErrorTypeException("Company with companyID " + companyID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        companyService.deleteCompany(company);
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
