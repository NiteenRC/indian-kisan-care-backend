package com.nc.med.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.mapper.SalesOrderSearch;
import com.nc.med.model.SalesOrder;
import com.nc.med.model.SalesOrderDetail;
import com.nc.med.service.ProductService;
import com.nc.med.service.SalesOrderDetailService;
import com.nc.med.service.SalesOrderService;
import com.nc.med.util.ValidationProperties;

@RestController
@RequestMapping("/salesOrder")
@Validated
public class SalesOrderController {
	public static final Logger LOGGER = LoggerFactory.getLogger(SalesOrderController.class);

	@Autowired
	public SalesOrderService orderService;

	@Autowired
	private SalesOrderDetailService orderDetailService;

	@Autowired
	ProductService productService;

	@Autowired
	private ValidationProperties validationProperties;

	@PostMapping
	public ResponseEntity<?> addOrderList(@RequestBody SalesOrder salesOrder) {
		salesOrder.setTotalQty(salesOrder.getSalesOrderDetail().size());
		SalesOrder salesOrderRes = orderService.saveOrder(salesOrder);

		for (SalesOrderDetail salesOrderDetail : salesOrder.getSalesOrderDetail()) {
			salesOrderDetail.setSalesOrder(salesOrderRes);
			try {
				orderDetailService.saveSalesOrderDetail(salesOrderDetail);
			} catch (Exception e) {
				e.printStackTrace();
				orderService.deleteOrder(salesOrderRes);
				return ResponseEntity.ok(new CustomErrorTypeException(validationProperties.getStock()));
			}
		}
		return ResponseEntity.ok(salesOrderRes);
	}

	@GetMapping
	public ResponseEntity<?> fetchAllOrderList() {
		return ResponseEntity.ok(orderService.findAllOrders());
	}

	@GetMapping("/customer/balance/{customerID}")
	public ResponseEntity<?> fetchCustomerBalanceByCustomerID(@PathVariable Integer customerID) {
		return new ResponseEntity<>(orderService.findCustomerBalanceByCustomer(customerID), HttpStatus.OK);
	}

	@GetMapping("/customer")
	public ResponseEntity<?> fetchCustomerBalance() {
		return ResponseEntity.ok(orderService.findAllCustomersBalance());
	}

	@GetMapping("/customer/balance")
	public ResponseEntity<?> fetchCustomerBalanceSheet() {
		return ResponseEntity.ok(orderService.findCurrentBalanceByCustomers());
	}

	@GetMapping("/customer/{customerID}")
	public ResponseEntity<?> fetchAllByCustomer(@PathVariable Integer customerID) {
		return ResponseEntity.ok(orderService.findAllByCustomer(customerID));
	}

	@GetMapping("/barChart")
	public ResponseEntity<?> fetchBarChart() throws ParseException {
		return ResponseEntity.ok(orderService.findBarChartModels());
	}

	@GetMapping("/product")
	public ResponseEntity<?> fetchSalesOrderProductWiseSale() throws ParseException {
		return new ResponseEntity<>(orderDetailService.salesOrderDetailProductWise(), HttpStatus.OK);
	}

	@GetMapping("/sales-order-search")
	public ResponseEntity<?> fetchSalesOrderProduct(@RequestBody SalesOrderSearch salesOrderSearch)
			throws ParseException {
		LOGGER.info("criteria: {}", salesOrderSearch.getCustomerId());
		return new ResponseEntity<>(orderService.salesOrderDetailSearch(salesOrderSearch), HttpStatus.OK);
	}

	@DeleteMapping("/{orderID}")
	public ResponseEntity<?> deleteOrder(@PathVariable Integer orderID) {
		SalesOrder order = orderService.findByOrderID(orderID);
		if (order == null) {
			return new ResponseEntity<>(new CustomErrorTypeException("orderID: " + orderID + " not found."),
					HttpStatus.NOT_FOUND);
		}
		orderService.deleteOrder(order);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PostMapping("Not yet")
	public ResponseEntity<?> removeOrderList(@RequestBody List<SalesOrder> orders) {
		return null;// orderService.removeFromOrder(orders);
	}
}