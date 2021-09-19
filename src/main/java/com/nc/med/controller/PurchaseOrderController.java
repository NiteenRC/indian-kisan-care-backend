package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.mapper.BalancePayment;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.service.PurchaseOrderDetailService;
import com.nc.med.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/purchaseOrder")
@Validated
public class PurchaseOrderController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);
    private final PurchaseOrderService orderService;
    private final PurchaseOrderDetailService orderDetailService;

    public PurchaseOrderController(PurchaseOrderService orderService, PurchaseOrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @PostMapping
    public ResponseEntity<?> addOrderList(@RequestBody PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getBillDate() == null) {
            purchaseOrder.setBillDate(new Date());
        }
        PurchaseOrder purchaseOrderRes = orderService.saveOrder(purchaseOrder);

        purchaseOrder.getPurchaseOrderDetail().forEach(purchaseOrderDetail -> {
            purchaseOrderDetail.setPurchaseOrder(purchaseOrderRes);
            orderDetailService.savePurchaseOrderDetail(purchaseOrderDetail);
        });
        return new ResponseEntity<>(purchaseOrderRes, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateOrderList(@RequestBody BalancePayment balancePayment) {
        PurchaseOrder order = orderService.getPurchaseOrder(balancePayment);
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
}
