package com.nc.med.controller;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_PDF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.report.InvoiceService;
import com.nc.med.report.MockOrderService;
import com.nc.med.report.OrderModel;

@RestController
@RequestMapping("/invoice")
@Validated
public class InvoiceController {

	static private Logger logger = LogManager.getLogger(InvoiceController.class);

	@Resource
	private MockOrderService mockOrderService;
	@Resource
	private InvoiceService invoiceService;

	// generate invoice pdf
	// value = "/{startDate}/{endDate}",
	// @PathVariable String startDate, @PathVariable String endDate,
	@GetMapping // (produces = "application/pdf")
	public ResponseEntity<InputStreamResource> invoiceGenerate(
			@RequestParam(name = "code", defaultValue = "XYZ123456789") String code,
			@RequestParam(name = "lang", defaultValue = "en") String lang) throws IOException {
		logger.info("Start invoice generation...");
		final OrderModel order = mockOrderService.getOrderByCode(code);
		final File invoicePdf = invoiceService.generateInvoiceFor(order, Locale.forLanguageTag(lang));
		logger.info("Invoice generated successfully...");

		final HttpHeaders httpHeaders = getHttpHeaders(code, lang, invoicePdf);
		return new ResponseEntity<>(new InputStreamResource(new FileInputStream(invoicePdf)), httpHeaders, OK);
	}

	private HttpHeaders getHttpHeaders(String code, String lang, File invoicePdf) {
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(APPLICATION_PDF);
		respHeaders.setContentLength(invoicePdf.length());
		respHeaders.setContentDispositionFormData("attachment", format("%s-%s.pdf", code, lang));
		return respHeaders;
	}
}
