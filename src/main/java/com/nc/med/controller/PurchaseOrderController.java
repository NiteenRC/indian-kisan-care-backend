package com.nc.med.controller;

import java.util.Collections;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.mapper.BalancePayment;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.repo.SupplierRepo;
import com.nc.med.service.PurchaseOrderDetailService;
import com.nc.med.service.PurchaseOrderService;

@RestController
@RequestMapping("/purchaseOrder")
@Validated
public class PurchaseOrderController {
	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);

	@Autowired
	private PurchaseOrderService orderService;

	@Autowired
	private PurchaseOrderDetailService orderDetailService;

	@Autowired
	private SupplierRepo supplierRepo;

	@PostMapping
	public ResponseEntity<?> addOrderList(@RequestBody PurchaseOrder purchaseOrder) {
		PurchaseOrder purchaseOrderRes = orderService.saveOrder(purchaseOrder);

		purchaseOrder.getPurchaseOrderDetail().stream().forEach(purchaseOrderDetail -> {
			purchaseOrderDetail.setPurchaseOrder(purchaseOrderRes);
			orderDetailService.savePurchaseOrderDetail(purchaseOrderDetail);
		});
		return new ResponseEntity<>(purchaseOrderRes, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> updateOrderList(@RequestBody BalancePayment balancePayment) {
		PurchaseOrder order = new PurchaseOrder();
		order.setSupplier(supplierRepo.findById(balancePayment.getId()).get());
		order.setAmountPaid(balancePayment.getPayAmount());
		order.setCurrentBalance(-balancePayment.getPayAmount());
		order.setPurchaseOrderDetail(Collections.emptyList());
		order.setBillDate(new Date());
		PurchaseOrder purchasesOrderRes = orderService.saveOrder(order);
		return ResponseEntity.ok(purchasesOrderRes);
	}

	@GetMapping
	public ResponseEntity<?> fetchAllOrderList() {
		return ResponseEntity.ok(orderService.findAllOrders());
	}

	@GetMapping("/supplier/{supplierID}")
	public ResponseEntity<?> fetchCustomerBalance(@PathVariable Long supplierID) {
		return new ResponseEntity<>(orderService.findSupplierBalanceBySupplier(supplierID), HttpStatus.OK);
	}

	@GetMapping("/supplier")
	public ResponseEntity<?> fetchSupplierBalance() {
		return new ResponseEntity<>(orderService.findAllSuppliersBalance(), HttpStatus.OK);
	}

	@GetMapping("/supplier/balance")
	public ResponseEntity<?> fetchSupplierBalanceSheet() {
		return new ResponseEntity<>(orderService.findCurrentBalanceBySuppliers(), HttpStatus.OK);
	}

	@DeleteMapping("/{orderID}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long orderID) {
		PurchaseOrder order = orderService.findByOrderID(orderID);
		if (order == null) {
			return new ResponseEntity<>(new CustomErrorTypeException("orderID: " + orderID + " not found."),
					HttpStatus.NOT_FOUND);
		}
		orderService.deleteOrder(order);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PostMapping("Not yet")
	public ResponseEntity<?> removeOrderList(@RequestBody List<PurchaseOrder> orders) {
		return null;// orderService.removeFromOrder(orders);
	}
}
