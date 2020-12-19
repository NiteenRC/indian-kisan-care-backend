package com.nc.med.report;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nc.med.repo.SalesOrderRepo;

@Service
public class MockOrderService {

	@Autowired
	private SalesOrderRepo salesOrderRepo;

	public OrderModel getOrderByCode(final String code) {
		return order(code);
	}

	private OrderModel order(String code) {
		return new OrderModel(code, address(), entries());
	}

	private AddressModel address() {
		return new AddressModel("#001", "1st cross", "Belandur", "560001", "Bangalore", "Karnataka");
	}

	private List<OrderEntryModel> entries() {
		return salesOrderRepo.findAll().stream()
				.map(x -> new OrderEntryModel(x.getCustomer().getCustomerName(), x.getTotalQty(), x.getTotalPrice()))
				.collect(Collectors.toList());
	}
}
