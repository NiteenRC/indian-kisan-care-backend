package com.nc.med.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.Company;
import com.nc.med.service.CompanyService;

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

		Company companyObj = companyService.findByCompanyName(company.getCompanyName());
		if (companyObj != null) {
			return new ResponseEntity<>(new CustomErrorTypeException("Company name already exist!!"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(companyService.saveCompany(company), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> updateCompany(@RequestBody Company company) {
		LOGGER.info("company " + company.getCompanyName());
		Company companyObj = companyService.findByCompanyName(company.getCompanyName());
		if (companyObj == null || companyObj.getId() == company.getId()) {
			return new ResponseEntity<>(companyService.saveCompany(company), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new CustomErrorTypeException("Company name already exist!!"),
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
