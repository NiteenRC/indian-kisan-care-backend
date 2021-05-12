package com.nc.med.controller;

import com.nc.med.exception.CustomErrorTypeException;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.service.ProductService;
import com.nc.med.service.PurchaseOrderDetailService;
import com.nc.med.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchaseOrder")
@Validated
public class PurchaseOrderController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    private PurchaseOrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PurchaseOrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<?> addOrderList(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder purchaseOrderRes = orderService.saveOrder(purchaseOrder);

        purchaseOrder.getPurchaseOrderDetail().stream().forEach(purchaseOrderDetail -> {
            purchaseOrderDetail.setPurchaseOrder(purchaseOrderRes);
            orderDetailService.savePurchaseOrderDetail(purchaseOrderDetail);
        });
        return new ResponseEntity<>(purchaseOrderRes, HttpStatus.OK);
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
    public ResponseEntity<?> deleteOrder(@PathVariable Integer orderID) {
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
