package com.nc.med.report;

import com.nc.med.repo.SalesOrderDetailRepo;
import com.nc.med.repo.SalesOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MockOrderService {

    @Autowired
    private SalesOrderRepo salesOrderRepo;

    @Autowired
    private SalesOrderDetailRepo salesOrderDetailRepo;

    public OrderModel getOrderByCode(final String code) {
        return order(code);
    }

    private OrderModel order(String code) {
        return new OrderModel(code, address(), entries());
    }

    private AddressModel address() {
        return new AddressModel("#001", "Indian Kisan Care", "Shinal", "591303", "Athani", "Karnataka");
    }

    private List<OrderEntryModel> entries() {
        return salesOrderDetailRepo.findAll().stream()
                .map(x -> new OrderEntryModel(x.getProduct().getProductName(), x.getQtyOrdered(), x.getSalesPrice()))
                .collect(Collectors.toList());

        //.map(x -> new OrderEntryModel(x.getCustomer().getCustomerName(), x.getTotalQty(), x.getTotalPrice()))
    }
}
