package com.nc.med.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nc.med.mapper.SupplierBalanceSheet;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.Supplier;
import com.nc.med.repo.PurchaseOrderRepo;
import com.nc.med.repo.SupplierRepo;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

	@Autowired
	private PurchaseOrderRepo purchaseOrderRepo;

	@Autowired
	private SupplierRepo supplierRepo;

	@Override
	public PurchaseOrder saveOrder(PurchaseOrder order) {
		double sum = 0.0d;
		int totalQty = order.getPurchaseOrderDetail().stream().mapToInt(x -> x.getQtyOrdered()).sum();
		order.setTotalQty(totalQty);

		Supplier supplier = order.getSupplier();
		Supplier supplierObj = null;

		if (supplier.getSupplierName() == "") {
			List<Supplier> suppliers = supplierRepo.findAll();
			Long maxId = 1L;
			if (!suppliers.isEmpty()) {
				maxId += suppliers.stream().max(Comparator.comparing(Supplier::getId)).get().getId();
			}
			order.setSupplier(supplierRepo.save(new Supplier("UNKNOWN" + maxId)));
		} else {
			String supplierName = supplier.getSupplierName().toUpperCase();
			supplierObj = supplierRepo.findBySupplierNameContainingIgnoreCase(supplierName);

			if (supplierObj == null) {
				supplierObj = supplierRepo.save(new Supplier(supplierName));
			}
			order.setSupplier(supplierObj);

			try {
				sum = order.getCurrentBalance();
				sum += ((double) purchaseOrderRepo.findCurrentSum(supplierObj));
			} catch (Exception e) {
			}
		}
		// purchaseOrderRepo.updateAddress(supplier, sum);
		order.setPreviousBalance(sum);
		return purchaseOrderRepo.save(order);
	}

	@Override
	public PurchaseOrder findOrderByProductName(String productName) {
		return null;// purchaseOrderRepo.findByProductName(productName);
	}

	@Override
	public PurchaseOrder findByOrderID(Integer orderID) {
		return purchaseOrderRepo.findById(orderID).get();
	}

	@Override
	public void deleteOrder(PurchaseOrder orderID) {
		purchaseOrderRepo.delete(orderID);
	}

	@Override
	public List<PurchaseOrder> findAllOrders() {
		return purchaseOrderRepo.findAll(Sort.by("createdDate").descending());
	}

	@Override
	public List<PurchaseOrder> findByDates(String startDate, String endDate) throws ParseException {
		List<PurchaseOrder> orders = purchaseOrderRepo.findAll();

		try {
			Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);

			// return orders.stream().filter(order -> order.getCreatedDate().getTime() >=
			// fromDate.getTime()
			// && order.getCreatedDate().getTime() <=
			// toDate.getTime()).collect(Collectors.toList());
			return Collections.emptyList();
		} catch (ParseException e) {
			throw new ParseException("Please enter valid date formats", 0);
		}
	}

	@Override
	public double findSupplierBalanceBySupplier(Long supplierID) {
		List<PurchaseOrder> purchaseOrders = purchaseOrderRepo
				.findAmountBalanceBySupplier(supplierRepo.findById(supplierID).get());

		return purchaseOrders.stream().mapToDouble(purchaseOrder -> purchaseOrder.getCurrentBalance()).sum();
	}

	@Override
	public double findAllSuppliersBalance() {
		return purchaseOrderRepo.findAll().stream().mapToDouble(salesOrder -> salesOrder.getCurrentBalance()).sum();
	}

	@Override
	public List<SupplierBalanceSheet> findCurrentBalanceBySuppliers() {
		return purchaseOrderRepo.findAll().stream().collect(Collectors.groupingBy(PurchaseOrder::getSupplier,
				Collectors.summingDouble(PurchaseOrder::getCurrentBalance))).entrySet().stream().map(x -> {
					return new SupplierBalanceSheet(x.getKey(), x.getValue());
				}).collect(Collectors.toList());
	}
}
