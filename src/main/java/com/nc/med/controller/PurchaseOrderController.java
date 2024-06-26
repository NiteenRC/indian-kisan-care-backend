package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.mapper.BalancePayment;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.PurchaseOrderDetail;
import com.nc.med.service.PurchaseOrderDetailService;
import com.nc.med.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    public ResponseEntity<?> fetchAllOrderList(@RequestParam String name, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(orderService.findBySupplierSupplierNameIgnoreCaseContaining(name, pageable));
    }

    @GetMapping("/supplier/balance/{supplierID}")
    public ResponseEntity<?> fetchCustomerBalance(@PathVariable Long supplierID) {
        return new ResponseEntity<>(orderService.findSupplierBalanceBySupplier(supplierID), HttpStatus.OK);
    }

    @GetMapping("/supplier/{supplierID}")
    public ResponseEntity<?> fetchSupplierById(@PathVariable Long supplierID) {
        return new ResponseEntity<>(orderService.findBySupplier(supplierID), HttpStatus.OK);
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
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderID) throws Exception {
        PurchaseOrder order = orderService.findByOrderID(orderID);
        if (order == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("orderID: " + orderID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        orderService.deleteOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/details/{orderID}")
    public ResponseEntity<?> deleteOrderDetails(@PathVariable Long orderID) throws Exception {
        PurchaseOrderDetail order = orderDetailService.findById(orderID);
        if (order == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("orderID: " + orderID + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        List<PurchaseOrderDetail> purchaseOrder = orderService.deleteOrderDetails(order);
        return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
    }

    @GetMapping("/details/{orderID}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderID) {
        if (orderID == null) {
            return new ResponseEntity<>(new CustomErrorTypeException("orderID: " + orderID + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        PurchaseOrderDetail order = orderDetailService.findById(orderID);
        PurchaseOrder purchaseOrder = orderService.findByOrderID(order.getPurchaseOrder().getPurchaseOrderID());
        return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
    }

    @GetMapping("/current-stock")
    public ResponseEntity<?> fetchCurrentStock(@RequestParam String productName) {
        return ResponseEntity.ok(orderDetailService.findCurrentStock(productName));
    }
}
