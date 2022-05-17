package com.nc.med.controller;

import com.nc.med.model.*;
import com.nc.med.repo.*;
import com.nc.med.util.CsvUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/upload")
public class FileController {
    private ProductRepo productRepo;
    private PurchaseOrderRepo purchaseOrderRepo;
    private PurchaseOrderDetailRepo purchaseOrderDetailRepo;
    private SalesOrderRepo salesOrderRepo;
    private SalesOrderDetailRepo salesOrderDetailRepo;

    @PostMapping(value = "/product", consumes = "multipart/form-data")
    public void product(@RequestParam("file") MultipartFile file) throws IOException {
        productRepo.saveAll(CsvUtils.read(Product.class, file.getInputStream()));
    }

    @PostMapping(value = "/purchase_order", consumes = "multipart/form-data")
    public void purchaseOrder(@RequestParam("file") MultipartFile file) throws IOException {
        purchaseOrderRepo.saveAll(CsvUtils.read(PurchaseOrder.class, file.getInputStream()));
    }

    @PostMapping(value = "/purchase_order_detail", consumes = "multipart/form-data")
    public void purchaseOrderDetails(@RequestParam("file") MultipartFile file) throws IOException {
        purchaseOrderDetailRepo.saveAll(CsvUtils.read(PurchaseOrderDetail.class, file.getInputStream()));
    }

    @PostMapping(value = "/sales_order", consumes = "multipart/form-data")
    public void salesOrder(@RequestParam("file") MultipartFile file) throws IOException {
        salesOrderRepo.saveAll(CsvUtils.read(SalesOrder.class, file.getInputStream()));
    }

    @PostMapping(value = "/sales_order_detail", consumes = "multipart/form-data")
    public void salesOrderDetails(@RequestParam("file") MultipartFile file) throws IOException {
        salesOrderDetailRepo.saveAll(CsvUtils.read(SalesOrderDetail.class, file.getInputStream()));
    }
}
