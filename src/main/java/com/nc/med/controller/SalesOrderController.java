package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.mapper.BalancePayment;
import com.nc.med.mapper.SalesOrderSearch;
import com.nc.med.model.SalesOrder;
import com.nc.med.model.SalesOrderDetail;
import com.nc.med.repo.CustomerRepo;
import com.nc.med.repo.SalesOrderRepo;
import com.nc.med.service.SalesOrderDetailService;
import com.nc.med.service.SalesOrderService;
import com.nc.med.util.ValidationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/salesOrder")
@Validated
public class SalesOrderController {
    public static final Logger LOGGER = LoggerFactory.getLogger(SalesOrderController.class);
    private final SalesOrderService orderService;
    private final SalesOrderDetailService orderDetailService;
    private final ValidationProperties validationProperties;
    private final CustomerRepo customerRepo;
    private final SalesOrderRepo salesOrderRepo;

    public SalesOrderController(SalesOrderService orderService, SalesOrderDetailService orderDetailService, ValidationProperties validationProperties, CustomerRepo customerRepo, SalesOrderRepo salesOrderRepo) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.validationProperties = validationProperties;
        this.customerRepo = customerRepo;
        this.salesOrderRepo = salesOrderRepo;
    }

    @PostMapping
    public ResponseEntity<?> addOrderList(@RequestBody SalesOrder salesOrder) {
        salesOrder.setTotalQty(salesOrder.getSalesOrderDetail().size());
        if (salesOrder.getBillDate() == null) {
            salesOrder.setBillDate(new Date());
        }
        SalesOrder salesOrderRes = orderService.saveOrder(salesOrder);
        double totalProfit = 0d;
        for (SalesOrderDetail salesOrderDetail : salesOrder.getSalesOrderDetail()) {
            salesOrderDetail.setSalesOrder(salesOrderRes);
            try {
                SalesOrderDetail orderDetail = orderDetailService.saveSalesOrderDetail(salesOrderDetail);
                totalProfit += orderDetail.getProfit();
            } catch (Exception e) {
                e.printStackTrace();
                orderService.deleteOrder(salesOrderRes);
                return ResponseEntity.ok(new CustomErrorTypeException(validationProperties.getStock()));
            }
        }
        salesOrderRes.setTotalProfit(totalProfit);
        salesOrderRes = salesOrderRepo.save(salesOrderRes);
        return ResponseEntity.ok(salesOrderRes);
    }

    @PutMapping
    public ResponseEntity<?> updateOrderList(@RequestBody BalancePayment balancePayment) {
        SalesOrder order = new SalesOrder();
        order.setCustomer(customerRepo.findById(balancePayment.getId()).orElse(null));
        order.setAmountPaid(balancePayment.getPayAmount());
		order.setCurrentBalance(-balancePayment.getPayAmount());
		order.setPreviousBalance(balancePayment.getCurrentBalance() - balancePayment.getPayAmount());
        order.setSalesOrderDetail(Collections.emptyList());
        order.setBillDate(new Date());
        order.setStatus(balancePayment.getStatus());
        SalesOrder salesOrderRes = orderService.saveOrder(order);
        return ResponseEntity.ok(salesOrderRes);
    }

    @GetMapping
    public ResponseEntity<?> fetchAllOrderList() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @GetMapping("/customer/balance/{customerID}")
    public ResponseEntity<?> fetchCustomerBalanceByCustomerID(@PathVariable Long customerID) {
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
    public ResponseEntity<?> fetchAllByCustomer(@PathVariable Long customerID) {
        return ResponseEntity.ok(orderService.findAllByCustomer(customerID));
    }

    @GetMapping("/barChart")
    public ResponseEntity<?> fetchBarChart() {
        return ResponseEntity.ok(orderService.findBarChartModels());
    }

    @GetMapping("/stock-book-by-date")
    public ResponseEntity<?> fetchStockDataByDate(@RequestParam String productName, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start;
        LocalDate end;
        if (startDate.equalsIgnoreCase("null") && endDate.equalsIgnoreCase("null")) {
            start = null;
            end = null;
        } else {
            start = Instant.ofEpochMilli(Long.parseLong(startDate)).atZone(ZoneId.of("UTC")).toLocalDate();
            end = Instant.ofEpochMilli(Long.parseLong(endDate)).atZone(ZoneId.of("UTC")).toLocalDate();
        }
        return ResponseEntity.ok(orderService.findStockDataByDateAndProduct(productName, start, end));
    }

    @GetMapping("/product")
    public ResponseEntity<?> fetchSalesOrderProductWiseSale() {
        return new ResponseEntity<>(orderDetailService.salesOrderDetailProductWise(), HttpStatus.OK);
    }

    @GetMapping("/sales-order-search")
    public ResponseEntity<?> fetchSalesOrderProduct(@RequestBody SalesOrderSearch salesOrderSearch) {
        LOGGER.info("criteria: {}", salesOrderSearch.getCustomerId());
        return new ResponseEntity<>(orderService.salesOrderDetailSearch(salesOrderSearch), HttpStatus.OK);
    }

    @DeleteMapping("/{orderID}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderID) {
        SalesOrder order = orderService.findByOrderID(orderID);
        if (order == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("orderID: " + orderID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        orderService.deleteOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/details/{orderID}")
    public ResponseEntity<?> deleteOrderDetails(@PathVariable Long orderID) {
        SalesOrderDetail order = orderDetailService.findById(orderID);
        if (order == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("orderID: " + orderID + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        List<SalesOrderDetail> salesOrderDetails = orderService.deleteOrderDetails(order);
        return new ResponseEntity<>(salesOrderDetails, HttpStatus.OK);
    }

    @GetMapping("/details/{orderID}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderID) {
        if (orderID == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("orderID: " + orderID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        SalesOrderDetail order = orderDetailService.findById(orderID);
        SalesOrder salesOrder = orderService.findByOrderID(order.getSalesOrder().getSalesOrderID());
        return new ResponseEntity<>(salesOrder, HttpStatus.OK);
    }
}
